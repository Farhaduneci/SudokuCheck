package os;

public class localRunnable implements Runnable {
  
  @Override
  public void run() {
    throw new RuntimeException("Local Validation Failed");
  }
}