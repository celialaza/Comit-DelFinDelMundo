package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;


public class PresentacionController {
    Logger logger = Logger.getLogger(PresentacionController.class.getName());

        @FXML
        private Button btnInstrucciones;

        @FXML
        private Button btnJugar;


        @FXML
        public void initialize() {

        }
        @FXML
        private void Instrucciones() {
            logger.info("Iniciando Instrucciones");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Instrucciones");
            alert.setHeaderText("Bienvenido al Comité del Fin del Mundo");

            String instrucciones = "Tu misión es repoblar un nuevo planeta\n\n "+
                    "1. Te enfrentarás a 30 importantes decisiones(cartas).\n"+
                    "2. Solo podrás guardar 10 en tu inventario. \n"+
                    "3. Cada carta afecta tus 4 estadísticas: Salud, Bienestar, Legado y Recursos.\n\n"+
                    "4. Ten cuidado con lo que consideras oportuno, porque de las estadísticas dependerá que tu planeta prospere.\n "+
                    "5. El éxito está en el equilibrio. ¡Suerte, Presidenta!";
            alert.setContentText(instrucciones);


            try {
                alert.getDialogPane().getStylesheets().add(
                        getClass().getResource("/org/example/demo/estilos.css").toExternalForm()
                );
                // Esto le da una clase de estilo al panel para poder apuntar
                alert.getDialogPane().getStyleClass().add("my-dialog");

            } catch (NullPointerException e) {
                System.err.println("No se pudo encontrar estilos.css para la alerta.");
            }
            alert.showAndWait();
        }

        @FXML
        private void Jugar() {
            logger.info("Iniciando el juego...");

                try {
                    // CAMBIA "ModoJuegoView.fxml" por la ruta correcta si es necesario
                    Parent root = FXMLLoader.load(getClass().getResource("/org/example/demo/modo-juego-view.fxml"));
                    Stage stage = (Stage) btnJugar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



}
