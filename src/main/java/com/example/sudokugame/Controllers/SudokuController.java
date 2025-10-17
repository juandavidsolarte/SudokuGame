package com.example.sudokugame.Controllers;

import com.example.sudokugame.model.SudokuBoard;
import com.example.sudokugame.utils.ConfirmBox;
import com.example.sudokugame.utils.HelpBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.Optional;

/**
 * Controlador de la vista del juego Sudoku 6x6.
 * Se encarga de manejar la interacción entre la vista y el modelo.
 * @author Juan David Solarte
 * @author Sergio Ernesto Patiño
 */
public class SudokuController {

    @FXML
    private GridPane sudokuGrid;
    @FXML
    private Label lblStatus;
    @FXML
    private Button btnHint;
    @FXML
    private Label lblHintsRemaining;


    // ------------------ DECLARACION --------------
    private StackPane activeCell = null;
    private StackPane[][] cells; // Matriz para guardar referencias a las celdas. (PARA ACCEDER POR SU POSICION)
    private final int boardSize = 6; //final palabra clave que se utiliza para declarar constantes  para que no se les pueda reasignar un nuevo valor para que no puedan ser sobrescritos en una subclase
    private SudokuBoard model;// (Se crea la instancia del modelo)
    private int hintsLeft = 3;

    // Variables para guardar la celda activa actualmente
    private int activeRow = -1;
    private int activeCol = -1;

    /**
     * Inicializa la vista: crea las celdas y aplica estilos de bloque.
     */
    @FXML
    public void initialize() {
        model = new SudokuBoard();  // Inicializa el modelo
        cells = new StackPane[6][6]; // Inicializa la matriz de celdas
        createCells();  // Crea la cuadrícula visual
        // Inicializar el botón de pista como desactivado
        if (btnHint != null) {
            btnHint.setDisable(true);
        }

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
    public void handleCellClick(int row, int col) {

        // Guardar las coordenadas de la celda activa
        activeRow = row;
        activeCol = col;
        // Activar el botón de hint cuando se selecciona una celda
        if (btnHint != null && hintsLeft > 0) {
            btnHint.setDisable(false);
        }
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
        System.out.println("Tecla digitada: " + event.getCode());
        if (activeCell != null && !activeCell.isDisabled()) {
            String tecla = event.getText();


            //  Detectar si se presiona la tecla back space
            if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
                // Eliminar el texto de la celda
                if (!activeCell.getChildren().isEmpty()) {
                    activeCell.getChildren().clear(); // Borra el número mostrado
                }

                // Actualizar el modelo a 0 (celda vacía)
                int row = GridPane.getRowIndex(activeCell);
                int col = GridPane.getColumnIndex(activeCell);
                model.setCell(row, col, 0);

                lblStatus.setText("Número eliminado");
                //System.out.println("Número eliminado en fila " + row + ", columna " + col);
                model.printBoard();
                return; // salir para no ejecutar la lógica de los números
            }

            // Validar que sea número dentro del rango permitido
            if (!tecla.isEmpty() && Character.isDigit(tecla.charAt(0))) {
                int num = Integer.parseInt(tecla);


                if (num < 1 || num > boardSize) {
                    lblStatus.setText("Only numbers allowed from 1 to  " + boardSize);
                    return;
                }


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

                model.setCell(row, col, 0);
                boolean answer = model.isValidPlacement2(num, row, col);
                System.out.println("respuesta = "+ answer);

                // Si el lugar es valido en la matriz se actualiza el modelo con nuel nuevo numero
                if (answer == true) {
                    lblStatus.setText("");
                    model.setCell(row, col, num);
                    cellText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: #6338FF;");

                    // Verificar si el tablero está completo después de colocar el número
                    System.out.println("Verificando si está completo...");
                    if (model.isBoardComplete()) {

                        lblStatus.setText("¡Sudoku Completed!");
                        lblStatus.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: Green;");

                    } else {
                        System.out.println("Aún faltan celdas"); // Debug
                    }


                }
                else {
                    lblStatus.setText("Invalid number");
                    cellText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: Red;");

                }
                System.out.println("Modelo Actualizado : ");
                model.printBoard();
            }
        }
    }// End Method



    /**
     * Maneja el clic en el botón "Nuevo Juego".
     * Muestra un mensaje de confirmación y reinicia el estado visual.
     */
    @FXML
    private void handleNewGame() {
        // SE AGREGA VALIDACION PARA VERIFICAR CONFIRMACION DE INICIAR JUEGO
        ConfirmBox confirmBox = new ConfirmBox();
        boolean confirmed = confirmBox.showConfirmBox(
                "New Game",
                null,
                "¿Are you sure do you want to create a new game?"
        );

        if (confirmed) {

            System.out.println("New Game initiated");
            // Reiniciar el contador de pistas
            hintsLeft = 3;
            updateHintsLabel();

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
                lblStatus.setText("New Game initiated");
            }

            //Resetear la cantidad de pistas
            hintsLeft=3;

            // Resetear las coordenadas de la celda activa
            activeRow = -1;
            activeCol = -1;
            // Desactivar el botón de hint hasta que se seleccione una celda
            if (btnHint != null) {
                btnHint.setDisable(true);
            }


        }
        // Si no se confirma, no hacemos nada

    }
    // ---------------------------pista -------------

    /**
     * Gestiona la solicitud de una pista y revalida el tablero sin eliminar los errores previos.
     * <p>
     * Este metodo coloca el número correcto en la celda seleccionada según el tablero resuelto.
     * Luego revisa el tablero para detectar nuevas inconsistencias, pero sin borrar
     * las celdas previamente marcadas en rojo.
     * </p>
     */
    @FXML
    private void handleHint() {
        // Verificar si no quedan pistas
        if (hintsLeft <= 0) {
            if (btnHint != null) {
                btnHint.setDisable(true);
            }
            if (lblStatus != null) {
                lblStatus.setText("No quedan pistas disponibles");
            }
            return;
        }

        // Verificar si hay una celda seleccionada
        if (activeCell != null && activeRow != -1 && activeCol != -1) {
            System.out.println("Pista solicitada para la celda seleccionada");

            // Verificar si la celda ya tiene un número
            if (model.getCell(activeRow, activeCol) != 0) {
                if (lblStatus != null) {
                    lblStatus.setText("Esta celda ya tiene un número");
                }
                return;
            }

            // Llamar al metodo giveHintAt del modelo SudokuBoard
            boolean success = model.giveHint(activeRow, activeCol);

            if (success) {
                // Obtener el valor correcto del tablero resuelto
                int correctValue = model.getBoard()[activeRow][activeCol];

                // Actualizar el modelo
                model.setCell(activeRow, activeCol, correctValue);

                // Actualizar la visualización
                updateCellDisplay(activeRow, activeCol, correctValue);

                // Bloquear la celda (ya no es editable)
                StackPane cell = cells[activeRow][activeCol];
                cell.setDisable(true);

                // Limpiar selección
                if (activeCell != null) {
                    activeCell.getStyleClass().remove("active");
                    activeCell = null;
                }
                activeRow = -1;
                activeCol = -1;

                // Actualizar contador de pistas
                hintsLeft -= 1;
                updateHintsLabel();

                // Desactivar botón si no quedan pistas
                if (btnHint != null && hintsLeft <= 0) {
                    btnHint.setDisable(true);
                }

                // Verificar errores
                var errores = model.revisarErrores();
                if (!errores.isEmpty()) {
                    for (int[] pos : errores) {
                        int r = pos[0];
                        int c = pos[1];
                        if (!cells[r][c].isDisabled()) {
                            marcarErrorVisual(r, c);
                        }
                    }
                    lblStatus.setText("Algunos números siguen siendo inválidos");
                } else {
                    lblStatus.setText("Pista aplicada: número " + correctValue);
                }

            } else {
                if (lblStatus != null) {
                    lblStatus.setText("No se pudo generar una pista para esta celda");
                }
            }

        } else {
            if (lblStatus != null) {
                lblStatus.setText("Selecciona una celda vacía primero");
            }
        }
    }

    /**
     * Marca una celda específica como errónea, cambiando el color de su texto a rojo.
     * <p>
     * Si la celda ya estaba marcada previamente, este metodo no la sobrescribe;
     * simplemente la deja roja. Esto permite conservar los errores antiguos.
     * </p>
     *
     * @param row la fila de la celda que presenta el error.
     * @param col la columna de la celda que presenta el error.
     */
    private void marcarErrorVisual(int row, int col) {
        StackPane cell = cells[row][col];
        if (!cell.getChildren().isEmpty()) {
            Text text = (Text) cell.getChildren().get(0);

            // Solo marca en rojo si aún no lo está
            if (!text.getStyle().contains("Red")) {
                text.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: Red;");
            }
        }
    }



    /**
     * Metodo para actualizar la visualización de una celda específica
     * @param row fila de a la que pertenece la celda
     * @param col columna a la que pertenece la celda
     * @param value valor que posee la celda
     */
    private void updateCellDisplay(int row, int col, int value) {
        StackPane cell = cells[row][col];
        // Limpiar el contenido anterior
        cell.getChildren().clear();

        // Crear un nuevo Text con el valor
        if (value != 0) {
            Text text = new Text(String.valueOf(value));
            text.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            // Agregar clase CSS para diferenciar números de pista
            text.getStyleClass().add("hint-number");

            cell.getChildren().add(text);
        }
    }


    // ---------------------------pista -------------


    /**
     * Metodo que maneja la visualizacion de las reglas.
     */
    @FXML
    private void handleShowRules() {
        String title = "Sudoku 6x6";
        String header = "Game Rules";
        String message =
                "The objective is to complete the 6×6 grid with numbers from 1 to 6, following these rules:\n\n" +
                        "• Each ROW must contain the numbers 1–6 without repetition.\n" +
                        "• Each COLUMN must contain the numbers 1–6 without repetition.\n" +
                        "• Each BLOCK of 2×3 (there are 6 blocks in a 6×6 Sudoku) must contain the numbers 1–6 without repetition.\n\n" +
                        "At the beginning, some numbers are already placed — you cannot change them.!\n" +
                        "You have a maximum of 3 hints per game.";

        HelpBox helpBox = new HelpBox();
        helpBox.showHelpBox(title, message, header);
    }

    //-----------METODO PARA RESOLVER TABLERO-----
   /*
    public void handleSolve(ActionEvent actionEvent) {
        System.out.println("IMPRIMIENDO VIEJO : ");
        model.printBoard();

        model.solveBoard();
        System.out.println("\n RESUELTO : \n");
        model.printBoard();

    }

    */
    //-----------METODO PARA RESOLVER TABLERO-----

    /**
     * Actualiza el texto del label de pistas restantes.
     */
    private void updateHintsLabel() {
        if (lblHintsRemaining != null) {
            lblHintsRemaining.setText(String.valueOf(hintsLeft));
        }
    }


}
