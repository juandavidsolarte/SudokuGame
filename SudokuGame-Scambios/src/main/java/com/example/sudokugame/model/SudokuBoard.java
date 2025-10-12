package com.example.sudokugame.model;

import java.util.Arrays;
import java.util.Random;

/**
 * Clase que representa el tablero de Sudoku 6x6.
 * Implementa la lógica del juego: generación, validación, sugerencias.
 * El tablero está compuesto por 6 filas y 6 columnas, dividido en 6 bloques de 2x3.
 * Cada celda contiene un número entre 1 y 6, o 0 si está vacía.
 */
public class SudokuBoard {

    //----- DECLARACION DE VARIABLES -----
    private int[][] board;
    private int[][] initialBoard; // Para marcar las celdas iniciales las no editables
    private int hintsLeft = 3;

    /**
     * Constructor por defecto.
     * Inicializa un tablero vacío de 6x6.
     */
    public SudokuBoard() {
        this.board = new int[6][6];
        this.initialBoard = new int[6][6];
    }
    //----- DECLARACION DE VARIABLES -----

    /**
     * Limpia el tablero estableciendo todas las celdas a 0.
     */
    private void clearBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = 0;
                initialBoard[i][j] = 0;
            }
        }
    }

    // ------ METODO PARA LLENAR TABLERO  ---------------

    /**
     * Genera un tablero inicial válido con exactamente 2 números predefinidos
     * en cada uno de los 6 bloques de 2x3.
     * <p>
     * El algoritmo:
     * 1.Recorre cada bloque de 3 filas de bloques × 2 columnas de bloques.
     * 2. Para cada bloque, intenta colocar 2 números aleatorios válidos.
     * 3. Usa retroceso implícito: si una posición no es válida, prueba otra.
     */
    public void generateInitialBoard() {
        clearBoard();
        Random random = new Random();
        // Recorrer los 6 bloques (3 filas de bloques × 2 columnas de bloques)
        for (int sectionRow = 0; sectionRow < 3; sectionRow++) {
            for (int sectionCol = 0; sectionCol < 2; sectionCol++) {

                // Colocar exactamente 2 números en este bloque
                int numbersPlaced = 0;
                int attempts = 0;
                int maxAttempts = 100; // Evitar bucles infinitos

                while (numbersPlaced < 2 && attempts < maxAttempts) {
                    attempts++;

                    // Calcular posición dentro del bloque 2x3
                    int localRow = random.nextInt(2); // 0 o 1 (2 filas por bloque)
                    int localCol = random.nextInt(3); // 0, 1 o 2 (3 columnas por bloque)

                    // Convertir a posición global en el tablero 6x6
                    int row = sectionRow * 2 + localRow;
                    int col = sectionCol * 3 + localCol;

                    // Verificar si la celda ya está ocupada
                    if (board[row][col] != 0) {
                        continue; // Ya tiene un número, intentar otra posición
                    }

                    // Generar un número aleatorio del 1 al 6
                    int number = random.nextInt(6) + 1;

                    // Verificar si el número es válido en esta posición
                    if (isValidPlacement(row, col, number)) {
                        board[row][col] = number;
                        initialBoard[row][col] = number; // Marcar como número inicial
                        numbersPlaced++;
                    }
                }
            }
        }


    }
    // ------ METODO PARA LLENAR TABLERO  ---------------

    // ------ METODO DE VERIFICACION ---------------
    /**
     * Verifica si un número puede colocarse en una posición específica.
     *
     * @param row Fila de la celda
     * @param col Columna de la celda
     * @param num Número a colocar (1-6)
     * @return true si el número es válido, false en caso contrario
     */
    private boolean isValidPlacement(int row, int col, int num) {
        // Verificar fila
        for (int c = 0; c < 6; c++) {
            if (board[row][c] == num) {
                return false;
            }
        }

        // Verificar columna
        for (int r = 0; r < 6; r++) {
            if (board[r][col] == num) {
                return false;
            }
        }

        // Verificar bloque 2x3
        int sectionRowStart = (row / 2) * 2; // Inicio de fila del bloque
        int sectionColStart = (col / 3) * 3; // Inicio de columna del bloque

        for (int r = sectionRowStart; r < sectionRowStart + 2; r++) {
            for (int c = sectionColStart; c < sectionColStart + 3; c++) {
                if (board[r][c] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    // ------ METODO DE VERIFICACION ---------------

    // ------ METODO PARA ENVIAR AL CONTROLLER ---------------

    /**
     * Obtiene el valor almacenado en una celda específica del tablero.
     * <p>
     * Este metodo permite al controlador acceder de forma segura al estado
     * interno del modelo sin exponer la matriz completa.
     * </p>
     * <ul>
     *   <li>Devuelve {@code 0} si la celda está vacía.</li>
     *   <li>Devuelve un valor entre {@code 1} y {@code 6} si la celda contiene un número.</li>
     * </ul>
     *
     * @param row Fila de la celda (0 a 5).
     * @param col Columna de la celda (0 a 5).
     * @return Valor de la celda: 0 (vacía) o 1–6 (número colocado).
     * @throws IndexOutOfBoundsException si {@code row} o {@code col} están fuera del rango [0, 5].
     */
    public int getCell(int row, int col) {
        return board[row][col];
    }

    // ------ METODO PARA ENVIAR AL CONTROLLER ---------------

    /**
     * Asigna un valor dado a una celda
     * @param row Fila de la celda (0 a 5).
     * @param col Columna de la celda (0 a 5).
     * @param value Valor al que se le asigna a la celda.
     */
    public void setCell(int row, int col, int value) {
        board[row][col] = value;
        //System.out.print(Arrays.deepToString(board));
    }






}
