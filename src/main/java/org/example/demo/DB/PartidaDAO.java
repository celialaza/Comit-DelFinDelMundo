package org.example.demo.DB;

import org.example.demo.LOGIC.Partida;
import org.example.demo.MODEL.Carta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {

    public static class RegistroFila {
        private String nombre;
        private String fecha;
        private String resultado;
        private String detalles;

        public RegistroFila(String n, String f, String r, String d) {
            this.nombre = n; this.fecha = f; this.resultado = r; this.detalles = d;
        }
        public String getNombre() { return nombre; }
        public String getFecha() { return fecha; }
        public String getResultado() { return resultado; }
        public String getDetalles() { return detalles; }
    }

    public void guardarPartida(Partida partida) {
        String sqlPartida = "INSERT INTO Historial_Partidas (nombre_comite, resultado, nombre_presidente_salvado) VALUES (?, ?, ?)";
        String sqlInventario = "INSERT INTO Historial_Inventario (partida_id, titulo_carta) VALUES (?, ?)";

        String resultadoTexto = partida.getResultadoFinal().contains("FRACASADO") ? "DERROTA" : "VICTORIA";

        // --- CORRECCIÓN 2C: USAMOS EL NOMBRE DEL PRESIDENTE ---
        String nombrePresi = null;
        if (partida.isPresidenteSalvado()) {
            // AHORA SÍ: Usamos getNombrePresidente()
            String nombreReal = partida.getNombrePresidente();

            // Seguridad por si es nulo o vacío
            if (nombreReal == null || nombreReal.isEmpty()) nombreReal = "Anónimo";

            nombrePresi = "*** Presidente \"" + nombreReal + "\" (Salvado) ***";
        }
        // -----------------------------------------------------

        try (Connection conn = DataManager.getDataSource().getConnection()) {
            conn.setAutoCommit(false);

            int partidaId = 0;
            try (PreparedStatement stmt = conn.prepareStatement(sqlPartida, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, partida.getNombreComite());
                stmt.setString(2, resultadoTexto);
                stmt.setString(3, nombrePresi);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    partidaId = rs.getInt(1);
                }
            }

            if (partidaId > 0) {
                try (PreparedStatement stmtInv = conn.prepareStatement(sqlInventario)) {
                    for (Carta c : partida.getInventario()) {
                        stmtInv.setInt(1, partidaId);
                        stmtInv.setString(2, c.getTitulo());
                        stmtInv.addBatch();
                    }
                    stmtInv.executeBatch();
                }
            }

            conn.commit();
            System.out.println("Partida registrada con éxito.");

        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<RegistroFila> obtenerHistorial(String filtro) {
        List<RegistroFila> lista = new ArrayList<>();
        String sql = "SELECT id, nombre_comite, fecha, resultado, nombre_presidente_salvado FROM Historial_Partidas ORDER BY fecha DESC";

        try (Connection conn = DataManager.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre_comite");
                String fecha = rs.getString("fecha");
                String resultado = rs.getString("resultado");
                String presiSalvado = rs.getString("nombre_presidente_salvado");

                if (filtro.equals("VICTORIAS") && !resultado.equals("VICTORIA")) continue;
                if (filtro.equals("DERROTAS") && !resultado.equals("DERROTA")) continue;

                String detalles = obtenerDetallesInventario(id, presiSalvado);
                lista.add(new RegistroFila(nombre, fecha, resultado, detalles));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    private String obtenerDetallesInventario(int partidaId, String presiSalvado) {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT titulo_carta FROM Historial_Inventario WHERE partida_id = ?";
        try (Connection conn = DataManager.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, partidaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sb.append("- ").append(rs.getString("titulo_carta")).append("\n");
            }
        } catch (SQLException e) { e.printStackTrace(); }

        if (presiSalvado != null && !presiSalvado.isEmpty()) {
            sb.append("\n").append(presiSalvado);
        }
        return sb.toString();
    }
}