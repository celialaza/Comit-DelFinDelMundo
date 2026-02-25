package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.demo.LOGIC.LanguageManager;

import java.io.IOException;

public class ModoJuegoController {

    @FXML private Button btnIndividual;
    @FXML private Button btnGrupo;
    @FXML private Button btnVolver;
    @FXML private Label lblTituloSeleccion;
    @FXML private Label lblPreguntaModo;

    @FXML
    public void initialize() {
        // ACTUALIZACIÓN DE TEXTOS SEGÚN IDIOMA
        if (lblTituloSeleccion != null) {
            lblTituloSeleccion.setText(LanguageManager.getString("game.title"));
        }

        if (lblPreguntaModo != null) {
            lblPreguntaModo.setText(LanguageManager.getString("mode.title"));
        }

        btnIndividual.setText(LanguageManager.getString("mode.individual"));
        btnGrupo.setText(LanguageManager.getString("mode.group"));

        if (btnVolver != null) {
            btnVolver.setText(LanguageManager.getString("btn.back"));
        }
    }

    @FXML
    private void jugarIndividual() {
        MusicManager.playClickSound();
        MusicManager.stopIntroMusic();
        MusicManager.playSelectionMusic();
        abrirInputNombre(false);
    }

    @FXML
    private void jugarGrupo() {
        MusicManager.playClickSound();
        MusicManager.stopIntroMusic();
        MusicManager.playSelectionMusic();
        abrirInputNombre(true);
    }

    private void abrirInputNombre(boolean esGrupo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/InputNombre-view.fxml"));
            Parent root = loader.load();

            InputNombreController controller = loader.getController();
            controller.setModoJuego(esGrupo);

            btnIndividual.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void volverAtras() {
        MusicManager.playClickSound();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/demo/presentacion-view.fxml"));
            btnIndividual.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}