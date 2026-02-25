package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.example.demo.LOGIC.LanguageManager;
import org.example.demo.MODEL.Carta;

import java.text.Normalizer; // Necesario para limpiar tildes
import java.util.List;

public class InventarioController {

    @FXML private TilePane gridInventario;
    @FXML private Button btnCerrarVentana; // Asegúrate de tener fx:id="btnCerrarVentana" en el FXML
    @FXML
    private Label title;

    public void cargarDatos(List<Carta> inventario) {
        // TRADUCCIÓN DEL TÍTULO PRINCIPAL Y BOTÓN
        if (title != null) {
            title.setText(LanguageManager.getString("inventory.title"));
        }
        if (btnCerrarVentana != null) {
            btnCerrarVentana.setText(LanguageManager.getString("inventory.btn.close"));
        }

        gridInventario.getChildren().clear();
        if (inventario.isEmpty()) {
            Label vacio = new Label(LanguageManager.getString("inventory.empty"));
            vacio.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");
            gridInventario.getChildren().add(vacio);
            return;
        }
        for (Carta c : inventario) {
            crearFichaCarta(c);
        }
    }

    private void crearFichaCarta(Carta c) {
        VBox ficha = new VBox(8);
        ficha.getStyleClass().add("item-inventario");
        ficha.setMinWidth(120); ficha.setMaxWidth(120);
        ficha.setMinHeight(150); ficha.setMaxHeight(150);
        ficha.setAlignment(Pos.CENTER);

        if (c.getTitulo().equalsIgnoreCase("El Psicópata")) {
            ficha.setStyle("-fx-border-color: #FF3333; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-cursor: hand; -fx-background-color: #455A64;");
        } else {
            ficha.setStyle("-fx-cursor: hand; -fx-background-color: #455A64;");
        }

        ficha.setOnMouseClicked(e -> {
            MusicManager.playClickSound();
            mostrarDetalleCarta(c);
        });

        ImageView img = new ImageView();
        img.setFitWidth(75); img.setFitHeight(75); img.setPreserveRatio(true);
        try {
            String ruta = c.getImagen();
            if (ruta != null && !ruta.isEmpty()) {
                if (!ruta.startsWith("/")) ruta = "/" + ruta;
                img.setImage(new Image(getClass().getResourceAsStream(ruta)));
            }
        } catch (Exception e) {}

        // LÓGICA DE TRADUCCIÓN DE TÍTULOS (Normalización)
        String tituloOriginal = c.getTitulo().trim();
        String textoNormalizado = Normalizer.normalize(tituloOriginal, Normalizer.Form.NFD);
        String llaveBase = textoNormalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase().replace(" ", "_").replace("@", "a")
                .replace("\"", "").replace("(", "").replace(")", "").replace("º", "");

        String tituloAMostrar;
        if (llaveBase.equals("integrantes_del_comite")) {
            tituloAMostrar = InputNombreController.nombreComiteTemporal;
        } else {
            tituloAMostrar = LanguageManager.getString("card.title." + llaveBase);
        }

        Label lblTitulo = new Label(tituloAMostrar.toUpperCase());
        lblTitulo.getStyleClass().add("texto-item-inventario");
        lblTitulo.setMinWidth(100); lblTitulo.setMaxWidth(100);
        lblTitulo.setMinHeight(35); lblTitulo.setMaxHeight(35);
        lblTitulo.setWrapText(true);
        lblTitulo.setTextAlignment(TextAlignment.CENTER);
        lblTitulo.setAlignment(Pos.CENTER);

        ficha.getChildren().addAll(img, lblTitulo);
        gridInventario.getChildren().add(ficha);
    }

    private void mostrarDetalleCarta(Carta c) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.getButtonTypes().add(ButtonType.CLOSE);

        VBox boxCarta = new VBox(15);
        boxCarta.setAlignment(Pos.CENTER);
        boxCarta.setPadding(new Insets(20));
        boxCarta.setStyle("-fx-background-color: #2C3E50;");

        // NORMALIZACIÓN PARA TRADUCIR EL DETALLE
        String tituloOriginal = c.getTitulo().trim();
        String textoNormalizado = Normalizer.normalize(tituloOriginal, Normalizer.Form.NFD);
        String llaveBase = textoNormalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase().replace(" ", "_").replace("@", "a")
                .replace("\"", "").replace("(", "").replace(")", "").replace("º", "");

        String tituloTraducido = (llaveBase.equals("integrantes_del_comite")) ?
                InputNombreController.nombreComiteTemporal :
                LanguageManager.getString("card.title." + llaveBase);

        Label titulo = new Label(tituloTraducido.toUpperCase());
        titulo.setStyle("-fx-text-fill: #30B8D0; -fx-font-size: 20px; -fx-font-weight: bold;");

        ImageView img = new ImageView();
        img.setFitHeight(120); img.setPreserveRatio(true);
        try {
            String ruta = c.getImagen();
            if (ruta != null && !ruta.isEmpty()) {
                if (!ruta.startsWith("/")) ruta = "/" + ruta;
                img.setImage(new Image(getClass().getResourceAsStream(ruta)));
            }
        } catch(Exception e){}

        HBox impacto = new HBox(15);
        impacto.setAlignment(Pos.CENTER);
        impacto.getChildren().addAll(
                crearFilaImpacto("/iconos/heart100.png", c.getSalud()),
                crearFilaImpacto("/iconos/happy100.png", c.getBienestar()),
                crearFilaImpacto("/iconos/book100.png", c.getLegado()),
                crearFilaImpacto("/iconos/settings100.png", c.getRecursos())
        );

        // TRADUCCIÓN DE LA DESCRIPCIÓN
        Label desc = new Label(LanguageManager.getString("card.desc." + llaveBase));
        desc.setWrapText(true);
        desc.setTextAlignment(TextAlignment.CENTER);
        desc.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");

        ScrollPane scrollDesc = new ScrollPane(desc);
        scrollDesc.setFitToWidth(true);
        scrollDesc.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollDesc.setPrefHeight(90);

        boxCarta.getChildren().addAll(titulo, img, impacto, scrollDesc);
        boxCarta.setOnMouseClicked(e -> alert.close());

        DialogPane dp = alert.getDialogPane();
        dp.setContent(boxCarta);
        dp.setPrefSize(380, 420);

        try {
            dp.getStylesheets().add(getClass().getResource("/org/example/demo/estilos.css").toExternalForm());
            dp.getStyleClass().add("my-dialog-limpio");
        } catch (Exception e) {}

        // TRADUCCIÓN DEL BOTÓN ACEPTAR DEL DIÁLOGO
        Button btnOk = (Button) dp.lookupButton(ButtonType.CLOSE);
        if (btnOk != null) btnOk.setText(LanguageManager.getString("btn.accept"));

        alert.showAndWait();
    }

    private HBox crearFilaImpacto(String rutaIcono, int valor) {
        ImageView icon = new ImageView();
        try {
            icon.setImage(new Image(getClass().getResourceAsStream(rutaIcono)));
        } catch(Exception e){}
        icon.setFitHeight(20); icon.setPreserveRatio(true);
        Label lbl = new Label((valor >= 0 ? "+" : "") + valor);
        lbl.setStyle("-fx-text-fill: " + (valor >= 0 ? "#55FF55" : "#FF5555") + "; -fx-font-weight: bold;");
        HBox fila = new HBox(5, icon, lbl);
        fila.setAlignment(Pos.CENTER);
        return fila;
    }

    @FXML
    private void cerrarVentana() {
        MusicManager.playClickSound();
        ((Stage) gridInventario.getScene().getWindow()).close();
    }
}