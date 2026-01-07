package org.example.demo.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.demo.LOGIC.Partida;
import org.example.demo.MODEL.Carta;
import javafx.stage.Modality;
import javafx.stage.StageStyle; // quita bordes de ventana

import java.io.IOException;

public class JuegoController {

    @FXML private Label lblStat1; // Salud
    @FXML private Label lblStat2; // Bienestar
    @FXML private Label lblStat3; // Legado
    @FXML private Label lblStat4; // Recursos

    @FXML private Label lblInventarioContador;
    @FXML private Label lblTituloCarta;
    @FXML private ImageView imgCarta;
    @FXML private Label lblDescripcion;

    private Partida partidaActual;
    private Carta cartaEnPantalla;

    @FXML
    public void initialize() {

    }
    /**
     * Este método recibe la partida desde la pantalla anterior e inicia el juego.
     */
    public void setPartida(Partida partida) {
        this.partidaActual = partida;
        cargarSiguienteCarta();
    }

    /**
     * Este método lee el estado de la Partida y actualiza la pantalla.
     */
    private void cargarSiguienteCarta() {
        // Verificamos si el juego ha terminado
        if (partidaActual.isJuegoTerminado()) {
            mostrarFinDeJuego();
            return;
        }

        // Obtenemos la carta que toca jugar
        this.cartaEnPantalla = partidaActual.getSiguienteCarta();

        if (this.cartaEnPantalla != null) {
            // Actualizamos textos de la carta
            lblTituloCarta.setText(cartaEnPantalla.getTitulo());
            lblDescripcion.setText(cartaEnPantalla.getDescripcion());

            // Actualizamos la Imagen
            try {
                // Asume que la ruta viene como "/org/example/demo/imagenes/carta1.png"
                String ruta = cartaEnPantalla.getImagen();
                if (ruta != null && !ruta.isEmpty()) {
                    // Si la ruta no empieza por /, se lo ponemos
                    if (!ruta.startsWith("/")) ruta = "/" + ruta;
                    imgCarta.setImage(new Image(getClass().getResourceAsStream(ruta)));
                }
            } catch (Exception e) {
                System.err.println("No se pudo cargar la imagen de la carta: " + e.getMessage());
            }
        }

        //Actualizamos las Estadísticas Globales
        lblStat1.setText(partidaActual.getSalud() + "%");
        lblStat2.setText(partidaActual.getBienestar() + "%");
        lblStat3.setText(partidaActual.getLegado() + "%");
        lblStat4.setText(partidaActual.getRecursos() + "%");

        //Actualizamos el contador del Inventario
        int cartasGuardadas = partidaActual.getInventario().size();
        lblInventarioContador.setText(cartasGuardadas + "/10");
    }

    @FXML
    private void accionAnadir() {
        if (cartaEnPantalla != null) {
            System.out.println("Añadiendo carta: " + cartaEnPantalla.getTitulo());

            partidaActual.elegirCarta(cartaEnPantalla);

            cargarSiguienteCarta();
        }
    }

    @FXML
    private void accionDescartar() {
        if (cartaEnPantalla != null) {
            System.out.println("Descartando carta...");

            partidaActual.descartarCarta();

            cargarSiguienteCarta();
        }
    }
    @FXML
    private void abrirInventario() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/inventario-view.fxml"));
            Parent root = loader.load();

            // Pasamos los datos
            InventarioController controller = loader.getController();
            controller.cargarDatos(partidaActual.getInventario());

            // Configurar la ventana modal (Dialog)
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana de atrás
            stage.setTitle("Inventario");
            stage.setScene(new Scene(root));

            //Que no se pueda redimensionar
            stage.setResizable(false);

            stage.showAndWait(); // Espera a que se cierre

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void mostrarFinDeJuego() {
        String resultado = partidaActual.getResultadoFinal();

        // De momento mostramos una alerta simple
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de la Partida");
        alert.setHeaderText("La colonia ha llegado a su destino");
        alert.setContentText(resultado);
        alert.showAndWait();

        // Aquí podrías cerrar la ventana o navegar al menú principal
    }

}