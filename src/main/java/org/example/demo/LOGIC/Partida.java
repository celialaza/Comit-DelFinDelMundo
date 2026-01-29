package org.example.demo.LOGIC;

import lombok.Data;
import org.example.demo.DB.CartaDAO;
import org.example.demo.DB.DAO;
import org.example.demo.MODEL.Carta;
import java.util.ArrayList;
import java.util.List;

@Data
public class Partida {
    private String nombreComite = "Comité Anónimo";
    private String nombrePresidente = "";

    // AHORA EMPIEZAN EN 0%
    private int salud = 0;
    private int bienestar = 0;
    private int legado = 0;
    private int recursos = 0;

    private int capacidadInventario = 10;
    private List<Carta> inventario = new ArrayList<>();
    private List<Carta> mazoCartas;
    private int cartaActualIndex = 0;
    private boolean presidenteSalvado = false;
    private DAO cartaDAO;

    public Partida() {
        this.cartaDAO = new CartaDAO();
        this.mazoCartas = cartaDAO.cargarCartas();
    }

    public void elegirCarta(Carta carta) {
        if (inventario.size() < capacidadInventario) {
            this.inventario.add(carta);
            // Aplicamos topes: no baja de 0 ni sube de 100
            this.salud = Math.max(0, Math.min(100, this.salud + carta.getSalud()));
            this.bienestar = Math.max(0, Math.min(100, this.bienestar + carta.getBienestar()));
            this.legado = Math.max(0, Math.min(100, this.legado + carta.getLegado()));
            this.recursos = Math.max(0, Math.min(100, this.recursos + carta.getRecursos()));
        }
        this.cartaActualIndex++;
    }

    public void descartarCarta() {
        this.cartaActualIndex++;
    }

    public boolean isJuegoTerminado() {
        return (inventario.size() >= capacidadInventario) || (cartaActualIndex >= mazoCartas.size());
    }

    public String getResultadoFinal() {
        final int UMBRAL_DERROTA = 50;
        if (salud < UMBRAL_DERROTA || bienestar < UMBRAL_DERROTA || legado < UMBRAL_DERROTA || recursos < UMBRAL_DERROTA) {
            return "Tu colonia ha FRACASADO. No has logrado alcanzar el 50% de estabilidad mínima.";
        }
        return "¡VICTORIA! La colonia ha prosperado partiendo de la nada.";
    }

    public Carta getSiguienteCarta() {
        if (cartaActualIndex < mazoCartas.size()) return mazoCartas.get(cartaActualIndex);
        return null;
    }

    public void aumentarCapacidadInventario() { this.capacidadInventario = 11; }
    public void salvarPresidente() { this.presidenteSalvado = true; }
}