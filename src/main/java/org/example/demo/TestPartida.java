package org.example.demo;

import org.example.demo.DB.DataManager;

import java.sql.Connection;
import java.sql.SQLException;

public class TestPartida {
    public static void main(String[] args) {
        System.out.println("Probando conexión a la base de datos...");

        try (Connection conn = DataManager.getDataSource().getConnection()) {
            System.out.println("¡Conexión exitosa!");
            System.out.println("Conectado a: " + conn.getCatalog());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }
    }
    }

