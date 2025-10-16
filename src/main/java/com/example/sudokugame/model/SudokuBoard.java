package com.example.sudokugame.model;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Clase que representa el tablero de Sudoku 6x6.
 * Implementa la lógica del juego: generación, validación, sugerencias.
 * El tablero está compuesto por 6 filas y 6 columnas, dividido en 6 bloques de 2x3.
 * Cada celda contiene un número entre 1 y 6, o 0 si está vacía.
 */
public class SudokuBoard {

    //----- DECLARACION DE VARIABLES -----
    private int[][] board;
    private int[][] solvedBoard; // Variable para guardar el tablero resuelto


    static int GRID_SIZE = 6;
    static int ROW_BOX_SIZE = 2;
    static int COLUMN_BOX_SIZE = 3;

    /**
     * Constructor por defecto.
     * Inicializa un tablero vacío de 6x6.
     */
    public SudokuBoard() {
        this.board = new int[GRID_SIZE][GRID_SIZE];
        this.solvedBoard = new int[GRID_SIZE][GRID_SIZE];
    }
    //----- DECLARACION DE VARIABLES -----

    /**
     * Limpia el tablero estableciendo todas las celdas a 0.
     */
    private void clearBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = 0;
                solvedBoard[i][j] = 0;
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
        solveBoard();// Genera un tablero que si tiene solucion.

        // Guardar el tablero resuelto en solvedBoard
        solvedBoard = new int[6][6];
        for (int r = 0; r < 6; r++) {
            System.arraycopy(board[r], 0, solvedBoard[r], 0, 6);
        }
        System.out.println("Tablero resuelto:");
        printBoard();



        // Recorrer los 6 bloques (3 filas de bloques × 2 columnas de bloques)
        for (int rowNumber = 0; rowNumber < 6; rowNumber++) {
            //System.out.println("Generando fila "+rowNumber);
            for (int sectionCol = 0; sectionCol < 2; sectionCol++) {

                //----- Inicia  Nueva implementacion(Borrando de tablero)
                int count = 0;
                int numbersAllowed = 2;
                while (count < numbersAllowed) {

                    int colNumber = (int) (Math.random() * 3);

                    if (sectionCol > 0) {
                        colNumber = colNumber + 3;
                    }

                    // Solo poner en 0 si aún no está en 0
                    if (board[rowNumber][colNumber] != 0) {
                        board[rowNumber][colNumber] = 0;
                        count++;
                    }

                }

                // ----- Finaliza  Nueva implementacion(Borrando de tablero)
                /*
                // Aqui revisar que elemento se van a borrar
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

                 */

            }
        }//END FOR SECTION
        // Imprimir el tablero con los números borrados
        System.out.println("\nTablero inicial (con números borrados):");
        printBoard();




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

        for (int i = localBoxRow; i< localBoxRow + ROW_BOX_SIZE ;  i++){
            for (int j= localBoxColumn; j< localBoxColumn + COLUMN_BOX_SIZE;  j++) {
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
    /*
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

     */

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

    /**
     * Esta funcion resuelve automaticamente el sudoku, utiliza funciones creadas anteriormente
     * +
     */
    public boolean solveBoard(){
        for (int row=0; row < GRID_SIZE; row ++){
            for (int column=0; column< GRID_SIZE; column ++){
                if (board [row] [column] == 0){
                    for (int numberToTry = 1 ;numberToTry <= GRID_SIZE; numberToTry ++){
                        if (isValidPlacement2(numberToTry, row, column)){
                            board [row] [column] = numberToTry;
                            //Ahora se hace llamado recursivo
                            if (solveBoard()) {
                                return true;
                            }
                            else{
                                board[row][column] =0;
                            }
                        }

                    }

                    return false;
                }
            }
        }

        return true;
    }//end method

    /*
    public int generateRandomNumber(int max){
        int x =  (int) (Math.random()*max);
        return x;

    }

     */

    //------------------ Metodo para dar la pista-----------------
    /**
     * Proporciona una pista en una celda específica del tablero Sudoku.
     * <p>
     * Este metodo coloca el número correcto (según el tablero resuelto) en la celda indicada
     * por las coordenadas (row, col), siempre y cuando la celda esté vacía (es decir, contenga 0).
     * No modifica el tablero de solución, solo el tablero actual del jugador.
     */
    public boolean giveHint(int row, int col) {
        // Verificar que la posición es válida
        if (row < 0 || row >= 6 || col < 0 || col >= 6) {
            System.out.println("Posición inválida!");
            return false;
        }

        // Verificar que la celda está vacía
        if (board[row][col] != 0) {
            System.out.println("Esta celda ya tiene un número");
            return false;
        }

        // Consultar el número correcto del tablero resuelto
        int correctValue = solvedBoard[row][col];

        // Colocar el número en el board
        board[row][col] = correctValue;

        System.out.println("Pista: Se colocó el número " + correctValue +
                " en la posición [" + row + "][" + col + "]");

        return true;
    }

    /**
     * Revisa todo el tablero actual y devuelve una lista con las posiciones
     * de las celdas que actualmente violan las reglas del Sudoku.
     * <p>
     * Este metodo es útil para verificar la coherencia del tablero después de que
     * el usuario o el sistema (por ejemplo, al dar una pista) modifiquen celdas.
     * </p>
     *
     * @return una lista de arreglos de enteros con dos elementos [fila, columna]
     *         representando las celdas que tienen un valor incorrecto.
     */
    public ArrayList<int[]> revisarErrores() {
        ArrayList<int[]> errores = new ArrayList<>();

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int num = board[row][col];
                if (num == 0) continue; // Ignora celdas vacías

                // Quitar temporalmente el número
                board[row][col] = 0;

                if (!isValidPlacement2(num, row, col)) {
                    errores.add(new int[]{row, col});
                }

                // Restaurar número
                board[row][col] = num;
            }
        }

        return errores;
    }






    //------------------ Metodo para dar la pista-----------------






}
