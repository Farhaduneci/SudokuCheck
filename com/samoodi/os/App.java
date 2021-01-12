/* -------------------------------------------------------------------------- */
/*                           Reza Samoodi, 970 30 33                          */
/* -------------------------------------------------------------------------- */

package os;

import java.util.Scanner;
import jcolor.*;

public class App implements Runnable{
    public static void main(String[] args) {
        //Sudoku table size
        final int TABLE_SIZE = 9;

        /*
        * Java Colored Debug Printer (JColor) offers an easy syntax to print messages with a
        * colored font or background on a terminal.
        * You can find the package at https://github.com/dialex/JColor
        * This package is used with the name of "Ansi" in project!
        */

        // Ansi formats for printing messages
        AnsiFormat error = new AnsiFormat(
            Attribute.WHITE_TEXT(),
            Attribute.RED_BACK(),
            Attribute.BOLD()
        );

        //Scanner instance
        Scanner scanner = new Scanner(System.in);

        //Array to save input numbers as sudoku table
        int table[][] = new int[TABLE_SIZE][TABLE_SIZE]; 
        
        int inputNumber = 0; //Input variable

        //Print the empty array
        printTable(table);

        for (int i = 0; i < TABLE_SIZE; i++) { //Iterates over table rows
            for (int j = 0; j < TABLE_SIZE; j++) { //Iterates over table columns
                do {
                    System.out.print("\nPlease enter a number: ");
                    
                    while (!scanner.hasNextInt()) {
                        System.out.println(Ansi.colorize("That's not a number!", error));
                        scanner.next();
                    }

                    inputNumber = scanner.nextInt(); //Get input number from user
                } while (inputNumber > 9 || inputNumber < 1);



                //TODO change the behavior and save the input after validation
                table[i][j] = inputNumber;

                printTable(table);
            }
        }

        // Not necessary needed but not using this will cause resource leak!
        scanner.close();
    }

    private static void printTable(int[][] table) {
        //Clear the screen
        clearScreen();

        for (int[] row : table) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            //Separates rows
            System.out.println();
        }
    }

    private static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

	@Override
	public void run() {
        //TODO implement threads
    } 
}