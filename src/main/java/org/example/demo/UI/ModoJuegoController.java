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
        System.out.println("Elegido: MODO INDIVIDUAL");

    }

    @FXML
    private void jugarGrupo() {
        System.out.println("Elegido: MODO GRUPO");

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