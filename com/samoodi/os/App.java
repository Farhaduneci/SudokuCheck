/* -------------------------------------------------------------------------- */
/*                          Reza Samoodi, 970 30 33                          */
/* -------------------------------------------------------------------------- */

package os;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import jcolor.*;

public class App {
    //Sudoku table size
    public final static int TABLE_SIZE = 9;
    
    /*
    * Java Colored Debug Printer (JColor) offers an easy syntax to print messages with a
    * colored font or background on a terminal.
    * You can find the package at https://github.com/dialex/JColor
    * This package is used with the name of "Ansi" in project!
    */

    // Ansi formats for printing messages
    public static AnsiFormat error = new AnsiFormat(
        Attribute.WHITE_TEXT(),
        Attribute.RED_BACK(),
        Attribute.BOLD()
    );

    public static AnsiFormat succuss = new AnsiFormat(
        Attribute.BRIGHT_GREEN_TEXT(),
        Attribute.BOLD()
    );
    
    public static List<Local> locals = new LinkedList<>();

    public static void main(String[] args) {
        //Create Local zones
        for(int row = 0; row < 9; row += 3){
            for(int col = 0; col < 9; col += 3){
              locals.add(new Local(row, row + 2, col, col + 2));
            }
        }

        //Scanner instance
        Scanner scanner = new Scanner(System.in);

        //Array to save input numbers as sudoku table
        int table[][] = new int[TABLE_SIZE][TABLE_SIZE]; 
        
        int inputNumber = 0; //Input variable

        //Print the empty array
        printTable(table);

        for (int i = 0; i < TABLE_SIZE; i++) { //Iterates over table rows
            for (int j = 0; j < TABLE_SIZE; j++) { //Iterates over table columns
                inputNumber = getInput(error, scanner, false);

                //Validate Input
                boolean valid = validateNumber(inputNumber, table, i, j);
                
                if (valid) {
                    //Save input
                    table[i][j] = inputNumber;
                }
            
                if (!valid) {
                    do {
                        inputNumber = getInput(error, scanner, true);
                    } while (!validateNumber(inputNumber, table, i, j));

                    //Save input
                    table[i][j] = inputNumber;
                }

                printTable(table);
            }
        }

        System.out.println(Ansi.colorize("Hurray, Sudoku entered was OK!", succuss));

        // Not necessary needed but not using this will cause resource leak!
        scanner.close();
    }

    private static int getInput(AnsiFormat error, Scanner scanner, boolean wasWrong) {
        int inputNumber;

        if (wasWrong) {
            System.out.println("\n" + Ansi.colorize("Wrong Input, Try again...", error));
        }

        do {
            System.out.print("\nPlease enter a number: ");

            while (!scanner.hasNextInt()) {
                System.out.println(Ansi.colorize("That's not a number!", error));
                scanner.next();
            }

            inputNumber = scanner.nextInt(); //Get input number from user
        } while (inputNumber > 9 || inputNumber < 0);

        if(inputNumber == 0)
            System.exit(0);

        return inputNumber;
    }

    private static void printTable(int[][] table) {
        //Clear the screen
        clearScreen();
        
        int val = 0;

        for (int i = 0; i < 9; i++) {
                System.out.print("\n");
                if(i % 3 == 0)
                    System.out.print("\n");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0)
                    System.out.print(" ");
                
                val = table[i][j];
                System.out.printf("%s ", val == 0 ? "." : val);
            }
        }

        System.out.println("\n\n\n\n" + "Enter \"0\" when ever you need to exit.");
    }

    private static void clearScreen() { 
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

	private static boolean validateNumber(int number, int[][] table, int i, int j) {
        
        //Check X & Y Runnables
        xRunnable xRunnable = new xRunnable(number, table, i, j);
        yRunnable yRunnable = new yRunnable(number, table, i, j);
        
        //Check local indexes Runnable
        localRunnable localRunnable = new localRunnable(number, table, i, j);

        //Create xThread
        Thread validateXThread = new Thread(xRunnable);
        
        //Create yThread
        Thread validateYThread = new Thread(yRunnable);
        
        //Create localThread
        Thread validateLocalThread = new Thread(localRunnable);

        //Validate X & Y
        validateXThread.start();   
        validateYThread.start();   
        
        //Validate Local
        validateLocalThread.start();

        //Stop Main thread until all child threads finish
        try {
			validateXThread.join();
			validateYThread.join();
            
            validateLocalThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        return
            xRunnable.getHasError() ||
            yRunnable.getHasError() ||
            localRunnable.getHasError()
                ? false
                : true;
    }
}