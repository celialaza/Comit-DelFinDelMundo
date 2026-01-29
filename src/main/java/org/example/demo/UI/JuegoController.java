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

    @FXML private Label lblStat1, lblStat2, lblStat3, lblStat4;
    @FXML private Label lblInventarioContador, lblTituloCarta, lblDescripcion, lblQuedanOpciones;
    @FXML private ImageView imgCarta;

    private Partida partidaActual;
    private Carta cartaEnPantalla;

    public void setPartida(Partida partida) {
        this.partidaActual = partida;
        actualizarInterfaz(); // Aquí se aplicará el rojo inicial
        cargarSiguienteCarta();
    }

    private void cargarSiguienteCarta() {
        if (partidaActual.isJuegoTerminado()) {
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
            } catch (Exception e) { System.err.println("Imagen no encontrada."); }
        }
        actualizarInterfaz();
    }

    @FXML
    private void accionAnadir() {
        if (cartaEnPantalla == null) return;
        if (partidaActual.getInventario().size() >= partidaActual.getCapacidadInventario()) {
            mostrarAlerta("Mochila llena", "No hay espacio. Descarta esta opción.");
            return;
        }
        partidaActual.elegirCarta(cartaEnPantalla);
        cargarSiguienteCarta();
    }

    @FXML
    private void accionDescartar() {
        if (cartaEnPantalla == null) return;
        partidaActual.descartarCarta();
        cargarSiguienteCarta();
    }

    private void actualizarInterfaz() {
        actualizarLabelStat(lblStat1, partidaActual.getSalud());
        actualizarLabelStat(lblStat2, partidaActual.getBienestar());
        actualizarLabelStat(lblStat3, partidaActual.getLegado());
        actualizarLabelStat(lblStat4, partidaActual.getRecursos());

        lblInventarioContador.setText(partidaActual.getInventario().size() + "/" + partidaActual.getCapacidadInventario());

        int queda = partidaActual.getMazoCartas().size() - partidaActual.getCartaActualIndex();
        lblQuedanOpciones.setText("Quedan " + queda + " opciones");
    }

    private void actualizarLabelStat(Label label, int valor) {
        label.setText(valor + "%");
        if (valor < 50) {
            // ROJO para peligro (se activará al inicio por estar al 0%)
            label.setStyle("-fx-text-fill: #FF3333; -fx-font-weight: bold;");
        } else {
            // Color oscuro original de tu diseño para valores seguros
            label.setStyle("-fx-text-fill: #263238; -fx-font-weight: bold;");
        }
    }

    private void gestionarFinDePartida() {
        boolean esVictoria = !partidaActual.getResultadoFinal().contains("FRACASADO");
        try {
            String fxml = esVictoria ? "/org/example/demo/victoria-view.fxml" : "/org/example/demo/derrota-view.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            FinPartidaController controller = loader.getController();
            controller.setPartida(this.partidaActual);
            lblTituloCarta.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void abrirInventario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/inventario-view.fxml"));
            Parent root = loader.load();
            InventarioController controller = loader.getController();
            controller.cargarDatos(partidaActual.getInventario());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void mostrarAlerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(t); a.setHeaderText(null); a.setContentText(m);
        a.showAndWait();
    }
}