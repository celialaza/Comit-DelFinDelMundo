package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.demo.LOGIC.Partida;
import org.example.demo.MODEL.Carta;

import java.io.IOException;

public class JuegoController {

    @FXML private Label lblStat1;
    @FXML private Label lblStat2;
    @FXML private Label lblStat3;
    @FXML private Label lblStat4;

    @FXML private Label lblInventarioContador;
    @FXML private Label lblTituloCarta;
    @FXML private ImageView imgCarta;
    @FXML private Label lblDescripcion;
    @FXML private VBox boxCartaInterna;

    private Partida partidaActual;
    private Carta cartaEnPantalla;

    @FXML public void initialize() {}

    public void setPartida(Partida partida) {
        this.partidaActual = partida;
        cargarSiguienteCarta();
    }

    private void cargarSiguienteCarta() {
        if (partidaActual.isJuegoTerminado()) {
            gestionarFinDePartida();
            return;
        }

        if (comprobarDerrotaInmediata()) {
            gestionarFinDePartida();
            return;
        }

        this.cartaEnPantalla = partidaActual.getSiguienteCarta();

        if (this.cartaEnPantalla != null) {
            lblTituloCarta.setText(cartaEnPantalla.getTitulo());
            lblDescripcion.setText(cartaEnPantalla.getDescripcion());
            try {
                String ruta = cartaEnPantalla.getImagen();
                if (ruta != null && !ruta.isEmpty()) {
                    if (!ruta.startsWith("/")) ruta = "/" + ruta;
                    imgCarta.setImage(new Image(getClass().getResourceAsStream(ruta)));
                }
            } catch (Exception e) { System.err.println("Imagen no encontrada"); }
        }
        actualizarEstadisticasVista();
    }

    private void actualizarEstadisticasVista() {
        lblStat1.setText(partidaActual.getSalud() + "%");
        lblStat2.setText(partidaActual.getBienestar() + "%");
        lblStat3.setText(partidaActual.getLegado() + "%");
        lblStat4.setText(partidaActual.getRecursos() + "%");

        // Capacidad dinámica (10 u 11)
        int capacidad = partidaActual.getCapacidadInventario();
        lblInventarioContador.setText(partidaActual.getInventario().size() + "/" + capacidad);
    }

    @FXML private void accionAnadir() {
        if (cartaEnPantalla == null) return;
        int capacidad = partidaActual.getCapacidadInventario();
        if (partidaActual.getInventario().size() >= capacidad) {
            mostrarAlerta("Mochila llena", "No caben más cartas. Descarta esta.");
            return;
        }
        partidaActual.elegirCarta(cartaEnPantalla);
        cargarSiguienteCarta();
    }

    @FXML private void accionDescartar() {
        if (cartaEnPantalla == null) return;
        partidaActual.descartarCarta();
        cargarSiguienteCarta();
    }

    @FXML private void abrirInventario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/inventario-view.fxml"));
            Parent root = loader.load();
            InventarioController controller = loader.getController();
            controller.cargarDatos(partidaActual.getInventario());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Inventario");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private boolean comprobarDerrotaInmediata() {
        return partidaActual.getResultadoFinal().contains("FRACASADO");
    }

    private void gestionarFinDePartida() {
        boolean esDerrota = partidaActual.getResultadoFinal().contains("FRACASADO");
        navegarAPantallaFinal(!esDerrota);
    }

    private void navegarAPantallaFinal(boolean esVictoria) {
        try {
            String fxml = esVictoria ? "/org/example/demo/victoria-view.fxml" : "/org/example/demo/derrota-view.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // PASAMOS LA PARTIDA AL CONTROLADOR FINAL PARA PODER GUARDARLA
            FinPartidaController controller = loader.getController();
            controller.setPartida(this.partidaActual);

            if (lblTituloCarta.getScene() != null) {
                lblTituloCarta.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}