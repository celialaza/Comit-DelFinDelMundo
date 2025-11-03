package org.example.demo;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties; // Importa la clase Properties
public class DATABASE {

        public static void main(String[] args) {

            // 1. Crear un objeto Properties
            Properties prop = new Properties();
            String propFileName = "db.properties"; // El fichero que creamos en resources/

            // 2. Cargar el fichero de propiedades desde la carpeta "resources"
            try (InputStream inputStream =DATABASE.class.getClassLoader().getResourceAsStream(propFileName)) {

                if (inputStream == null) {
                    System.err.println("¡Error! No se encontró el fichero de propiedades: " + propFileName);
                    return; // Salir si no podemos cargar la config
                }

                // Cargar las propiedades (url, user, password)
                prop.load(inputStream);

            } catch (IOException e) {
                System.err.println("Error al leer el fichero de propiedades: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            // 3. Obtener las variables del fichero (¡Ya no están en el código!)
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password"); // <-- ¡Seguro!

            // 4. Conectar a la BBDD
            System.out.println("Intentando conectar a " + url + "...");

            try (Connection connection = DriverManager.getConnection(url, user, password)) {

                System.out.println("==========================================");
                System.out.println("¡CONEXIÓN EXITOSA!");
                System.out.println("Conectado a: " + connection.getCatalog());
                System.out.println("==========================================");

            } catch (SQLException e) {
                System.out.println("==========================================");
                System.out.println("ERROR AL CONECTAR:");
                e.printStackTrace();
                System.out.println("==========================================");
            }
        }
        }