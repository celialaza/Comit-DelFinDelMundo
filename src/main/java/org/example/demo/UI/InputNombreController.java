package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.example.demo.LOGIC.LanguageManager;
import org.example.demo.LOGIC.Partida;
import java.io.IOException;

public class InputNombreController {

    @FXML private Label lblPregunta;
    @FXML private TextField campoNombre;
    @FXML private Button btnAtras;
    @FXML private Button btnContinuar;

    private boolean esModoGrupo;
    private boolean pidiendoPresidente;

    public static String nombreComiteTemporal = "Comité Anónimo";
    public static String nombrePresidenteTemporal = "";

    public void setModoJuego(boolean esGrupo) {
        this.esModoGrupo = esGrupo;
        this.pidiendoPresidente = false;
        nombreComiteTemporal = "Comité Anónimo";
        nombrePresidenteTemporal = "";

        lblPregunta.setText(LanguageManager.getString("input.committee.q"));
        campoNombre.setText("");
        campoNombre.setPromptText(LanguageManager.getString("input.committee.prompt"));
        actualizarBotones();
    }

    private void actualizarBotones() {
        if (btnAtras != null) btnAtras.setText(LanguageManager.getString("btn.back"));
        if (btnContinuar != null) btnContinuar.setText(LanguageManager.getString("btn.continue"));
    }

    @FXML
    private void irAlJuego() {
        MusicManager.playClickSound();
        String texto = campoNombre.getText();

        if (texto == null || texto.trim().isEmpty()) {
            mostrarAlertaError(LanguageManager.getString("input.error.name"));
            return;
        }

        if (esModoGrupo) {
            if (!pidiendoPresidente) {
                nombreComiteTemporal = texto;
                mostrarReglasPresidente();
                prepararInputPresidente();
            } else {
                nombrePresidenteTemporal = texto;
                mostrarAvisoSecreto(texto);
                cargarPantallaDecision(texto);
            }
        } else {
            nombreComiteTemporal = texto;
            iniciarPartidaDirectamente(texto);
        }
    }

    private void prepararInputPresidente() {
        this.pidiendoPresidente = true;
        lblPregunta.setText(LanguageManager.getString("input.president.q"));
        campoNombre.setText("");
        campoNombre.setPromptText(LanguageManager.getString("input.president.prompt"));
        actualizarBotones();
        MusicManager.playSelectionMusic();
    }

    private void mostrarReglasPresidente() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageManager.getString("input.alert.rules.title"));
        alert.setHeaderText(null);
        alert.setContentText(LanguageManager.getString("input.alert.rules.content"));
        aplicarEstiloDialogo(alert, "dialog-presidente");
        alert.showAndWait();
    }

    private void mostrarAvisoSecreto(String nombrePresidente) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(LanguageManager.getString("input.alert.secret.title"));
        alert.setHeaderText(String.format(LanguageManager.getString("input.alert.secret.header"), nombrePresidente.toUpperCase()));
        alert.setContentText(LanguageManager.getString("input.alert.secret.content"));
        aplicarEstiloDialogo(alert, "dialog-penalizacion");
        alert.showAndWait();
    }

    private void cargarPantallaDecision(String nombrePresidente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/decision-presidente-view.fxml"));
            Parent root = loader.load();
            DecisionPresidenteController controller = loader.getController();
            controller.setNombrePresidente(nombrePresidente);
            campoNombre.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void iniciarPartidaDirectamente(String nombreComite) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/JuegoView.fxml"));
            Parent root = loader.load();
            Partida nuevaPartida = new Partida();
            nuevaPartida.setNombreComite(nombreComite);
            JuegoController controller = loader.getController();
            controller.setPartida(nuevaPartida);
            campoNombre.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void mostrarAlertaError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        aplicarEstiloDialogo(alert, "dialog-penalizacion");
        alert.showAndWait();
    }

    private void aplicarEstiloDialogo(Alert alerta, String claseCSS) {
        DialogPane dp = alerta.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("/org/example/demo/estilos.css").toExternalForm());
        dp.getStyleClass().add(claseCSS);
        dp.setMinHeight(Region.USE_PREF_SIZE);

        Button btnAceptar = (Button) dp.lookupButton(ButtonType.OK);
        if (btnAceptar != null) {
            btnAceptar.setText(LanguageManager.getString("btn.accept"));
        }
    }

    @FXML
    private void volverAtras() {
        MusicManager.playClickSound();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/modo-juego-view.fxml"));
            campoNombre.getScene().setRoot(loader.load());
        } catch (IOException e) { e.printStackTrace(); }
    }
}