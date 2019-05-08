import java.util.*;
import java.util.Random;

/**
 * This class generates different versions of the Sudoku puzzle
 *
 * @Phuocan Nguyen
 * @April 30 2019
 */
public class Sudoku_Puzzle
{

    public static int[][] puzzle_generator()
    {
        int[][] key = {{7,8,2,6,4,5,3,9,1},{5,9,6,3,7,1,4,2,8},{4,3,1,2,9,8,6,5,7},
                {3,1,4,9,8,2,5,7,6},{2,5,8,7,6,3,9,1,4},{9,6,7,5,1,4,8,3,2},
                {6,7,5,8,2,9,1,4,3},{8,4,9,1,3,7,2,6,5},{1,2,3,4,5,6,7,8,9},};
        Random board_variation = new Random();
        int rowHeight = 9;
        int colHeight = 9;
        
        if (board_variation.nextInt(3) == 1) {
            //reverses puzzle horizontally
            for (int row = 0; row < rowHeight; row++) {
                for (int col = 0; col < colHeight/2; col++) {
                    int holder = key[row][col];
                    key[row][col] = key[row][colHeight - col - 1];
                    key[row][colHeight - col - 1] = holder;
                }
            }
        }
        else if (board_variation.nextInt(3) == 2) {
            //reverses puzzle vertically
            for (int row = 0; row < rowHeight/2; row++) {
                for (int col = 0; col < rowHeight; col++) {
                    int holder = key[row][col];
                    key[row][col] = key[rowHeight - row - 1][col];
                    key[rowHeight - row - 1][col] = holder;
                }
            }
        }
        
        //if board_variation = 3, return the original board
        return key;
    }
}
