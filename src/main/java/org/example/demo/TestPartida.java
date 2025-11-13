package org.example.demo;

import org.example.demo.DB.DataManager;
import org.example.demo.LOGIC.Partida;
import org.example.demo.MODEL.Carta;


import java.util.Scanner;

public class TestPartida {
    public static void main(String[] args) {

        //COMPROBACIÓN DEL ESTADO DE LA CONEXIÓN A LA BD
        /*System.out.println("Probando conexión a la base de datos...");

        try (Connection conn = DataManager.getDataSource().getConnection()) {
            System.out.println("¡Conexión exitosa!");
            System.out.println("Conectado a: " + conn.getCatalog());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }*/


        //SIMULACIÓN DE PARTIDA
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- INICIANDO SIMULACIÓN DE PARTIDA ---");

        // 1. Inicia la partida
        // El constructor de Partida llama a tu DAO y carga el mazo
        Partida partida = new Partida();
        System.out.println("¡Partida creada y mazo cargado!");

        // 2. Bucle principal del juego
        while (!partida.isJuegoTerminado()) { // Revisa si el inventario está lleno o se acabó el mazo

            // 3. Obtener la carta actual
            Carta cartaActual = partida.getSiguienteCarta();

            if (cartaActual == null) {
                // Esto no debería pasar si la lógica de isJuegoTerminado es correcta
                System.out.println("Error: No hay más cartas, pero el juego no ha terminado.");
                break;
            }

            // 4. Mostrar estado actual y la carta
            System.out.println("\n-------------------------------------------");
            System.out.println("ESTADO ACTUAL (Inventario: " + partida.getInventario().size() + "/10)");
            System.out.printf("  Salud: %d | Bienestar: %d | Legado: %d | Recursos: %d\n",
                    partida.getSalud(), partida.getBienestar(), partida.getLegado(), partida.getRecursos());

            System.out.println("\nCARTA SOBRE LA MESA:");
            System.out.println("  TÍTULO: " + cartaActual.getTitulo());
            System.out.println("  DESC: " + cartaActual.getDescripcion());

            // 5. Pedir decisión al jugador
            System.out.print("\n¿Qué haces? (1 = Elegir, 2 = Descartar): ");
            String decision = scanner.nextLine();

            // 6. Actuar según la decisión
            if (decision.equals("1")) {
                partida.elegirCarta(cartaActual);
                System.out.println(">> DECISIÓN: Has ELEGIDO '" + cartaActual.getTitulo() + "'");
            } else {
                partida.descartarCarta();
                System.out.println(">> DECISIÓN: Has DESCARTADO '" + cartaActual.getTitulo() + "'");
            }
        }

        // 7. Fin del juego
        System.out.println("\n===========================================");
        System.out.println("--- PARTIDA TERMINADA ---");
        System.out.println("===========================================");

        // 8. Mostrar resultado final
        System.out.println("RESULTADO: " + partida.getResultadoFinal());
        System.out.println("\nESTADÍSTICAS FINALES:");
        System.out.printf("  Salud: %d | Bienestar: %d | Legado: %d | Recursos: %d\n",
                partida.getSalud(), partida.getBienestar(), partida.getLegado(), partida.getRecursos());

        System.out.println("Inventario final: " + partida.getInventario().size() + " cartas.");

        scanner.close();
    }
}


