package org.example.demo.UI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.demo.LOGIC.Partida;
import javafx.scene.control.Button;

import java.io.IOException;

public class DecisionPresidenteController {
    @FXML
    private Button btnEspacio;
    @FXML
    private void elegirEspacio() {
        System.out.println("DECISIÓN: El Presidente ha elegido +1 ESPACIO.");
        Partida nuevaPartida = new Partida();
        nuevaPartida.aumentarCapacidadInventario();

        iniciarJuego(nuevaPartida);
    }

    @FXML
    private void elegirSalvacion() {
        System.out.println("DECISIÓN: El Presidente ha elegido SALVARSE A SÍ MISMO.");
        Partida nuevaPartida = new Partida();
        nuevaPartida.salvarPresidente();

        iniciarJuego(nuevaPartida);
    }
    /**
     * Método común para cargar la pantalla del juego y pasarle los datos.
     */
    private void iniciarJuego(Partida partidaCreada) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/JuegoView.fxml"));
            Parent root = loader.load();

            JuegoController controller = loader.getController();
            controller.setPartida(partidaCreada);

            Stage stage = (Stage) btnEspacio.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar JuegoView.fxml.");
        }
    }
}

