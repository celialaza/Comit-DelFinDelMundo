package org.example.demo.UI;

import javafx.application.Application;
import org.example.demo.LOGIC.AIChatService;

public class Launcher {
    public static void main(String[] args) {
        // Inicializamos el servicio de IA antes de lanzar la aplicación
        AIChatService.inicializar();

        Application.launch(InicioApplication.class, args);
    }
}