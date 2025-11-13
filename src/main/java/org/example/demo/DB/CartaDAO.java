package org.example.demo.DB;

import org.example.demo.MODEL.Carta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartaDAO implements DAO {
        @Override
        public List<Carta> cargarCartas() {
            List<Carta> cartas = new ArrayList<>();
            String sql = "SELECT * FROM cartas";

            try (Connection conn = DataManager.getDataSource().getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Carta carta = new Carta(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("descripcion"),
                            rs.getString("imagen"),
                            rs.getInt("salud"),
                            rs.getInt("bienestar"),
                            rs.getInt("legado"),
                            rs.getInt("recursos")
                    );
                    cartas.add(carta);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            Collections.shuffle(cartas);
            return cartas;
        }
    }

