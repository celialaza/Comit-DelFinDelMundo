package org.example.demo.MODEL;



import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Carta {
    private int id;
    private String titulo;
    private String descripcion;
    private String imagen;
    private int salud;
    private int bienestar;
    private int legado;
    private int recursos;


    }

