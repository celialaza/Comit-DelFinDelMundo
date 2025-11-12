package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

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

            /*
            try {
                // Carga el FXML de la SIGUIENTE vista (Paso 2 de 5)
                // Asegúrate de crear un 'ModoJuegoView.fxml' vacío
                Parent siguienteVistaRoot = FXMLLoader.load(getClass().getResource("ModoJuegoView.fxml"));

                // Coge la ventana actual
                Stage ventanaActual = (Stage) btnJugar.getScene().getWindow();

                // Pone la nueva vista en la ventana
                ventanaActual.setScene(new Scene(siguienteVistaRoot));

            } catch (IOException e) {
                System.out.println("Error al cargar ModoJuegoView.fxml. ¿Lo has creado?");
                e.printStackTrace();
            }
        }*/
    }

}
