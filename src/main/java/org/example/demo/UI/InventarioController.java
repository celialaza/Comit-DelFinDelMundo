package org.example.demo.UI;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo.MODEL.Carta;

import java.util.List;

public class InventarioController {

    @FXML
    private TilePane gridInventario;

    /**
     * Este método lo llamaremos desde el JuegoController para pasarle los datos.
     */
    public void cargarDatos(List<Carta> inventario) {
        gridInventario.getChildren().clear(); // Limpiamos por si acaso

        if (inventario.isEmpty()) {
            Label vacio = new Label("La mochila está vacía.");
            vacio.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");
            gridInventario.getChildren().add(vacio);
            return;
        }

        // Bucle para crear una mini-ficha por cada carta
        for (Carta c : inventario) {
            crearFichaCarta(c);
        }
    }

    private void crearFichaCarta(Carta c) {
        // 1. Crear el contenedor vertical (VBox)
        VBox ficha = new VBox(5); // 5px de espacio entre foto y texto
        ficha.getStyleClass().add("item-inventario");
        ficha.setPrefWidth(100); // Ancho fijo para que queden iguales
        ficha.setMaxWidth(100);

        // 2. La Imagen
        ImageView img = new ImageView();
        img.setFitWidth(80);
        img.setFitHeight(80);
        img.setPreserveRatio(true);

        try {
            // Cargar imagen segura
            String ruta = c.getImagen();
            if (ruta != null && !ruta.isEmpty()) {
                if (!ruta.startsWith("/")) ruta = "/" + ruta;
                img.setImage(new Image(getClass().getResourceAsStream(ruta)));
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar imagen mini: " + c.getTitulo());
        }

        // 3. El Título
        Label lblTitulo = new Label(c.getTitulo());
        lblTitulo.getStyleClass().add("texto-item-inventario");

        // 4. Añadir todo al grid
        ficha.getChildren().addAll(img, lblTitulo);
        gridInventario.getChildren().add(ficha);
    }

    @FXML
    private void cerrarVentana() {
        // Obtenemos la ventana actual y la cerramos
        Stage stage = (Stage) gridInventario.getScene().getWindow();
        stage.close();
    }
}