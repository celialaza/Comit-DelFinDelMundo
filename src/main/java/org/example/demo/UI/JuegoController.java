package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.demo.LOGIC.LanguageManager;
import org.example.demo.LOGIC.Partida;
import org.example.demo.MODEL.Carta;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.Normalizer; // Asegúrate de tener este import

public class JuegoController {

    @FXML private Label lblStat1, lblStat2, lblStat3, lblStat4;
    @FXML private ImageView imgStat1, imgStat2, imgStat3, imgStat4;
    @FXML private Label lblInventarioContador, lblTituloCarta, lblDescripcion, lblQuedanOpciones;
    @FXML private ImageView imgCarta;
    @FXML private ImageView imgChatIA;
    @FXML private Label lblEligeme;

    private MediaPlayer mediaPlayer;
    private MediaPlayer loopPlayer;
    private Partida partidaActual;
    private Carta cartaEnPantalla;

    @FXML private ScrollPane descripcion;
    @FXML private VBox boxCartaInterna;
    @FXML private VBox contendorprinci;
    @FXML private VBox cuerpo;
    @FXML private Label lblInventario;
    @FXML private Button btnDescartar;
    @FXML private Button btnAnadir;

    public void setPartida(Partida partida) {
        MusicManager.stopIntroMusic();
        MusicManager.stopBackgroundMusic();
        this.partidaActual = partida;
        iniciarBucleFondo();
        actualizarInterfaz();
        cargarSiguienteCarta();
    }

    private void cargarSiguienteCarta() {
        if (partidaActual.isJuegoTerminado()) {
            gestionarFinDePartida();
            return;
        }

        iniciarMusicaAccion();
        this.cartaEnPantalla = partidaActual.getSiguienteCarta();

        if (this.cartaEnPantalla != null) {
            String tituloOriginal = cartaEnPantalla.getTitulo().trim(); // Añadido trim() para evitar espacios

            // LÓGICA CORREGIDA: Ahora sí eliminamos las tildes de la llave
            String textoNormalizado = Normalizer.normalize(tituloOriginal, Normalizer.Form.NFD);
            String llaveBase = textoNormalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                    .toLowerCase()
                    .replace(" ", "_")
                    .replace("@", "a")
                    .replace("\"", "")
                    .replace("(", "")
                    .replace(")", "")
                    .replace("º", "");

            // Usamos la llave limpia para comparar el caso especial del Comité
            if (llaveBase.equals("integrantes_del_comite")) {
                lblTituloCarta.setText(partidaActual.getNombreComite().toUpperCase());
            } else {
                lblTituloCarta.setText(LanguageManager.getString("card.title." + llaveBase).toUpperCase());
            }

            // Traducir descripción (Ahora buscará "medico" en vez de "médico")
            lblDescripcion.setText(LanguageManager.getString("card.desc." + llaveBase));

            try {
                String ruta = cartaEnPantalla.getImagen();
                if (ruta != null && !ruta.isEmpty()) {
                    if (!ruta.startsWith("/")) ruta = "/" + ruta;
                    InputStream is = getClass().getResourceAsStream(ruta);
                    if (is != null) imgCarta.setImage(new Image(is));
                }
            } catch (Exception e) {}
        }
        actualizarInterfaz();
    }

    @FXML
    private void accionAnadir() {
        if (cartaEnPantalla == null) return;
        if (partidaActual.getInventario().size() >= partidaActual.getCapacidadInventario() &&
                !cartaEnPantalla.getTitulo().equalsIgnoreCase("El Psicópata")) {
            mostrarAlerta(LanguageManager.getString("alert.full.backpack.title"),
                    LanguageManager.getString("alert.full.backpack.msg"));
            return;
        }
        partidaActual.elegirCarta(cartaEnPantalla);
        cargarSiguienteCarta();
    }

    @FXML
    private void accionDescartar() {
        if (cartaEnPantalla == null) return;
        partidaActual.descartarCarta();
        cargarSiguienteCarta();
    }

    private void actualizarInterfaz() {
        actualizarEstadoStat(lblStat1, imgStat1, "heart", partidaActual.getSalud());
        actualizarEstadoStat(lblStat2, imgStat2, "happy", partidaActual.getBienestar());
        actualizarEstadoStat(lblStat3, imgStat3, "book", partidaActual.getLegado());
        actualizarEstadoStat(lblStat4, imgStat4, "settings", partidaActual.getRecursos());

        lblInventarioContador.setText(partidaActual.getInventario().size() + "/" + partidaActual.getCapacidadInventario());

        int queda = partidaActual.getMazoCartas().size() - partidaActual.getCartaActualIndex();
        lblQuedanOpciones.setText(String.format(LanguageManager.getString("game.remaining"), queda));

        if (btnAnadir != null) btnAnadir.setText(LanguageManager.getString("game.add"));
        if (btnDescartar != null) btnDescartar.setText(LanguageManager.getString("game.discard"));
        if (lblEligeme != null) lblEligeme.setText(LanguageManager.getString("game.choose_me"));
        if (lblInventario != null) lblInventario.setText(LanguageManager.getString("game.inventory"));
    }

    private void actualizarEstadoStat(Label label, ImageView icono, String prefijo, int valor) {
        label.setText(valor + "%");
        label.setStyle("-fx-text-fill: " + (valor < 50 ? "#FF3333" : "#263238") + "; -fx-font-weight: bold;");
        String sufijo = (valor == 0) ? "0" : (valor <= 35) ? "25" : (valor <= 65) ? "50" : (valor <= 95) ? "75" : "100";
        try {
            String rutaIcono = "/iconos/" + prefijo + sufijo + ".png";
            InputStream is = getClass().getResourceAsStream(rutaIcono);
            if (is != null) icono.setImage(new Image(is));
        } catch (Exception e) {}
    }

    private void gestionarFinDePartida() {
        partidaActual.aplicarPenalizaciones();
        detenerTodoSonido();

        if (!partidaActual.getMensajePenalizacion().isEmpty()) {
            Alert alertaP = new Alert(Alert.AlertType.WARNING);
            alertaP.setTitle(LanguageManager.getString("ranking.title"));
            alertaP.setHeaderText(LanguageManager.getString("alert.penalty.header"));
            alertaP.setContentText(partidaActual.getMensajePenalizacion());
            aplicarEstiloDialogo(alertaP, "dialog-penalizacion");
            alertaP.showAndWait();
        }

        if (partidaActual.isPresidenteSalvado()) {
            Alert alertaPresi = new Alert(Alert.AlertType.INFORMATION);
            alertaPresi.setTitle(LanguageManager.getString("alert.protocol.title"));
            alertaPresi.setHeaderText(LanguageManager.getString("alert.protocol.header"));

            String nombrePresi = partidaActual.getNombrePresidente();
            String msg = LanguageManager.getCurrentLocale().getLanguage().equals("es") ?
                    "El Presidente \"" + (nombrePresi.isEmpty() ? "Anónimo" : nombrePresi) + "\" ha activado su salvoconducto." :
                    "President \"" + (nombrePresi.isEmpty() ? "Anonymous" : nombrePresi) + "\" has activated their safe-conduct.";

            alertaPresi.setContentText(msg);
            aplicarEstiloDialogo(alertaPresi, "dialog-presidente");
            alertaPresi.showAndWait();
        }

        boolean esVictoria = !partidaActual.getResultadoFinal().contains("FRACASADO");
        try {
            String fxml = esVictoria ? "/org/example/demo/victoria-view.fxml" : "/org/example/demo/derrota-view.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            FinPartidaController controller = loader.getController();
            controller.setPartida(this.partidaActual);
            lblTituloCarta.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void aplicarEstiloDialogo(Alert alerta, String claseCSS) {
        DialogPane dp = alerta.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("/org/example/demo/estilos.css").toExternalForm());
        dp.getStyleClass().add(claseCSS);
        dp.setMinHeight(Region.USE_PREF_SIZE);

        Button btnAceptar = (Button) dp.lookupButton(ButtonType.OK);
        if (btnAceptar != null) {
            btnAceptar.setText(LanguageManager.getString("btn.accept"));
        }
    }

    @FXML
    private void abrirInventario() {
        iniciarMusicaAccion();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/inventario-view.fxml"));
            Parent root = loader.load();
            InventarioController controller = loader.getController();
            controller.cargarDatos(partidaActual.getInventario());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            actualizarInterfaz();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void abrirChatIA() {
        iniciarMusicaAccion();
        if (cartaEnPantalla == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/chat-ia-view.fxml"));
            Parent root = loader.load();
            ChatIAController controller = loader.getController();
            controller.inicializarChat(cartaEnPantalla.getTitulo());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            String prefijo = LanguageManager.getCurrentLocale().getLanguage().equals("es") ? "Chat con " : "Chat with ";
            stage.setTitle(prefijo + cartaEnPantalla.getTitulo());

            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void mostrarAlerta(String t, String m) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(t); a.setHeaderText(null); a.setContentText(m);
        aplicarEstiloDialogo(a, "dialog-penalizacion");
        a.showAndWait();
    }

    private void iniciarBucleFondo() {
        try {
            URL resource = getClass().getResource("/org/example/demo/cancionFondoCarta.mp3");
            if (resource != null) {
                Media media = new Media(resource.toExternalForm());
                loopPlayer = new MediaPlayer(media);
                loopPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                loopPlayer.setVolume(0.5);
                loopPlayer.play();
            }
        } catch (Exception e) { System.err.println("Error fondo: " + e.getMessage()); }
    }

    private void iniciarMusicaAccion() {
        try {
            if (mediaPlayer != null) mediaPlayer.stop();
            URL resource = getClass().getResource("/org/example/demo/cancionCarta.mp3");
            if (resource != null) {
                Media media = new Media(resource.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(1);
                mediaPlayer.play();
            }
        } catch (Exception e) { System.err.println("Error acción: " + e.getMessage()); }
    }

    private void detenerTodoSonido() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        if (loopPlayer != null) {
            loopPlayer.stop();
            loopPlayer.dispose();
            loopPlayer = null;
        }
    }
}