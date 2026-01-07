package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class InputNombreController {

    @FXML
    private Label lblPregunta;
    @FXML
    private TextField campoNombre;

    private boolean esModoGrupo;
    private boolean pidiendoPresidente;


    public void setModoJuego(boolean esGrupo) {
        this.esModoGrupo = esGrupo;
        this.pidiendoPresidente = false;

    }


    public void setModoPresidente() {
        this.esModoGrupo = true;
        this.pidiendoPresidente = true;
        lblPregunta.setText("¿CÓMO SE LLAMA EL PRESIDENTE?");
        campoNombre.setText("");
    }

    @FXML
    private void irAlJuego() {
        String textoIngresado = campoNombre.getText();

        if (textoIngresado.isEmpty()) {
            System.out.println("¡Escribe algo!");
            return;
        }


        if (esModoGrupo) {
            System.out.println("GUARDADO - Presidente del Grupo: " + textoIngresado);


        } else {

            System.out.println("GUARDADO - Nombre del Comité: " + textoIngresado);

        }

        lanzarJuego();
    }

    private void lanzarJuego() {
        System.out.println("Cargando pantalla de decisión...");

            try {

                String ruta = "/org/example/demo/decision-presidente-view.fxml";

                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(ruta));
                javafx.scene.Parent root = loader.load();

                javafx.stage.Stage stage = (Stage) campoNombre.getScene().getWindow();
                stage.setScene(new javafx.scene.Scene(root));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    @FXML
    private void volverAtras() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/modo-juego-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) campoNombre.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}