package com.example.sudokugame.utils;
import javafx.scene.control.Alert;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Implementación de IHelpBox usando JavaFX Alert.
 * Muestra ventanas de información para reglas, ayuda, etc.
 */

public class HelpBox implements IHelpBox {
    @Override
    public void showHelpBox(String title, String message, String header) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);

        // Crear un TextArea para el mensaje
        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(200); // Altura máxima del área de texto

        // Envolver en un ScrollPane por si el texto es muy largo
        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(200);


        VBox content = new VBox(new Label("Rules:"), scrollPane);
        content.setSpacing(10);
        alert.getDialogPane().setContent(content);


        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setMinWidth(500);   // Ancho mínimo
        stage.setMinHeight(300);  // Alto mínimo

        alert.showAndWait();
    }

}
