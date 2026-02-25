package org.example.demo.DB;

import org.example.demo.LOGIC.Partida;
import org.example.demo.MODEL.Carta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {

    public static class RegistroFila {
        private String nombre, fecha, resultado, detalles;
        public RegistroFila(String n, String f, String r, String d) {
            this.nombre = n; this.fecha = f; this.resultado = r; this.detalles = d;
        }
        public String getNombre() { return nombre; }
        public String getFecha() { return fecha; }
        public String getResultado() { return resultado; }
        public String getDetalles() { return detalles; }
    }

    public void guardarPartida(Partida partida) {
        String sqlPartida = "INSERT INTO Historial_Partidas (nombre_comite, resultado, nombre_presidente_salvado, salud, bienestar, legado, recursos) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlInventario = "INSERT INTO Historial_Inventario (partida_id, titulo_carta) VALUES (?, ?)";

        String res = partida.getResultadoFinal().contains("FRACASADO") ? "DERROTA" : "VICTORIA";
        String presi = partida.isPresidenteSalvado() ? "*** Presidente \"" + partida.getNombrePresidente() + "\" (Salvado) ***" : null;

        try (Connection conn = DataManager.getDataSource().getConnection()) {
            conn.setAutoCommit(false);
            int id = 0;
            try (PreparedStatement stmt = conn.prepareStatement(sqlPartida, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, partida.getNombreComite());
                stmt.setString(2, res);
                stmt.setString(3, presi);
                stmt.setInt(4, partida.getSalud()); stmt.setInt(5, partida.getBienestar());
                stmt.setInt(6, partida.getLegado()); stmt.setInt(7, partida.getRecursos());
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) id = rs.getInt(1);
            }

            if (id > 0) {
                try (PreparedStatement stmtInv = conn.prepareStatement(sqlInventario)) {
                    for (Carta c : partida.getInventario()) {
                        String tit = c.getTitulo();
                        // Personalización del historial (Comité e Integrantes)
                        if (tit.equalsIgnoreCase("Integrantes del Comité")) {
                            tit = partida.getNombreComite();
                        } else if (tit.equalsIgnoreCase("Seres Queridos")) {
                            tit = "LOS SERES QUERIDOS DE " + partida.getNombreComite();
                        }

                        stmtInv.setInt(1, id);
                        stmtInv.setString(2, tit.toUpperCase());
                        stmtInv.addBatch();
                    }
                    stmtInv.executeBatch();
                }
            }
            conn.commit();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<RegistroFila> obtenerHistorial(String filtro) {
        List<RegistroFila> lista = new ArrayList<>();
        String sql = "SELECT * FROM Historial_Partidas ORDER BY fecha DESC";
        try (Connection conn = DataManager.getDataSource().getConnection();
             ResultSet rs = conn.createStatement().executeQuery(sql)) {
            while (rs.next()) {
                String res = rs.getString("resultado");
                if (filtro.equals("VICTORIAS") && !res.equals("VICTORIA")) continue;
                if (filtro.equals("DERROTAS") && !res.equals("DERROTA")) continue;

                String det = "ESTADÍSTICAS FINALES:\n" +
                        "Salud: " + rs.getInt("salud") + "% | Bienestar: " + rs.getInt("bienestar") + "%\n" +
                        "Legado: " + rs.getInt("legado") + "% | Recursos: " + rs.getInt("recursos") + "%\n" +
                        "------------------------------------------\n" +
                        "OPCIONES ELEGIDAS:\n" +
                        obtenerDetallesInventario(rs.getInt("id"), rs.getString("nombre_presidente_salvado"));

                lista.add(new RegistroFila(rs.getString("nombre_comite"), rs.getString("fecha"), res, det));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    private String obtenerDetallesInventario(int id, String presi) {
        StringBuilder sb = new StringBuilder();
        try (Connection conn = DataManager.getDataSource().getConnection();
             PreparedStatement st = conn.prepareStatement("SELECT titulo_carta FROM Historial_Inventario WHERE partida_id = ?")) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) sb.append("- ").append(rs.getString("titulo_carta")).append("\n");
        } catch (SQLException e) {}
        if (presi != null) sb.append("\n").append(presi);
        return sb.toString();
    }
}