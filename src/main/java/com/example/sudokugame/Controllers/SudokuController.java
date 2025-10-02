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

    /**
     * Inicializa la vista: crea las celdas y aplica estilos de bloque.
     */
    @FXML
    public void initialize() {
        cells = new StackPane[6][6]; // Inicializar la matriz
        createCells();
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
        // Obtener la celda clickeada
        StackPane currentCell = cells[row][col];

        // Limpiar estados anteriores
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
     * Maneja el clic en el botón de acción.
     * Por ahora, muestra un mensaje en la consola y actualiza el label.
     */
    @FXML
    private void handleButtonClick() {
        System.out.println("¡Botón presionado!");
        if (lblStatus != null) {
            lblStatus.setText("¡Nueva partida iniciada!");
        }
    }

    public void handleButtonClick(ActionEvent actionEvent) {
    }

    /**
     * Maneja el clic en el botón "Nuevo Juego".
     * Muestra un mensaje de confirmación y reinicia el estado visual.
     */
    @FXML
    private void handleNewGame() {
        System.out.println("Nuevo Juego iniciado");
        if (lblStatus != null) {
            lblStatus.setText("Nuevo juego iniciado");
        }
        // Aquí luego conectarás la lógica del modelo
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
