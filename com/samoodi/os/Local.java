package os;

public class Local {
  int startI, endI, startJ, endJ;

  public Local(int startI, int endI, int startJ, int endJ) {
    this.startI = startI;
    this.endI = endI;
    
    this.startJ = startJ;
    this.endJ = endJ;
  }

	public int getStartI() {
		return this.startI;
	}

	public int getEndI() {
		return this.endI;
  }
  
  public int getStartJ() {
		return this.startJ;
  }
  
  public int getEndJ() {
		return this.endJ;
  }
  
  public static int findLocal(int i, int j) {
    int local = 0;

    if (i >= 0 && i <= 2 && j >= 3 && j <= 5)
      local = 1;
    if (i >= 0 && i <= 2 && j >= 6 && j <= 8)
      local = 2;

    if (i >= 3 && i <= 5 && j >= 0 && j <= 2)
      local = 3;
    if (i >= 3 && i <= 5 && j >= 3 && j <= 5)
      local = 4;
    if (i >= 3 && i <= 5 && j >= 6 && j <= 8)
      local = 5;

    if (i >= 6 && i <= 8 && j >= 0 && j <= 2)
      local = 6;
    if (i >= 6 && i <= 8 && j >= 3 && j <= 5)
      local = 7;
    if (i >= 6 && i <= 8 && j >= 6 && j <= 8)
      local = 8;
          
    return local;    
  }
}