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
        // Lógica de porcentajes
        // --- Regla 1: La Catástrofe ---
        // Empezamos con 50 en todo. Si caes por debajo de, digamos, 25 en CUALQUIER
        // cosa, has fracasado automáticamente.

        final int UMBRAL_FRACASO = 25;

        if (salud < UMBRAL_FRACASO || bienestar < UMBRAL_FRACASO || legado < UMBRAL_FRACASO || recursos < UMBRAL_FRACASO) {

            // Averiguamos cuál fue la causa del fracaso para dar feedback
            String causaDelFracaso = "la anarquía (legado)";
            if (salud < UMBRAL_FRACASO) causaDelFracaso = "la enfermedad (salud)";
            if (bienestar < UMBRAL_FRACASO) causaDelFracaso = "la desesperación (bienestar)";
            if (recursos < UMBRAL_FRACASO) causaDelFracaso = "el hambre (recursos)";

            // Devolvemos un mensaje de fracaso
            return "Tu colonia está ABOCADA AL FRACASO. A pesar de tus esfuerzos, " + causaDelFracaso + " ha destruido vuestra esperanza.";
        }

        // --- Regla 2: El Equilibrio (El Eslabón Débil) ---
        // Si llegas aquí, has sobrevivido. Ahora vemos CÓMO de bien.
        // Tu puntuación real es tu estadística más baja.
        // ¡Una cadena es tan fuerte como su eslabón más débil!

        int puntuacionEquilibrio = Math.min(salud,
                Math.min(bienestar,
                        Math.min(legado, recursos)));

        // Ahora definimos los finales basados en esa puntuación de equilibrio

        if (puntuacionEquilibrio > 80) {
            // Un éxito rotundo
            return "¡Tu colonia es PROSPERA! Un nuevo amanecer, perfectamente equilibrado. Has construido una utopía.";
        } else if (puntuacionEquilibrio > 50) {
            // Un éxito medio
            return "Tu colonia SOBREVIVE. Será duro, pero los cimientos son sólidos. Hay esperanza.";
        } else {
            // Sobreviviste, pero por los pelos (estás entre 25 y 50)
            return "Tu colonia AGUANTA... a duras penas. El primer invierno será una prueba de fuego. Revisa tus prioridades.";
        }
    }
    }

