package org.example.demo.UI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo.LOGIC.AIChatService;
import org.example.demo.LOGIC.LanguageManager;
import org.example.demo.LOGIC.PersonalidadMapper;

import java.text.Normalizer;

public class ChatIAController {

    @FXML private Label lblNombrePersonaje;
    @FXML private VBox vBoxChat;
    @FXML private TextField txtMensaje;
    @FXML private ScrollPane scrollChat;

    @FXML private Button btnEnviar;
    @FXML private Button btnCerrar;

    private String perfilActual;

    public void inicializarChat(String tituloCarta) {
        // Normalización para traducir el nombre del personaje en la cabecera
        String tituloLimpio = tituloCarta.trim();
        String textoNormalizado = Normalizer.normalize(tituloLimpio, Normalizer.Form.NFD);
        String base = textoNormalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase().replace(" ", "_").replace("@", "a").replace("\"", "")
                .replace("(", "").replace(")", "").replace("º", "");

        String tTraducido = base.equals("integrantes_del_comite") ?
                InputNombreController.nombreComiteTemporal :
                LanguageManager.getString("card.title." + base);

        // Traducción de la UI
        lblNombrePersonaje.setText(LanguageManager.getString("chat.talking_to") + tTraducido.toUpperCase());

        if (btnEnviar != null) btnEnviar.setText(LanguageManager.getString("chat.btn.send"));
        if (btnCerrar != null) btnCerrar.setText(LanguageManager.getString("chat.btn.close"));

        txtMensaje.setPromptText(LanguageManager.getString("chat.prompt"));

        // Se usa el título original de la DB para mapear la personalidad
        this.perfilActual = PersonalidadMapper.getPerfil(tituloCarta);

        anadirMensaje("SYSTEM", LanguageManager.getString("chat.system.welcome"), Pos.CENTER_LEFT, "#30B8D0");
    }

    @FXML
    private void enviarMensaje() {
        MusicManager.playClickSound();
        String mensajeUser = txtMensaje.getText();
        if (mensajeUser == null || mensajeUser.trim().isEmpty()) return;

        // Detectamos el idioma actual para pasárselo a la IA
        String idiomaIA = LanguageManager.getCurrentLocale().getLanguage().equals("es") ? "Español" : "English";
        String etiquetaUser = LanguageManager.getCurrentLocale().getLanguage().equals("es") ? "TÚ" : "YOU";

        txtMensaje.clear();
        anadirMensaje(etiquetaUser, mensajeUser, Pos.CENTER_RIGHT, "#55FF55");

        new Thread(() -> {
            // Llamada al servicio con el parámetro de idioma
            String respuesta = AIChatService.enviarMensaje(perfilActual, idiomaIA, mensajeUser);
            Platform.runLater(() -> {
                anadirMensaje("IA", respuesta, Pos.CENTER_LEFT, "#B2EBF2");
            });
        }).start();
    }

    private void anadirMensaje(String emisor, String texto, Pos alineacion, String color) {
        Label lbl = new Label(emisor + ": " + texto);
        lbl.setWrapText(true);
        lbl.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold; -fx-padding: 5;");
        lbl.setMaxWidth(300);

        VBox contenedor = new VBox(lbl);
        contenedor.setAlignment(alineacion);
        vBoxChat.getChildren().add(contenedor);
        Platform.runLater(() -> scrollChat.setVvalue(1.0));
    }

    @FXML
    private void cerrar() {
        MusicManager.playClickSound();
        ((Stage) txtMensaje.getScene().getWindow()).close();
    }
}