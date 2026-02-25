package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import org.example.demo.LOGIC.LanguageManager;

import java.io.IOException;
import java.util.logging.Logger;

public class PresentacionController {
    Logger logger = Logger.getLogger(PresentacionController.class.getName());

    @FXML private Button btnInstrucciones;
    @FXML private Button btnJugar;
    @FXML private Label lblTitulo;
    @FXML private ImageView imgBandera;

    @FXML
    public void initialize() {
        MusicManager.playIntroMusic();
        MusicManager.playBackgroundMusic();
        actualizarTextosUI();
    }

    @FXML
    private void cambiarIdioma() {
        MusicManager.playClickSound();
        LanguageManager.toggleLanguage();
        actualizarTextosUI();

        String idiomaActual = LanguageManager.getCurrentLocale().getLanguage();
        String rutaImagen = idiomaActual.equals("es") ? "/org/example/demo/reinoUnidoBandera.png" : "/org/example/demo/espanaBandera.png";

        try {
            imgBandera.setImage(new Image(getClass().getResourceAsStream(rutaImagen)));
        } catch (Exception e) {
            logger.severe("Error al cargar la imagen de la bandera: " + e.getMessage());
        }
    }

    private void actualizarTextosUI() {
        btnJugar.setText(LanguageManager.getString("play.button"));
        btnInstrucciones.setText(LanguageManager.getString("instructions.button"));
        if (lblTitulo != null) lblTitulo.setText(LanguageManager.getString("game.title"));
    }

    @FXML
    private void Instrucciones() {
        MusicManager.playClickSound();
        logger.info("Mostrando Historia e Instrucciones con Video...");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(LanguageManager.getString("instructions.title"));
        alert.setHeaderText(LanguageManager.getString("instructions.header"));

        VBox videoContainer = new VBox(0);
        videoContainer.setAlignment(Pos.CENTER);

        MediaView mediaView = null;
        MediaPlayer mediaPlayer = null;

        try {
            String rutaVideo = getClass().getResource("/video/introduccion.mp4").toExternalForm();
            Media media = new Media(rutaVideo);
            mediaPlayer = new MediaPlayer(media);
            mediaView = new MediaView(mediaPlayer);

            mediaView.setFitWidth(560);
            mediaView.setPreserveRatio(true);
            mediaPlayer.setAutoPlay(true);

            HBox controls = new HBox(10);
            controls.setAlignment(Pos.CENTER);
            controls.setPadding(new Insets(5, 10, 5, 10));
            controls.setMinWidth(560);
            controls.setMaxWidth(560);
            controls.setStyle("-fx-background-color: rgba(0,0,0,0.8); -fx-background-radius: 0 0 5 5;");

            Button playPauseBtn = new Button(LanguageManager.getString("instructions.btn.pause"));
            playPauseBtn.setStyle("-fx-min-width: 60; -fx-background-color: #30B8D0; -fx-text-fill: white;");

            MediaPlayer finalPlayer = mediaPlayer;
            playPauseBtn.setOnAction(e -> {
                MusicManager.playClickSound();
                if (finalPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    finalPlayer.pause();
                    playPauseBtn.setText(LanguageManager.getString("instructions.btn.play"));
                } else {
                    finalPlayer.play();
                    playPauseBtn.setText(LanguageManager.getString("instructions.btn.pause"));
                }
            });

            Slider progressSlider = new Slider();
            HBox.setHgrow(progressSlider, Priority.ALWAYS);

            mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                if (!progressSlider.isValueChanging()) {
                    progressSlider.setValue(newTime.toSeconds());
                }
            });

            mediaPlayer.setOnReady(() -> {
                progressSlider.setMax(finalPlayer.getTotalDuration().toSeconds());
            });

            progressSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (progressSlider.isValueChanging()) {
                    finalPlayer.seek(Duration.seconds(newVal.doubleValue()));
                }
            });

            controls.getChildren().addAll(playPauseBtn, progressSlider);
            videoContainer.getChildren().addAll(mediaView, controls);

        } catch (Exception e) {
            System.err.println("No se pudo cargar el video: " + e.getMessage());
        }

        Label labelTexto = new Label(LanguageManager.getString("instructions.history"));
        labelTexto.setWrapText(true);
        labelTexto.setPrefWidth(540);
        labelTexto.setStyle("-fx-text-fill: white; -fx-padding: 20; -fx-font-size: 14px;");

        VBox contenedorContenido = new VBox(10);
        contenedorContenido.setAlignment(Pos.TOP_CENTER);
        if (mediaView != null) contenedorContenido.getChildren().add(videoContainer);
        contenedorContenido.getChildren().add(labelTexto);

        ScrollPane scrollPane = new ScrollPane(contenedorContenido);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(380);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        alert.getDialogPane().setContent(scrollPane);

        // TRADUCCIÓN DEL BOTÓN ACEPTAR
        DialogPane dp = alert.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("/org/example/demo/estilos.css").toExternalForm());
        dp.getStyleClass().add("my-dialog");

        Button btnAceptar = (Button) dp.lookupButton(ButtonType.OK);
        if (btnAceptar != null) {
            btnAceptar.setText(LanguageManager.getString("btn.accept"));
        }

        MediaPlayer finalPlayer = mediaPlayer;
        alert.showAndWait();
        if (finalPlayer != null) finalPlayer.stop();
    }

    @FXML
    private void Jugar() {
        MusicManager.playClickSound();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/demo/modo-juego-view.fxml"));
            btnJugar.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}