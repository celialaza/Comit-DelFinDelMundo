package org.example.demo.LOGIC;

import lombok.Data;
import org.example.demo.DB.CartaDAO;
import org.example.demo.DB.DAO;
import org.example.demo.MODEL.Carta;

import java.util.ArrayList;
import java.util.List;

@Data
public class Partida {


    private int salud = 50;
    private int bienestar = 50;
    private int legado = 50;
    private int recursos = 50;


    private int capacidadInventario = 10;
    private List<Carta> inventario = new ArrayList<>();


    private List<Carta> mazoCartas;
    private int cartaActualIndex = 0;
    private boolean presidenteSalvado = false; // Para el otro bonus


    private DAO cartaDAO;

    public Partida() {
        this.cartaDAO = new CartaDAO();
        this.mazoCartas = cartaDAO.cargarCartas();
    }


    public void aumentarCapacidadInventario() {
        this.capacidadInventario = 11;
    }

    public void salvarPresidente() {
        this.presidenteSalvado = true;
    }


    public Carta getSiguienteCarta() {
        if (cartaActualIndex < mazoCartas.size()) {
            return mazoCartas.get(cartaActualIndex);
        }
        return null;
    }

    public void elegirCarta(Carta carta) {
        if (inventario.size() < capacidadInventario) {
            inventario.add(carta);

            this.salud += carta.getSalud();
            this.bienestar += carta.getBienestar();
            this.legado += carta.getLegado();
            this.recursos += carta.getRecursos();
        } else {
            System.out.println("¡Inventario lleno! No se pudo añadir la carta.");
        }

        cartaActualIndex++;
    }

    public void descartarCarta() {
        cartaActualIndex++;
    }

    public boolean isJuegoTerminado() {
        boolean inventarioLleno = inventario.size() >= capacidadInventario;
        boolean mazoTerminado = cartaActualIndex >= mazoCartas.size();

        return inventarioLleno || mazoTerminado;
    }

    public String getResultadoFinal() {
        final int UMBRAL_FRACASO = 25;


        if (salud < UMBRAL_FRACASO || bienestar < UMBRAL_FRACASO || legado < UMBRAL_FRACASO || recursos < UMBRAL_FRACASO) {
            String causa = "causas desconocidas";
            if (salud < UMBRAL_FRACASO) causa = "la enfermedad (salud)";
            else if (bienestar < UMBRAL_FRACASO) causa = "la desesperación (bienestar)";
            else if (recursos < UMBRAL_FRACASO) causa = "el hambre (recursos)";
            else if (legado < UMBRAL_FRACASO) causa = "la anarquía (legado)";

            return "Tu colonia ha FRACASADO debido a " + causa + ".";
        }

        int puntuacion = Math.min(Math.min(salud, bienestar), Math.min(legado, recursos));

        if (puntuacion > 80) return "¡ÉXITO TOTAL! Una utopía perfecta.";
        if (puntuacion > 50) return "SOBREVIVÍS. Será duro, pero hay esperanza.";
        return "SOBREVIVÍS POR LOS PELOS. El invierno será cruel.";
    }
}