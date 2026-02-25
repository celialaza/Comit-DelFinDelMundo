package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.example.demo.LOGIC.LanguageManager;
import org.example.demo.LOGIC.Partida;
import java.io.IOException;

public class DecisionPresidenteController {

    @FXML private Button btnEspacio;
    @FXML private Button btnSalvacion;
    @FXML private Label lblExplicacion;
    @FXML private Label lblTituloDecision;

    private String nombrePresidente;

    public void setNombrePresidente(String nombre) {
        this.nombrePresidente = nombre;

        if (lblTituloDecision != null) lblTituloDecision.setText(LanguageManager.getString("president.secret.title"));

        if (lblExplicacion != null) {
            String baseExpl = LanguageManager.getString("president.secret.expl");
            lblExplicacion.setText(String.format(baseExpl, nombre, nombre.toUpperCase()));
            lblExplicacion.setWrapText(true);
        }

        if (btnEspacio != null) btnEspacio.setText(LanguageManager.getString("president.btn.space"));
        if (btnSalvacion != null) btnSalvacion.setText(String.format(LanguageManager.getString("president.btn.salvation"), nombre));
    }

    @FXML
    private void elegirEspacio() {
        MusicManager.playClickSound();
        Partida nuevaPartida = new Partida();
        nuevaPartida.setNombreComite(InputNombreController.nombreComiteTemporal);
        nuevaPartida.setNombrePresidente(this.nombrePresidente);
        nuevaPartida.aumentarCapacidadInventario();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageManager.getString("president.alert.title"));
        alert.setHeaderText(LanguageManager.getString("president.alert.space.header"));
        alert.setContentText(String.format(LanguageManager.getString("president.alert.space.content"), nombrePresidente));

        aplicarEstiloDialogo(alert, "dialog-presidente");
        alert.showAndWait();

        iniciarJuego(nuevaPartida);
    }

    @FXML
    private void elegirSalvacion() {
        MusicManager.playClickSound();
        Partida nuevaPartida = new Partida();
        nuevaPartida.setNombreComite(InputNombreController.nombreComiteTemporal);
        nuevaPartida.setNombrePresidente(this.nombrePresidente);
        nuevaPartida.salvarPresidente();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(LanguageManager.getString("president.alert.title"));
        alert.setHeaderText(LanguageManager.getString("president.alert.salvation.header"));
        alert.setContentText(LanguageManager.getString("president.alert.salvation.content"));

        aplicarEstiloDialogo(alert, "dialog-presidente");
        alert.showAndWait();

        iniciarJuego(nuevaPartida);
    }

    private void aplicarEstiloDialogo(Alert alerta, String claseCSS) {
        DialogPane dp = alerta.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("/org/example/demo/estilos.css").toExternalForm());
        dp.getStyleClass().add(claseCSS);
        dp.setMinHeight(Region.USE_PREF_SIZE);

        Button btnAceptar = (Button) dp.lookupButton(ButtonType.OK);
        Button btnCancel = (Button) dp.lookupButton(ButtonType.CANCEL);
        if (btnAceptar != null) {
            btnAceptar.setText(LanguageManager.getString("btn.accept"));
        }
    }

    private void iniciarJuego(Partida partidaCreada) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/JuegoView.fxml"));
            Parent root = loader.load();
            JuegoController controller = loader.getController();
            controller.setPartida(partidaCreada);
            btnEspacio.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }
}