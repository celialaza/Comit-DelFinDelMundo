package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.demo.DB.PartidaDAO;
import org.example.demo.LOGIC.Partida;

import java.io.IOException;

public class FinPartidaController {

    @FXML private Button btnVolver;

    // Labels para mostrar las estadísticas finales
    @FXML private Label lblFinalSalud;
    @FXML private Label lblFinalBienestar;
    @FXML private Label lblFinalLegado;
    @FXML private Label lblFinalRecursos;
    @FXML private Label lblCausaDerrota; // Solo en derrota

    private Partida partidaFinalizada;

    /**
     * Recibe los datos de la partida y rellena la interfaz.
     */
    public void setPartida(Partida partida) {
        this.partidaFinalizada = partida;

        // Rellenamos los labels con los valores reales al terminar
        if (lblFinalSalud != null) lblFinalSalud.setText(partida.getSalud() + "%");
        if (lblFinalBienestar != null) lblFinalBienestar.setText(partida.getBienestar() + "%");
        if (lblFinalLegado != null) lblFinalLegado.setText(partida.getLegado() + "%");
        if (lblFinalRecursos != null) lblFinalRecursos.setText(partida.getRecursos() + "%");

        // Si estamos en derrota, mostramos la causa específica del fracaso
        if (lblCausaDerrota != null) {
            lblCausaDerrota.setText(partida.getResultadoFinal());
        }
    }

    @FXML
    private void volverAlMenu() {
        cambiarPantalla("/org/example/demo/presentacion-view.fxml");
    }

    @FXML
    private void registrarPartida() {
        guardarYVerRanking();
    }

    @FXML
    private void verRanking() {
        guardarYVerRanking();
    }

    private void guardarYVerRanking() {
        if (partidaFinalizada == null) return;
        PartidaDAO dao = new PartidaDAO();
        dao.guardarPartida(partidaFinalizada);
        cambiarPantalla("/org/example/demo/registro-view.fxml");
    }

    private void cambiarPantalla(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }
}