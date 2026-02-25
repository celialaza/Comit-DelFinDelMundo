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
import org.example.demo.LOGIC.LanguageManager;

import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

public class RegistroController {

    @FXML private Label lblTituloRanking;
    @FXML private TableView<PartidaDAO.RegistroFila> tablaRegistro;
    @FXML private TableColumn<PartidaDAO.RegistroFila, String> colNombre, colFecha, colResultado;
    @FXML private TextArea areaDetalles;
    @FXML private Button btnFiltroTodas, btnFiltroVictoria, btnFiltroDerrota;
    @FXML private Button btnJugarDeNuevo, btnSalir;

    private PartidaDAO dao = new PartidaDAO();

    @FXML
    public void initialize() {
        MusicManager.playFinalMusic();

        // 1. TRADUCCIONES DE INTERFAZ ESTÁTICA
        if (lblTituloRanking != null) lblTituloRanking.setText(LanguageManager.getString("ranking.title"));

        btnFiltroTodas.setText(LanguageManager.getString("ranking.filter.all"));
        btnFiltroVictoria.setText(LanguageManager.getString("ranking.filter.wins"));
        btnFiltroDerrota.setText(LanguageManager.getString("ranking.filter.losses"));

        if (btnJugarDeNuevo != null) btnJugarDeNuevo.setText(LanguageManager.getString("ranking.btn.playAgain"));
        if (btnSalir != null) btnSalir.setText(LanguageManager.getString("ranking.btn.exit"));

        // 2. TRADUCCIÓN DE CABECERAS DE COLUMNA
        colNombre.setText(LanguageManager.getString("ranking.col.committee"));
        colFecha.setText(LanguageManager.getString("ranking.col.date"));
        colResultado.setText(LanguageManager.getString("ranking.col.result"));

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colResultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));

        // 3. TRADUCCIÓN COMPLETA DE DETALLES (Estadísticas, Opciones y Presidente)
        tablaRegistro.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String detalles = newVal.getDetalles();

                if (LanguageManager.getCurrentLocale().getLanguage().equals("en")) {
                    // Traducir etiquetas de secciones y el estado del presidente
                    detalles = detalles.replace("ESTADÍSTICAS FINALES:", "FINAL STATISTICS:")
                            .replace("Salud:", "Health:")
                            .replace("Bienestar:", "Wellbeing:")
                            .replace("Legado:", "Legacy:")
                            .replace("Recursos:", "Resources:")
                            .replace("OPCIONES ELEGIDAS:", "CHOSEN OPTIONS:")
                            .replace("*** Presidente", "*** President")
                            .replace("(Salvado) ***", "(Saved) ***");

                    // Traducir cada carta de la lista
                    String[] lineas = detalles.split("\n");
                    StringBuilder sb = new StringBuilder();
                    boolean enOpciones = false;

                    for (String l : lineas) {
                        if (l.contains("CHOSEN OPTIONS:")) enOpciones = true;

                        if (enOpciones && l.startsWith("- ")) {
                            String tituloOriginal = l.substring(2).trim();

                            // Caso especial: Seres Queridos
                            if (tituloOriginal.startsWith("LOS SERES QUERIDOS DE ")) {
                                String nombreComite = tituloOriginal.replace("LOS SERES QUERIDOS DE ", "");
                                sb.append("- THE LOVED ONES OF ").append(nombreComite).append("\n");
                                continue;
                            }

                            // Normalizar para encontrar la llave de traducción
                            String base = Normalizer.normalize(tituloOriginal, Normalizer.Form.NFD)
                                    .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                                    .toLowerCase().replace(" ", "_").replace("@", "a").replace("\"", "")
                                    .replace("(", "").replace(")", "").replace("º", "");

                            String trad = LanguageManager.getString("card.title." + base);
                            if (!trad.startsWith("Key not found")) {
                                sb.append("- ").append(trad.toUpperCase()).append("\n");
                            } else {
                                sb.append(l).append("\n");
                            }
                        } else {
                            sb.append(l).append("\n");
                        }
                    }
                    detalles = sb.toString().trim();
                }
                areaDetalles.setText(detalles);
            }
        });

        areaDetalles.setText(LanguageManager.getString("ranking.details.empty"));

        configurarBoton(btnFiltroTodas, "#6c5ce7", "#a29bfe");
        configurarBoton(btnFiltroVictoria, "#00CEC9", "#81ecec");
        configurarBoton(btnFiltroDerrota, "#d63031", "#ff7675");

        cargarDatos("TODAS");
    }

    private void cargarDatos(String filtro) {
        if (dao == null) dao = new PartidaDAO();

        List<PartidaDAO.RegistroFila> listaOriginal = dao.obtenerHistorial(filtro);

        // TRADUCCIÓN DEL RESULTADO EN LA TABLA
        List<PartidaDAO.RegistroFila> listaTraducida = listaOriginal.stream().map(fila -> {
            String resTraducido = fila.getResultado();
            if (LanguageManager.getCurrentLocale().getLanguage().equals("en")) {
                if (resTraducido.equals("VICTORIA")) resTraducido = "VICTORY";
                if (resTraducido.equals("DERROTA")) resTraducido = "DEFEAT";
            }
            return new PartidaDAO.RegistroFila(fila.getNombre(), fila.getFecha(), resTraducido, fila.getDetalles());
        }).collect(Collectors.toList());

        ObservableList<PartidaDAO.RegistroFila> datos = FXCollections.observableArrayList(listaTraducida);
        tablaRegistro.setItems(datos);
    }

    private void configurarBoton(Button boton, String colorNormal, String colorHover) {
        String baseStyle = "-fx-background-radius: 20px; -fx-border-radius: 20px; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-color: rgba(255,255,255,0.2); -fx-border-width: 1px;";
        boton.setStyle(baseStyle + "-fx-background-color: " + colorNormal + ";");
        boton.setOnMouseEntered(e -> boton.setStyle(baseStyle + "-fx-background-color: " + colorHover + ";"));
        boton.setOnMouseExited(e -> boton.setStyle(baseStyle + "-fx-background-color: " + colorNormal + ";"));
    }

    @FXML private void filtrarTodas() { MusicManager.playClickSound(); cargarDatos("TODAS"); }
    @FXML private void filtrarVictorias() { MusicManager.playClickSound(); cargarDatos("VICTORIAS"); }
    @FXML private void filtrarDerrotas() { MusicManager.playClickSound(); cargarDatos("DERROTAS"); }

    @FXML
    private void jugarDeNuevo() {
        MusicManager.playClickSound();
        MusicManager.stopFinalMusic();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/demo/presentacion-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) tablaRegistro.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @FXML
    private void salir() {
        MusicManager.playClickSound();
        System.exit(0);
    }
}