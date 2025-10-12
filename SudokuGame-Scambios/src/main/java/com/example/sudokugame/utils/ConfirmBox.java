package com.example.sudokugame.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Implementación de IConfirmBox usando JavaFX Alert.
 */
public class ConfirmBox implements IConfirmBox {

    @Override
    public boolean showConfirmBox(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        // Mostrar y esperar respuesta
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}