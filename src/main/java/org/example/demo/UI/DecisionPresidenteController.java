package org.example.demo.UI;
import javafx.fxml.FXML;

public class DecisionPresidenteController {

    @FXML
    private void elegirEspacio() {
        System.out.println("DECISIÓN: El Presidente ha elegido +1 ESPACIO.");
        // Aquí iría la lógica para aumentar espacios en la balsa
        // irAlSiguienteNivel();
    }

    @FXML
    private void elegirSalvacion() {
        System.out.println("DECISIÓN: El Presidente ha elegido SALVARSE A SÍ MISMO.");
        // Aquí iría la lógica para asegurar su supervivencia
        // irAlSiguienteNivel();
    }

    // Aquí pondrás el método para cambiar de pantalla más adelante
}
