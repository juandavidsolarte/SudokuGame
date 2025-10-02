package com.example.sudokugame.model;

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
    private int hintsLeft = 3;

    /**
     * Constructor por defecto.
     * Inicializa un tablero vacío de 6x6.
     */
    public SudokuBoard() {
        this.board = new int[6][6];
    }
    //----- DECLARACION DE VARIABLES -----

    /**
     * Este método se utiliza al comenzar un nuevo juego para asegurar
     * que no queden rastros del estado anterior.
     * </p>
     * Reinicia el tablero, estableciendo todas las celdas a 0.
     */
    public void reset() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = 0;
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

        Random random = new Random();

        // Recorrer los 6 bloques (3 filas de bloques × 2 columnas de bloques)
        for (int sectionRow = 0; sectionRow < 3; sectionRow++) {
            for (int sectionCol = 0; sectionCol < 2; sectionCol++) {
                int numbersPlaced = 0;
                int attempts = 0;


            }

        }


    }


    // ------ METODO PARA LLENAR TABLERO  ---------------

    // ------ METODO DE VERIFICACION ---------------

    // ------ METODO DE VERIFICACION ---------------






}
