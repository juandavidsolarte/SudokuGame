package com.example.sudokugame.utils;

/**
 * Interfaz para mostrar cuadros de diálogo de confirmación.
 */
public interface IConfirmBox {
    /**
     * Muestra una alerta de confirmación.
     *
     * @param title   Título de la ventana.
     * @param header  Encabezado (puede ser null).
     * @param message Mensaje de la confirmación.
     * @return true si el usuario acepta, false si cancela.
     */
    boolean showConfirmBox(String title, String header, String message);
}