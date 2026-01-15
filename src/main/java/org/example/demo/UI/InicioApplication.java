package org.example.demo.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioApplication extends Application {

    // TAMAÑO CORREGIDO: 800x600 (El mismo que JuegoView)
    public static final int ANCHO = 800;
    public static final int ALTO = 600;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InicioApplication.class.getResource("/org/example/demo/presentacion-view.fxml"));

        // Forzamos el tamaño de la escena inicial para que coincida con el juego
        Scene scene = new Scene(fxmlLoader.load(), ANCHO, ALTO);

        stage.setTitle("El Comité del Fin del Mundo");
        stage.setScene(scene);

        // Opcional: Impedir que se redimensione para no romper el diseño
        stage.setResizable(false);

        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}