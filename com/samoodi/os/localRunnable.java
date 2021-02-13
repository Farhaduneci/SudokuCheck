package os;

import java.util.HashSet;
import java.util.Set;

import jcolor.Ansi;

public class localRunnable implements Runnable {
  private boolean hasError;
  
  private int number;
  private int i;
  private int j;

  private int table[][] = new int[App.TABLE_SIZE][App.TABLE_SIZE];
  
  public localRunnable(int number, int[][] table, int i, int j) {
    hasError = false;

    this.i = i;
    this.j = j;
    this.table = table;
    this.number = number;

    //Consider the number in table
    this.table[i][j] = number;
  }

  public boolean getHasError() {
		return this.hasError;
	}
  
  @Override
  public void run() {
    //Set will never let duplicated inputs
    Set<Integer> set = new HashSet<Integer>();

    int currentLocal = Local.findLocal(this.i, this.j);

    Local local = App.locals.get(currentLocal);

    for (int i = local.startI; i <= local.endI; i++) {
      for (int j = local.startJ; j <= local.endJ; j++) {
        number = table[i][j];

        if (number != 0) { 
          if (set.add(number) == false){
            System.out.println(Ansi.colorize(
            "\n" + "Local Validation Error! " + 
            "Number " + number + " repeats in local", App.error));
          
            hasError = true;
            break;
          }
        }
      }
    }
  }
}