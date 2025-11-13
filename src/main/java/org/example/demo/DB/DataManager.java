package org.example.demo.DB;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

//clase que maneja la conexión a la Base de datos
public class DataManager {

        private static DataSource datasource;

        private DataManager() {}

        public static DataSource getDataSource() {
            //patrón singleton
            if(datasource == null) {
                var ds = new MysqlDataSource();
                // Cargar datos desde db.properties
                try (InputStream input = DataManager.class.getClassLoader()
                        .getResourceAsStream("db.properties")) {

                    if (input == null) {
                        throw new RuntimeException("No se encontró el archivo db.properties");
                    }

                    Properties prop = new Properties();
                    prop.load(input);

                    ds.setURL(prop.getProperty("db.url"));
                    ds.setUser(prop.getProperty("db.user"));
                    ds.setPassword(prop.getProperty("db.password"));
                    ds.setAllowMultiQueries(true);

                } catch (IOException | SQLException e) {
                    throw new RuntimeException("Error al configurar el DataSource", e);
                }

                try {
                    ds.setAllowMultiQueries(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                datasource = ds;

            }
            return datasource;
        }

    }




