package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class ModoJuegoController {

    @FXML
    private Button btnIndividual;

    @FXML
    private Button btnGrupo;

    @FXML
    public void initialize() {
        System.out.println("Pantalla de Selección de Modo cargada.");
    }

    @FXML
    private void jugarIndividual() {
        abrirInputNombre(false);
    }

    @FXML
    private void jugarGrupo() {
        abrirInputNombre(true);
    }

    private void abrirInputNombre(boolean esGrupo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/InputNombre-view.fxml"));
            Parent root = loader.load();

            InputNombreController controller = loader.getController();
            controller.setModoJuego(esGrupo);

            // MANTENER TAMAÑO: Cambiar solo el root
            btnIndividual.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void volverAtras() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/demo/presentacion-view.fxml"));
            // MANTENER TAMAÑO
            btnIndividual.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}