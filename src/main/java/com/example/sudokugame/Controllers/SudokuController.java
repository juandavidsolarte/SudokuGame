package com.example.sudokugame.Controllers;

import com.example.sudokugame.model.SudokuBoard;
import com.example.sudokugame.utils.ConfirmBox;
import com.example.sudokugame.utils.HelpBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
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
    private StackPane activeCell = null;
    private StackPane[][] cells; // Matriz para guardar referencias a las celdas. (PARA ACCEDER POR SU POSICION)

    private SudokuBoard model;// (Se crea la instancia del modelo)

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
                //Crear cada celda
                StackPane cell = new StackPane();
                cell.getStyleClass().add("cell");


                // Primera columna: borde izquierdo grueso
                if (col == 0) {
                    cell.getStyleClass().add("border-left");
                }

                // Columnas 2, 5: fin de cada bloque de 3 columnas → borde derecho grueso
                if (col == 2 || col == 5) {
                    cell.getStyleClass().add("border-right");
                }

                // Filas 1, 3, 5: fin de cada bloque de 2 filas → borde inferior grueso
                if (row == 1 || row == 3 || row == 5) {
                    cell.getStyleClass().add("border-bottom");
                }

                int finalRow = row;
                int finalCol = col;

                // Aqui se llama al metodo para manejar el evento de pintar la celda, se le manda las ultimas coordenas.
                cell.setOnMouseClicked(e -> handleCellClick(finalRow, finalCol));

                //  Guarda una referencia a la celda visual StackPane en la matriz cells, para acceder esa celda específica usando sus coordenadas.
                cells[row][col] = cell;
                //  Para agregar la celda al GridPane que se llama "SudokuGrid", aca el orden es columna fila.
                sudokuGrid.add(cell, col, row);
            }
        }
    }
    // ------------------- CREAR CELDA ---------------------------

    // ------------------- COLOREAR CELDA ---------------------------


    /**
     * Maneja el clic en una celda: deselecciona la anterior y selecciona la nueva.
     *
     * @param row Fila de la celda.
     * @param col Columna de la celda.
     */
    private void handleCellClick(int row, int col) {
        // Obtener la celda clickeada.El metodo garantiza que cuando hagas clic en una celda
        //Se deseleccionen todas las celdas anteriores. SE CREA EL MANEJADOR currentCell. (cells: ro,colum son las coordenadas)
        StackPane currentCell = cells[row][col];

        // Limpiar estados anteriores. Esto sirve para que solo una celda a la vez se vea resaltada.(INICIAN EN NULO SE DECLARARON ARRIBA)
        if (activeCell != null) {
            activeCell.getStyleClass().remove("active");
        }

        // Aplicar nuevos estados
        activeCell = currentCell;
        activeCell.getStyleClass().add("active");

        // Permitir que la celda reciba foco de modo que pueda recibir cual tecla se presiono
        activeCell.setFocusTraversable(true);
        activeCell.requestFocus();

        // Registrar listener de teclado para la celda
        activeCell.setOnKeyPressed(this::handleKeyPress);
    }
    // ------------------- COLOREAR CELDA ---------------------------

    /**
     * Maneja la tecla presionada por el usuario asegurandose que sea entre 1-9 y
     * la muestra en la celda activa
     *
     * @param event evento que se genera al presionar una tecla
     */
    private void handleKeyPress(KeyEvent event) {
        //System.out.println("tecla digitada");
        if (activeCell != null && !activeCell.isDisabled()) {
            String tecla = event.getText();

            // Validación segura
            if (!tecla.isEmpty() && Character.isDigit(tecla.charAt(0))) {
                int num = Integer.parseInt(tecla);

                Text cellText;
                if (activeCell.getChildren().isEmpty()) {
                    cellText = new Text(tecla);
                    cellText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: Black;");
                    activeCell.getChildren().add(cellText);
                } else {
                    cellText = (Text) activeCell.getChildren().get(0);
                    cellText.setText(tecla);
                }

                // Se actualiza la matriz con el nuevo numero digitado
                int row = GridPane.getRowIndex(activeCell);
                int col = GridPane.getColumnIndex(activeCell);


                //model.setCell(row, col, num);

                //Verificar el modelo actualizado
                model.printBoard();

                boolean answer = model.isValidPlacement2(num, row, col);
                System.out.println("respuesta = "+ answer);
                // Si el lugar es valido en la matriz se actualiza el modelo con nuel nuevo numero
                if (answer == true) {
                    model.setCell(row, col, num);


                }
                else {
                    lblStatus.setText("Invalid number");
                    model.setCell(row, col, 0);//COMO SE EQUIVOCO SE ACTUALIZA EL MODELO CON 0

                }
                System.out.println("Modelo Actualizado : ");
                model.printBoard();




            }
        }
    }



    /**
     * Maneja el clic en el botón "Nuevo Juego".
     * Muestra un mensaje de confirmación y reinicia el estado visual.
     */
    @FXML
    private void handleNewGame() {
        // SE AGREGA VALIDACION PARA VERIFICAR CONFIRMACION DE INICIAR JUEGO
        ConfirmBox confirmBox = new ConfirmBox();
        boolean confirmed = confirmBox.showConfirmBox(
                "Nuevo Juego",
                null,
                "¿Estás seguro de que quieres empezar un nuevo juego?"
        );

        if (confirmed) {

            System.out.println("Nuevo Juego iniciado");

            //model.generateInitialBoard();
            model.generateInitialBoard2();
            model.printBoard();
            // Actualizar la vista
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    int value = model.getCell(row, col);
                    StackPane cell = cells[row][col];
                    cell.getChildren().clear();

                    if (value != 0) {
                        Text text = new Text(String.valueOf(value));
                        text.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: black;");
                        cell.getChildren().add(text);
                        cell.setDisable(true);
                    } else {
                        cell.setDisable(false);
                    }
                }
            }

            // Limpiar selección
            if (activeCell != null) {
                activeCell.getStyleClass().remove("active");
                activeCell = null;
            }

            if (lblStatus != null) {
                lblStatus.setText("Nuevo juego iniciado");
            }
        }
        // Si no se confirma, no hacemos nada

    }

    /**
     * Maneja el clic en el botón "Pista / Ayuda".
     * Muestra una sugerencia en la celda seleccionada (por ahora, solo un mensaje).
     */
    @FXML
    private void handleHint() {
        if (activeCell != null) {
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
        if (activeCell != null) {
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

    @FXML
    private void handleShowRules() {
        String title = "Sudoku 6x6";
        String header = "Reglas del Juego";
        String message =
                "El objetivo es completar la cuadrícula de 6×6 con números del 1 al 6, cumpliendo:\n\n" +
                        "• Cada FILA debe contener los números 1-6 sin repetir.\n" +
                        "• Cada COLUMNA debe contener los números 1-6 sin repetir.\n" +
                        "• Cada BLOQUE de 2×3 (Hay 6 para el sudoku 6x6), debe contener los números 1-6 sin repetir.\n\n" +
                        "Al inicio, algunos números ya están colocados. ¡No los puedes cambiar!\n" +
                        "Tines un máximo 3 pistas o hints por juego).";

        HelpBox helpBox = new HelpBox();
        helpBox.showHelpBox(title, message, header);
    }
}
