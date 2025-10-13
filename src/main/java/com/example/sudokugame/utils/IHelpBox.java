package com.example.sudokugame.utils;

public interface IHelpBox {
    /**
     * Muestra un cuadro de diálogo de ayuda.
     *
     * @param title   Título de la ventana.
     * @param message Contenido del mensaje.
     * @param header  Texto del encabezado (puede ser null).
     */

    void showHelpBox(String title, String message, String header);
}
