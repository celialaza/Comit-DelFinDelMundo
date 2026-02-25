package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.demo.DB.PartidaDAO;
import org.example.demo.LOGIC.LanguageManager;
import org.example.demo.LOGIC.Partida;

import java.io.IOException;
import java.io.InputStream;

public class FinPartidaController {

    @FXML private Button btnVolver;
    @FXML private Label lblFinalSalud, lblFinalBienestar, lblFinalLegado, lblFinalRecursos;
    @FXML private ImageView imgFinalSalud, imgFinalBienestar, imgFinalLegado, imgFinalRecursos;

    private Partida partidaFinalizada;
    @FXML
    private Label lblTituloEstado;
    @FXML
    private Button btnAccionFinal;
    @FXML
    private Label lblCausaDerrota;

    public void setPartida(Partida partida) {
        MusicManager.playFinalMusic();
        this.partidaFinalizada = partida;

        boolean esVictoria = !partida.getResultadoFinal().contains("FRACASADO");

        // TRADUCCIÓN DE ESTADOS Y MENSAJES
        if (lblTituloEstado != null) {
            lblTituloEstado.setText(LanguageManager.getString(esVictoria ? "end.victory" : "end.defeat"));
        }

        if (lblCausaDerrota != null) {
            lblCausaDerrota.setText(LanguageManager.getString(esVictoria ? "end.victory.msg" : "end.defeat.msg"));
        }

        if (btnAccionFinal != null) {
            btnAccionFinal.setText(LanguageManager.getString(esVictoria ? "end.btn.register" : "end.btn.ranking"));
        }

        if (btnVolver != null) btnVolver.setText(LanguageManager.getString("btn.back"));

        // Rellenar porcentajes e iconos
        if (lblFinalSalud != null) lblFinalSalud.setText(partida.getSalud() + "%");
        if (lblFinalBienestar != null) lblFinalBienestar.setText(partida.getBienestar() + "%");
        if (lblFinalLegado != null) lblFinalLegado.setText(partida.getLegado() + "%");
        if (lblFinalRecursos != null) lblFinalRecursos.setText(partida.getRecursos() + "%");

        actualizarIconoFinal(imgFinalSalud, "heart", partida.getSalud());
        actualizarIconoFinal(imgFinalBienestar, "happy", partida.getBienestar());
        actualizarIconoFinal(imgFinalLegado, "book", partida.getLegado());
        actualizarIconoFinal(imgFinalRecursos, "settings", partida.getRecursos());
    }

    private void actualizarIconoFinal(ImageView icono, String prefijo, int valor) {
        if (icono == null) return;
        String sufijo = (valor == 0) ? "0" : (valor <= 35) ? "25" : (valor <= 65) ? "50" : (valor <= 95) ? "75" : "100";
        try {
            InputStream is = getClass().getResourceAsStream("/iconos/" + prefijo + sufijo + ".png");
            if (is != null) icono.setImage(new Image(is));
        } catch (Exception e) {}
    }

    @FXML
    private void volverAlMenu() {
        MusicManager.playClickSound();
        MusicManager.stopFinalMusic();
        cambiarPantalla("/org/example/demo/presentacion-view.fxml");
    }

    @FXML
    private void registrarPartida() {
        MusicManager.playClickSound();
        guardarYVerRanking();
    }

    @FXML
    private void verRanking() {
        MusicManager.playClickSound();
        guardarYVerRanking();
    }

    private void guardarYVerRanking() {
        if (partidaFinalizada == null) return;
        new PartidaDAO().guardarPartida(partidaFinalizada);
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