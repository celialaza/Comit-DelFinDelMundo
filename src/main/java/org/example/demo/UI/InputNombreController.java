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
            cargarPantallaDecision();


        } else {
            iniciarPartidaDirectamente();

        }
    }

    // Método 1: Carga la pantalla de decisión (Solo Grupo)
    private void cargarPantallaDecision() {
        try {
            String ruta = "/org/example/demo/decision-presidente-view.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            Stage stage = (Stage) campoNombre.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la pantalla de decisión.");
        }
    }

    // Método 2: Carga el juego directamente (Solo Individual)
    private void iniciarPartidaDirectamente() {
        try {
            String ruta = "/org/example/demo/JuegoView.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();

            // Creamos la partida
            org.example.demo.LOGIC.Partida nuevaPartida = new org.example.demo.LOGIC.Partida();

            //Se la pasamos al controlador del juego
            JuegoController controller = loader.getController();
            controller.setPartida(nuevaPartida);

            //Mostramos la pantalla
            Stage stage = (Stage) campoNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la pantalla del Juego.");
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