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

    private int salud = 0;
    private int bienestar = 0;
    private int legado = 0;
    private int recursos = 0;

    private int capacidadInventario = 10;
    private List<Carta> inventario = new ArrayList<>();
    private List<Carta> mazoCartas;
    private int cartaActualIndex = 0;
    private boolean presidenteSalvado = false; // Indica si el presidente se salvó
    private DAO cartaDAO;

    private String mensajePenalizacion = "";

    public Partida() {
        this.cartaDAO = new CartaDAO();
        this.mazoCartas = cartaDAO.cargarCartas();
    }

    public void aplicarPenalizaciones() {
        int huecosVacios = capacidadInventario - inventario.size();

        if (huecosVacios > 0) {
            int penalizacionTotal = huecosVacios * 10;

            this.salud = Math.max(0, this.salud - penalizacionTotal);
            this.bienestar = Math.max(0, this.bienestar - penalizacionTotal);
            this.legado = Math.max(0, this.legado - penalizacionTotal);
            this.recursos = Math.max(0, this.recursos - penalizacionTotal);

            // TRADUCCIÓN DINÁMICA DEL MENSAJE
            if (LanguageManager.getCurrentLocale().getLanguage().equals("en")) {
                this.mensajePenalizacion = "Due to the committee's lack of responsibility and wrong decisions, " +
                        "the colony will be penalized with -" + penalizacionTotal + "% in each statistic " +
                        "for leaving " + huecosVacios + " empty slot(s) in the ship.";
            } else {
                this.mensajePenalizacion = "Ante las decisiones equivocadas del comité por falta de responsabilidad, " +
                        "se penalizará la colonia con -" + penalizacionTotal + "% en cada estadística " +
                        "por haber dejado " + huecosVacios + " hueco(s) vacíos en la nave.";
            }
        }
    }

    public void elegirCarta(Carta carta) {
        if (inventario.size() < capacidadInventario) {
            this.inventario.add(carta);

            if (carta.getTitulo().equalsIgnoreCase("El Psicópata")) {
                this.capacidadInventario += 2;
            }

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

    public void aumentarCapacidadInventario() { this.capacidadInventario++; }

    public void salvarPresidente() {
        this.presidenteSalvado = true;
    }
}