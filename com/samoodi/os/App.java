/* -------------------------------------------------------------------------- */
/*                          Reza Samoodi, 970 30 33                          */
/* -------------------------------------------------------------------------- */

package os;

import java.util.Scanner;
import jcolor.*;

public class App {
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
            Up:
            for (int j = 0; j < TABLE_SIZE; j++) { //Iterates over table columns
                inputNumber = getInput(error, scanner);

                //Validate Input
                boolean isValid = validateNumber(error, inputNumber, table);
                
                if (isValid) {
                    //Save input
                    table[i][j] = inputNumber;
                }
            
                if (!isValid) {
                    inputNumber = getInput(error, scanner);
                    break Up;
                }

                printTable(table);
            }
        }

        // Not necessary needed but not using this will cause resource leak!
        scanner.close();
    }

    private static int getInput(AnsiFormat error, Scanner scanner) {
        int inputNumber;

        do {
            System.out.print("\nPlease enter a number: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println(Ansi.colorize("That's not a number!", error));
                scanner.next();
            }

            inputNumber = scanner.nextInt(); //Get input number from user
        } while (inputNumber > 9 || inputNumber < 1);

        return inputNumber;
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

	private static boolean validateNumber(AnsiFormat error, int number, int[][] table) {
        
        //Check X & Y Runnables
        xRunnable xRunnable = new xRunnable();
        yRunnable yRunnable = new yRunnable();
        
        //Check local indexes Runnable
        localRunnable localRunnable = new localRunnable();

        //Create xThread
        Thread validateXThread = new Thread(xRunnable);
        
        //Create yThread
        Thread validateYThread = new Thread(yRunnable);
        
        //Create localThread
        Thread validateLocalThread = new Thread(localRunnable);

        validateXThread.setDaemon(true);
        validateYThread.setDaemon(true);
        
        validateLocalThread.setDaemon(true);


        Thread.UncaughtExceptionHandler handler =
            new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread th, Throwable ex) {
                System.out.println("\n\n");
                System.out.println(Ansi.colorize(ex.getMessage(), error));

                
            }
        };

        validateXThread.setUncaughtExceptionHandler(handler);
        validateYThread.setUncaughtExceptionHandler(handler);

        validateLocalThread.setUncaughtExceptionHandler(handler);
        
        //Validate X & Y
        validateXThread.start();   
        validateYThread.start();   
        
        //Validate Local
        validateLocalThread.start();  

        return true;
    }
}