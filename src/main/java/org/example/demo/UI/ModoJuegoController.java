package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
        // false = Modo individual (pedirá Comité)
        abrirInputNombre(false);
    }

    @FXML
    private void jugarGrupo() {
        // true = Modo grupo (pedirá Presidente)
        abrirInputNombre(true);
    }

    private void abrirInputNombre(boolean esGrupo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/InputNombre-view.fxml"));
            Parent root = loader.load();

            InputNombreController controller = loader.getController();

            if (esGrupo) {
                // Si es grupo, llamamos al método que cambia el título a PRESIDENTE
                controller.setModoPresidente();
            } else {
                // Si es individual, llamamos al método normal (título COMITÉ)
                controller.setModoJuego(false);
            }

            Stage stage = (Stage) btnIndividual.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void volverAtras() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/demo/presentacion-view.fxml"));
            Stage stage = (Stage) btnIndividual.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}