import java.util.*;
import java.util.Random;

/**
 * Sudoku - this program creates a simple version of the classic Sudoku game. The game begins by asking the user the level of difficulty
 * they would like the program to run on. After the user has chosen, the user is able to play by choosing a row and col to indicate where
 * they would like to place their numbers. The user will then be notified when they have correctly solved the puzzle
 * 
 * Author - Phuocan Nguyen
 * 
 */


public class Sudoku extends Sudoku_Puzzle
{
    public static int NUM_ROWS = 9;
    public static int NUM_COLS = 9;
    public static int BLOCK_SIZE = 2;
    public static int HARD_THRESHOLD = 2;

    public static void main (String[] str)
    {
        //this generates the sudoku puzzle from a seperate class
        Sudoku_Puzzle puzzle = new Sudoku_Puzzle();
        int[][] key = puzzle.puzzle_generator();
        
        int[][] board = new int[NUM_ROWS][NUM_COLS]; //another copy of the key, which contain the holes of the board
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                board[row][col] = key[row][col];
            }
        }

        System.out.println("Welcome to Sudoku!");
        int level = 4;
        while (level > 3){
            System.out.println("Which level you would like to play?");
            System.out.println("Please enter 1 for easy and 2 for hard.");
            Scanner keyboard = new Scanner(System.in);

            level = keyboard.nextInt();

            if (level == 1 || level == 2)
            {
                makeHoles(level,board);
            }
            else 
            {
                System.out.println("Sorry, this is not a valid level. Please try again.");
                level = 4;
            }
        }

        playGame(level,board,key);

    }

    public static boolean checkBoard(int[][] board, int[][] key) 
    {   
        //if any value placed by the user is different than that of the key, return false as the player has not won yet.
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if(board[row][col] != key[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }

    /** playGame
     * This method enacts the entire sudoku game
     */
    public static void playGame(int level, int board[][], int key[][])
    {
        //the orig_num array are the numbers of the original sudoku board with holes that the user is not allowed to change
        int[][] orig_num = new int[NUM_ROWS][NUM_COLS];
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                orig_num[row][col] = board[row][col];
            }
        }
        printBoard(level, board);
        
        //while the user's board is not the same as the key_board, continue to run through the game
        while (!(checkBoard(board, key)))
        {
            Scanner keyboard = new Scanner(System.in);

            int desiredRow;
            int desiredCol;
            int actualNum;

            System.out.println("Which row would you like to place your number?");
            desiredRow = keyboard.nextInt() - 1; //the minus one allows the user to place 1 instead of 0 for the first row

            System.out.println("Which column would you like to place your number?");
            desiredCol = keyboard.nextInt() - 1;

            System.out.println("Which number would you like to place down?");
            actualNum = keyboard.nextInt();
            
            //this portion ensures that the user's input is valid
            while (validMove(desiredRow, desiredCol, actualNum, board, orig_num) == false)
            {
                System.out.println("Which row would you like to place your number?");
                desiredRow = keyboard.nextInt() - 1;

                System.out.println("Which column would you like to place your number?");
                desiredCol = keyboard.nextInt() - 1;

                System.out.println("Which number would you like to place down?");
                actualNum = keyboard.nextInt();
                System.out.println();
            }

            board[desiredRow][desiredCol] = actualNum;

            printBoard(level, board);

            if (checkBoard(board, key) == true)
            {
                System.out.println("Congratulations! You've completed the Sudoku puzzle!");
            }

        }

    }

    /** validMove
     * Checks as to whether the row, col, or value chosen by the user is a valid move
     */
    public static boolean validMove(int row, int col, int value, int[][] board, int[][] originalNumbers)
    {
        if (row < 0 || row > NUM_ROWS) { //checks if row is out of bounds
            System.out.println("The row number chosen is out of bounds! Please choose again.");
            return false;
        }
        if (col < 0 || col > NUM_COLS) { //checks if col is out of bounds
            System.out.println("The col number chosen is out of bounds! Please choose again.");
            return false;
        }
        if (value < 0 || value > 9) { //checks if val is out of bounds
            System.out.println("The intended value chosen is out of bounds! Please choose again.");
            return false;
        }
        if (originalNumbers[row][col] > 0) { //checks if user is attempting to change number that's already been placed
            System.out.println("You are not allowed to change a number that's already been placed! Please choose again.");
            return false;
        }

        return true; 
    }

    /** makeHoles
     * creates random holes in the board. The number of holes will be determined 
     * by the level of the game that the user chose.
     * 
     */

    public static void makeHoles(int level, int[][] board)
    {  
        //this portion ensures that the first level will have 30 holes while the second level will always have 40
        int num_holes = 30;
        int count_holes = 0;
        if (level == 2) {
            num_holes = num_holes+10;
        }
        
        
        while (count_holes < num_holes) { //continue until the amount of holes has reached its intended amount
            Random randNumber = new Random();
            int holesToMake = randNumber.nextInt(NUM_ROWS*NUM_ROWS);

            double remainingSquares = NUM_ROWS*NUM_ROWS;
            double remainingHoles = (double)holesToMake;

            for(int i=0;i<NUM_ROWS;i++) {
                for(int j=0;j<NUM_COLS;j++) {
                    double holeChance = remainingHoles/remainingSquares;
                    if(Math.random() <= holeChance)
                    {
                        if (count_holes > num_holes) { //if the amount of holes have been made, break
                            break;
                        }
                        board[i][j] = 0;
                        count_holes++; //increment the countholes method
                        remainingHoles--; //decrease likelyhood of the next value to receive a hole
                    }
                    remainingSquares--; //increase the likelyhood of the next value to receive a hole
                }
            }
        }
    }

    /**
     * This methods builds the 9x9 sudoku board
     */
    public static void printBoard(int level, int board[][]) 
    {    
        for (int i= 0; i < 9; i++) {
            if (i%3 == 0) {
                System.out.println("+-------+-------+-------+");
            }
            for (int j= 0; j < 9; j++) {
                if (j%3 == 0) {
                    System.out.print("| ");
                }
                if (board[i][j] == 0 ) {
                    System.out.print(". ");
                }
                else {
                    System.out.print(board[i][j]+" ");
                }
            }
            System.out.print("| ");
            System.out.println();
        }
        System.out.println("+-------+-------+-------+");

    }

}//class Sudoku
