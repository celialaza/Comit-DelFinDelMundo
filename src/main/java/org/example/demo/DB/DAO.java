package org.example.demo.DB;

import org.example.demo.MODEL.Carta;

import java.util.List;

public interface DAO {

        /**
         * Carga todas las opciones disponibles del juego.
         * @return una lista de Carta
         */
        List<Carta> cargarCartas();


    }

