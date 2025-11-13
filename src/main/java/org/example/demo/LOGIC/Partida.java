package org.example.demo.LOGIC;

//Esta clase mantiene el estado de la partida

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.demo.DB.CartaDAO;
import org.example.demo.DB.DAO;
import org.example.demo.MODEL.Carta;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class Partida {

    // Estadísticas
    private int salud = 50;
    private int bienestar = 50;
    private int legado = 50;
    private int recursos = 50;

    //Inventario
    private List<Carta> inventario = new ArrayList<>(10);

    // Opciones cargadas de la BD
    private List<Carta> mazoCartas;
    private int cartaActualIndex = 0;

    // Usamos la interfaz
    private DAO cartaDAO;

    public Partida() {
        // Al iniciar la partida, se carga el mazo
        this.cartaDAO = new CartaDAO();
        this.mazoCartas = cartaDAO.cargarCartas();
    }

    /**
     * Devuelve la siguiente carta del mazo.
     */
    public Carta getSiguienteCarta() {
        if (cartaActualIndex < mazoCartas.size()) {
            return mazoCartas.get(cartaActualIndex);
        }
        return null;
    }

    /**
     * Método llamado si el jugador elige la carta.
     */
    public void elegirCarta(Carta carta) {
        if (inventario.size() < 10) {
            inventario.add(carta);
        }
        // Sumar estadísticas
        this.salud += carta.getSalud();
        this.bienestar += carta.getBienestar();
        this.legado += carta.getLegado();
        this.recursos += carta.getRecursos();

        cartaActualIndex++;
    }

    /**
     * Método llamado si el jugador descarta.
     */
    public void descartarCarta() {
        cartaActualIndex++;
    }

    /**
     * Comprueba si el juego termina.
     */
    public boolean isJuegoTerminado() {
        // Termina si el inventario está lleno o si se acaba el mazo
        return inventario.size() >= 10 || cartaActualIndex >= mazoCartas.size();
    }

    /**
     * Calcula el resultado final basado en la puntuación.
     */
    public String getResultadoFinal() {

        final int UMBRAL_FRACASO = 25;

        if (salud < UMBRAL_FRACASO || bienestar < UMBRAL_FRACASO || legado < UMBRAL_FRACASO || recursos < UMBRAL_FRACASO) {

            String causaDelFracaso = "la anarquía (legado)";
            if (salud < UMBRAL_FRACASO) causaDelFracaso = "la enfermedad (salud)";
            if (bienestar < UMBRAL_FRACASO) causaDelFracaso = "la desesperación (bienestar)";
            if (recursos < UMBRAL_FRACASO) causaDelFracaso = "el hambre (recursos)";

            return "Tu colonia está ABOCADA AL FRACASO. A pesar de tus esfuerzos, " + causaDelFracaso + " ha destruido vuestra esperanza.";
        }


        int puntuacionEquilibrio = Math.min(salud,
                Math.min(bienestar,
                        Math.min(legado, recursos)));


        if (puntuacionEquilibrio > 80) {
            return "¡Tu colonia es PROSPERA! Un nuevo amanecer, perfectamente equilibrado. Has construido una utopía.";
        } else if (puntuacionEquilibrio > 50) {
            return "Tu colonia SOBREVIVE. Será duro, pero los cimientos son sólidos. Hay esperanza.";
        } else {
            return "Tu colonia AGUANTA... a duras penas. El primer invierno será una prueba de fuego. Revisa tus prioridades.";
        }
    }
    }

