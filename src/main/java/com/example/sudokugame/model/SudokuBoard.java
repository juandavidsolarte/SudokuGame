package com.example.sudokugame.model;

import javax.swing.*;
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


    static int GRID_SIZE = 6;
    int ROW_BOX_SIZE = 2;
    int COLUMN_BOX_SIZE = 3;

    /**
     * Constructor por defecto.
     * Inicializa un tablero vacío de 6x6.
     */
    public SudokuBoard() {
        this.board = new int[GRID_SIZE][GRID_SIZE];
        this.initialBoard = new int[GRID_SIZE][GRID_SIZE];
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
    public void generateInitialBoard2() {
        clearBoard();
        Random random = new Random();
        // Recorrer los 6 bloques (3 filas de bloques × 2 columnas de bloques)
        for (int rowNumber = 0; rowNumber < 6; rowNumber++) {
            System.out.println("Generando fila "+rowNumber);
            for (int sectionCol = 0; sectionCol < 2; sectionCol++) {
                // Colocar exactamente 2 números en este bloque


                //int rowRand = (int) (Math.random()*2);
                int colNumber = (int) (Math.random()*3);


                if (sectionCol > 0){
                    colNumber = colNumber + 3;

                }
                boolean answer = false;
                System.out.println("respuesta = "+ answer);

                while (answer == false) {
                     int numRand = (int)  (Math.random()*7);
                     answer = isValidPlacement2(numRand, rowNumber, colNumber);
                     if (answer == true) {
                         board[rowNumber][colNumber] = numRand;

                     }
                }

            }
        }//END FOR SECTION
    }//end METHOD




    // ------ METODO PARA LLENAR TABLERO  ---------------

    // ------ METODO DE VERIFICACION ---------------
    /**
     * Verifica si un número puede colocarse en una posición específica.
     * @return true si el número es válido, false en caso contrario
     */

    // --- VALIDA QUE EL NUMERO A INSERTAR NO EXISTA EN LA FILA
    public  boolean  isNumberRow( int number, int row){
        for (int i=0; i< GRID_SIZE; i++){
            if (board[row][i] == number){
                return true;
            }
        }//end for

        return false;
    }

    // --- VALIDA QUE EL NUMERO A INSERTAR NO EXISTA EN LA COLUMNA
    public boolean  isNumberColumn( int number, int column){
        for (int i=0; i< GRID_SIZE; i++){
            if (board[i][column] == number){
                return true;
            }
        }//end for

        return false;
    }

    //Verifica si el numero está en sub-matriz
    public boolean  isNumberBox( int number, int row, int column){
        int localBoxRow = row - (row % ROW_BOX_SIZE);
        int localBoxColumn = column -  (column % COLUMN_BOX_SIZE);

        for (int i = localBoxRow; i< localBoxRow + 1 ;  i++){
            for (int j= localBoxColumn; j< localBoxColumn + 3;  j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }//end for

        return false;
    }

    /*--- ESTE METODO QUE USA LOS METODOS ANTERIORES PARA VERIFICAR SI EL NUMERO A INSERTAR ESTA EN UN LUGAR CORRECTO TENIENDO CUENTA LAS REGLAS DE JUEGO
    Retorna true, si el numero a insertar no esta ni en la fila ni en la clomuna ni e el bloque, returna false en otro caso.
     */
    public boolean isValidPlacement2( int number, int row, int column){

        //System.out.println("\nNUMBER : "+number + " Fila : " + row + " Column : " + column+"\n");

        boolean numberInRow = isNumberRow(number, row);// VERIFICA EN LA FILA
        boolean numberInColumn = isNumberColumn(number, column); // VERIFICA EN LA COLUMNA
        boolean numberInBox = isNumberBox(number, row, column); // VERIFICA EN EL BLOQUE

        // System.out.println("numberInRow : " + numberInRow + " numberInColumn : " + numberInColumn + " numberInBox : " + numberInBox);

        // si no hay numero en la fila y no hay numero en la columna y no hay numero en la sub-matriz
        return !numberInRow &&
                !numberInColumn &&
                !numberInBox;
        /*
        if (numberInRow == false && numberInColumn == false &&  numberInBox == false) {
            return true;
        }
        else  {
            return false;
            }

         */

    }






   // -----------------
    public boolean validateInitialBoard(int numberToTry){
        for (int row=0; row < GRID_SIZE; row ++){
            for (int column=0; column< GRID_SIZE; column ++){
                if (board [row] [column] == 0){
                        if (isValidPlacement2(numberToTry, row, column)){
                            board [row] [column] = numberToTry;
                            return true;

                        }




                }
            }
        }
        return false;

    }//end method

    // ------ METODO DE VERIFICACION ---------------

    // ------ METODO PARA ENVIAR AL CONTROLLER ---------------

    /**
     * Obtiene el valor almacenado en una celda específica del tablero.
     * <p>
     * Este metodo permite al controlador acceder de forma segura al estado
     *
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

    }


    public void printBoard() {
        System.out.println("\n===== NUEVO ESTADO DEL TABLERO =====");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();

        }
    }

    public int[][] getBoard() {
        return board;
    }
}
