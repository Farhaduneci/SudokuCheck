package os;

import jcolor.Ansi;

public class xRunnable implements Runnable {
  private boolean hasError;
  
  private int number;
  private int i;
  private int j;

  private int table[][] = new int[App.TABLE_SIZE][App.TABLE_SIZE];
  
  public xRunnable(int number, int[][] table, int i, int j) {
    hasError = false;
    
    this.i = i;
    this.j = j;
    this.table = table;
    this.number = number;
  }

  public boolean getHasError() {
		return this.hasError;
  }
  
  @Override
  public void run() {
    for (int j = this.j - 1; j >= 0; j--) {
      if (table[i][j] != 0 && number == table[i][j]) {
        System.out.println(Ansi.colorize(
          "\n" + "Horizontal Validation Error! " + 
          "Number " + number + " repeats at index " + (j + 1), App.error));
        
        hasError = true;
        break;
      }
    }
  }
} 