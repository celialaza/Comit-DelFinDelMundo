package org.example.demo.UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.demo.DB.PartidaDAO;

import java.io.IOException;

public class RegistroController {

    @FXML private TableView<PartidaDAO.RegistroFila> tablaRegistro;
    @FXML private TableColumn<PartidaDAO.RegistroFila, String> colNombre;
    @FXML private TableColumn<PartidaDAO.RegistroFila, String> colFecha;
    @FXML private TableColumn<PartidaDAO.RegistroFila, String> colResultado;

    @FXML private TextArea areaDetalles;

    // Botones del filtro (Ahora los 3 tienen ID)
    @FXML private Button btnFiltroTodas;     // NUEVO
    @FXML private Button btnFiltroVictoria;
    @FXML private Button btnFiltroDerrota;

    private PartidaDAO dao = new PartidaDAO();

    @FXML
    public void initialize() {
        // Configuración de columnas
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colResultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));

        // Listener de selección
        tablaRegistro.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                areaDetalles.setText("OPCIONES ELEGIDAS:\n\n" + newVal.getDetalles());
            }
        });

        // --- PERSONALIZACIÓN DE COLORES ---

        // 1. TODAS: Morado Espacial (Indigo) -> Morado Claro
        configurarBoton(btnFiltroTodas, "#6c5ce7", "#a29bfe");

        // 2. VICTORIAS: Turquesa -> Turquesa Claro
        configurarBoton(btnFiltroVictoria, "#00CEC9", "#81ecec");

        // 3. DERROTAS: Rojo Carmesí -> Rojo Claro
        configurarBoton(btnFiltroDerrota, "#d63031", "#ff7675");

        cargarDatos("TODAS");
    }

    /**
     * Configura el color y el efecto hover manualmente.
     */
    private void configurarBoton(Button boton, String colorNormal, String colorHover) {
        // Estilo base
        String baseStyle = "-fx-background-radius: 20px; -fx-border-radius: 20px; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-color: rgba(255,255,255,0.2); -fx-border-width: 1px;";

        // Color inicial
        boton.setStyle(baseStyle + "-fx-background-color: " + colorNormal + ";");

        // Efectos ratón
        boton.setOnMouseEntered(e -> boton.setStyle(baseStyle + "-fx-background-color: " + colorHover + ";"));
        boton.setOnMouseExited(e -> boton.setStyle(baseStyle + "-fx-background-color: " + colorNormal + ";"));
    }

    @FXML private void filtrarTodas() { cargarDatos("TODAS"); }
    @FXML private void filtrarVictorias() { cargarDatos("VICTORIAS"); }
    @FXML private void filtrarDerrotas() { cargarDatos("DERROTAS"); }

    private void cargarDatos(String filtro) {
        if (dao == null) dao = new PartidaDAO();
        ObservableList<PartidaDAO.RegistroFila> datos = FXCollections.observableArrayList(dao.obtenerHistorial(filtro));
        tablaRegistro.setItems(datos);
        areaDetalles.setText("Selecciona una partida para ver el inventario final.");
    }

    @FXML
    private void jugarDeNuevo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/presentacion-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) tablaRegistro.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void salir() {
        System.exit(0);
    }
}