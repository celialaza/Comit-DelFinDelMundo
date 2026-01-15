package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.demo.DB.PartidaDAO;
import org.example.demo.LOGIC.Partida;

import java.io.IOException;

public class FinPartidaController {

    @FXML private Button btnVolver;

    // Aquí guardamos los datos de la partida que acaba de terminar
    private Partida partidaFinalizada;

    /**
     * Este método recibe la partida desde JuegoController.
     */
    public void setPartida(Partida partida) {
        this.partidaFinalizada = partida;
    }

    @FXML
    private void volverAlMenu() {
        cambiarPantalla("/org/example/demo/presentacion-view.fxml");
    }

    /**
     * Botón "Registrar Partida" (Pantalla Victoria)
     */
    @FXML
    private void registrarPartida() {
        guardarYVerRanking();
    }

    /**
     * Botón "Ver Ranking" (Pantalla Derrota)
     * CORRECCIÓN: Ahora este método TAMBIÉN guarda la partida antes de ir al ranking.
     */
    @FXML
    private void verRanking() {
        guardarYVerRanking();
    }

    // --- MÉTODOS AUXILIARES ---

    private void guardarYVerRanking() {
        if (partidaFinalizada == null) {
            mostrarAlerta("Error", "No se han encontrado datos de la partida.");
            return;
        }

        // 1. GUARDAR EN BASE DE DATOS
        // (Esto asegura que tanto Victorias como Derrotas queden registradas)
        PartidaDAO dao = new PartidaDAO();
        dao.guardarPartida(partidaFinalizada);

        // 2. IR A LA PANTALLA DE REGISTRO
        cambiarPantalla("/org/example/demo/registro-view.fxml");
    }

    private void cambiarPantalla(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // Mantenemos la misma ventana y tamaño
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Navegación", "No se pudo cargar la pantalla: " + fxml);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}