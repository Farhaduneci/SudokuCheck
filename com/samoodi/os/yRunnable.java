package os;

import jcolor.Ansi;

public class yRunnable implements Runnable {
  private boolean hasError;
  
  private int number;
  private int i;
  private int j;

  private int table[][] = new int[App.TABLE_SIZE][App.TABLE_SIZE];

  public yRunnable(int number, int[][] table, int i, int j) {
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
    for (int i = this.i - 1; i >= 0; i--) {
      if (number == table[i][j]) {
        System.out.println(Ansi.colorize(
          "\n" + "Vertical Validation Error! " + 
          "Number " + number + " repeats at index " + (i + 1), App.error));
        
        hasError = true;
        break;
      }
    }
  }
}
