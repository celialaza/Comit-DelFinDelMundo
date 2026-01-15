package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class InputNombreController {

    @FXML private Label lblPregunta;
    @FXML private TextField campoNombre;

    private boolean esModoGrupo;
    private boolean pidiendoPresidente;

    public static String nombreComiteTemporal = "Comité Anónimo";

    public void setModoJuego(boolean esGrupo) {
        this.esModoGrupo = esGrupo;
        this.pidiendoPresidente = false;
        nombreComiteTemporal = "Comité Anónimo";

        lblPregunta.setText("¿CÓMO SE LLAMA EL COMITÉ?");
        campoNombre.setText("");
        campoNombre.setPromptText("Introduce el nombre del comité...");
    }

    private void prepararInputPresidente() {
        this.pidiendoPresidente = true;
        lblPregunta.setText("¿CÓMO SE LLAMA EL PRESIDENTE?");
        campoNombre.setText("");
        campoNombre.setPromptText("Introduce el nombre del presidente...");
    }

    @FXML
    private void irAlJuego() {
        String texto = campoNombre.getText();

        if (texto == null || texto.trim().isEmpty()) {
            mostrarAlertaError("Por favor, escribe un nombre para continuar.");
            return;
        }

        if (esModoGrupo) {
            if (!pidiendoPresidente) {
                nombreComiteTemporal = texto;
                mostrarReglasPresidente();
                prepararInputPresidente();
                return;
            }
            mostrarAvisoSecreto(texto);
            cargarPantallaDecision(texto);
        } else {
            iniciarPartidaDirectamente(texto);
        }
    }

    private void cargarPantallaDecision(String nombrePresidente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/decision-presidente-view.fxml"));
            Parent root = loader.load();

            DecisionPresidenteController controller = loader.getController();
            controller.setNombrePresidente(nombrePresidente);

            Stage stage = (Stage) campoNombre.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void iniciarPartidaDirectamente(String nombreComite) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/JuegoView.fxml"));
            Parent root = loader.load();
            org.example.demo.LOGIC.Partida nuevaPartida = new org.example.demo.LOGIC.Partida();
            nuevaPartida.setNombreComite(nombreComite);
            JuegoController controller = loader.getController();
            controller.setPartida(nuevaPartida);
            Stage stage = (Stage) campoNombre.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    // --- CORRECCIÓN 1: TEXTO ACTUALIZADO ---
    private void mostrarReglasPresidente() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Elección del Presidente");
        alert.setHeaderText("¡Atención Comité!");
        // Aquí está el texto nuevo que pediste:
        alert.setContentText("Solo puede haber un Presidente.\n\nEste dará los turnos de palabra en el comité y, en caso de empate, decidirá si aceptar o no una opción.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
    // ---------------------------------------

    private void mostrarAvisoSecreto(String nombrePresidente) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Decisión Confidencial");
        alert.setHeaderText("¡ALTO AHÍ, " + nombrePresidente.toUpperCase() + "!");
        alert.setContentText("La siguiente pantalla es SOLO para tus ojos.\nQue nadie más mire.");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void volverAtras() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/modo-juego-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) campoNombre.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }
}