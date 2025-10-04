package com.example.sudokugame.Controllers;

import com.example.sudokugame.model.SudokuBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * Controlador de la vista del juego Sudoku 6x6.
 * Se encarga de manejar la interacción entre la vista y el modelo.
 */
public class SudokuController {

    @FXML
    private GridPane sudokuGrid;
    @FXML
    private Label lblStatus;


    // ------------------ DECLARACION --------------
    private StackPane selectedCell = null;
    private StackPane activeCell = null;
    private StackPane[][] cells; // Matriz para guardar referencias a las celdas

    private SudokuBoard model;

    /**
     * Inicializa la vista: crea las celdas y aplica estilos de bloque.
     */
    @FXML
    public void initialize() {
        model = new SudokuBoard();  // Inicializa el modelo
        cells = new StackPane[6][6]; // Inicializa la matriz de celdas
        createCells();  // Crea la cuadrícula visual
    }
    // ------------------- CREAR CELDA ---------------------------

    /**
     * Crea las 36 celdas del tablero y las agrega al GridPane.
     */
    private void createCells() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                StackPane cell = new StackPane();
                cell.getStyleClass().add("cell");

                // Aplicar borde inferior grueso después de las filas 1 y 3
                // Esto separa los 3 bloques horizontales de 2 filas cada uno
                if (row == 1 || row == 3) {
                    cell.getStyleClass().add("border-bottom");
                }


                // Columna 2: separa el primer bloque del segundo
                if (col == 2) {
                    cell.getStyleClass().add("border-right");
                }

                int finalRow = row;
                int finalCol = col;
                cell.setOnMouseClicked(e -> handleCellClick(finalRow, finalCol));

                // Guardar la celda en la matriz
                cells[row][col] = cell;

                sudokuGrid.add(cell, col, row);
            }
        }
    }
    // ------------------- CREAR CELDA ---------------------------

    /**
     * Maneja el clic en una celda: deselecciona la anterior y selecciona la nueva.
     *
     * @param row Fila de la celda.
     * @param col Columna de la celda.
     */
    private void handleCellClick(int row, int col) {
        // Obtener la celda clickeada.El metodo garantiza que cuando hagas clic en una celda
        //Se deseleccionen todas las celdas anteriores
        StackPane currentCell = cells[row][col];

        // Limpiar estados anteriores. Esto sirve para que solo una celda a la vez se vea resaltada.
        if (activeCell != null) {
            activeCell.getStyleClass().remove("active");
        }
        if (selectedCell != null) {
            selectedCell.getStyleClass().remove("selected");
        }

        // Aplicar nuevos estados
        selectedCell = currentCell;
        activeCell = currentCell;
        selectedCell.getStyleClass().add("selected");
        activeCell.getStyleClass().add("active");
    }



    /**
     * Maneja el clic en el botón "Nuevo Juego".
     * Muestra un mensaje de confirmación y reinicia el estado visual.
     */
    @FXML
    private void handleNewGame() {
        System.out.println("Nuevo Juego iniciado");

        model.generateInitialBoard();       // Genera nuevo tablero válido

        // Mostrar el tablero en la vista
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                int value = model.getCell(row, col);
                StackPane cell = cells[row][col];
                cell.getChildren().clear();

                if (value != 0) {
                    Text text = new Text(String.valueOf(value));
                    text.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: black;");
                    cell.getChildren().add(text);
                    cell.setDisable(true); // No editable
                } else {
                    cell.setDisable(false); // Editable
                }
            }
        }

        // Limpiar selección
        if (selectedCell != null) {
            selectedCell.getStyleClass().remove("selected");
            selectedCell.getStyleClass().remove("active");
            selectedCell = null;
            activeCell = null;
        }

        // Actualizar estado
        if (lblStatus != null) {
            lblStatus.setText("Nuevo juego iniciado");
        }

    }

    /**
     * Maneja el clic en el botón "Pista / Ayuda".
     * Muestra una sugerencia en la celda seleccionada (por ahora, solo un mensaje).
     */
    @FXML
    private void handleHint() {
        if (selectedCell != null) {
            System.out.println("Pista solicitada para la celda seleccionada");
            if (lblStatus != null) {
                lblStatus.setText("Pista aplicada");
            }
            // Aquí luego pondrás: resaltar una celda con un número sugerido
        } else {
            if (lblStatus != null) {
                lblStatus.setText("Selecciona una celda primero");
            }
        }
    }

    @FXML
    private void handleClear() {
        if (selectedCell != null) {
            System.out.println("Celda borrada");
            if (lblStatus != null) {
                lblStatus.setText("Celda borrada");
            }
            // Aquí luego limpiarás el valor en el modelo y en la vista
        } else {
            if (lblStatus != null) {
                lblStatus.setText("Selecciona una celda para borrar");
            }
        }
    }
}
