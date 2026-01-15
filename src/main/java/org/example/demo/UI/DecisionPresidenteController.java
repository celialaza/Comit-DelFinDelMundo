package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.example.demo.LOGIC.Partida;

import java.io.IOException;

public class DecisionPresidenteController {

    @FXML private Button btnEspacio;
    @FXML private Button btnSalvacion;
    @FXML private Label lblExplicacion;

    private String nombrePresidente;

    public void setNombrePresidente(String nombre) {
        this.nombrePresidente = nombre;
        if (btnSalvacion != null) {
            btnSalvacion.setText("Salvación " + nombre);
        }
        if (lblExplicacion != null) {
            lblExplicacion.setText("¡Hola " + nombre + "! Como Presidente, tienes una decisión ejecutiva única:\n\n" +
                    "OPCIÓN A (+1 ESPACIO): Aumentas la capacidad de la nave para todos.\n" +
                    "OPCIÓN B (SALVACIÓN " + nombre.toUpperCase() + "): Te aseguras tu propia supervivencia (SECRETO).");
            lblExplicacion.setWrapText(true);
        }
    }

    @FXML
    private void elegirEspacio() {
        Partida nuevaPartida = new Partida();
        nuevaPartida.setNombreComite(InputNombreController.nombreComiteTemporal);

        // --- CORRECCIÓN 2B: GUARDAMOS EL NOMBRE DEL PRESIDENTE ---
        nuevaPartida.setNombrePresidente(this.nombrePresidente);
        // ---------------------------------------------------------

        nuevaPartida.aumentarCapacidadInventario();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Comunicado Oficial");
        alert.setHeaderText("¡DECISIÓN DEL PRESIDENTE REVELADA!");
        alert.setContentText(nombrePresidente + " ha decidido aumentar el espacio de la nave.\n(Todo el comité puede volver a mirar)");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();

        iniciarJuego(nuevaPartida);
    }

    @FXML
    private void elegirSalvacion() {
        Partida nuevaPartida = new Partida();
        nuevaPartida.setNombreComite(InputNombreController.nombreComiteTemporal);

        // --- CORRECCIÓN 2B: GUARDAMOS EL NOMBRE DEL PRESIDENTE ---
        nuevaPartida.setNombrePresidente(this.nombrePresidente);
        // ---------------------------------------------------------

        nuevaPartida.salvarPresidente();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confidencial");
        alert.setHeaderText("Operación completada");
        alert.setContentText("Tu salvación ha sido registrada en secreto.\nActúa con normalidad frente al comité.");
        alert.showAndWait();

        iniciarJuego(nuevaPartida);
    }

    private void iniciarJuego(Partida partidaCreada) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/JuegoView.fxml"));
            Parent root = loader.load();
            JuegoController controller = loader.getController();
            controller.setPartida(partidaCreada);
            Stage stage = (Stage) btnEspacio.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }
}