package org.example.demo.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InicioApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        // Carga la PRIMERA vista de todas: tu FXML de presentación
        FXMLLoader fxmlLoader = new FXMLLoader(InicioApplication.class.getResource("/org/example/demo/presentacion-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // 600x400 como en tu FXML
        stage.setTitle("El Comité del Fin del Mundo");
        stage.setScene(scene);
        stage.show();
    }
}
