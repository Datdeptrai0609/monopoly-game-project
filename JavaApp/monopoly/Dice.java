package monopoly; 
import java.util.Random;

public class Dice {
	private final Random rand;
  private int val = 0;
  private boolean is_double = false;

	public Dice() {
		rand = new Random();
	}

	public void roll() {
		int randA = rand.nextInt(5) + 2;

		int randB = rand.nextInt(5) + 2;

    val = randA + randB;
    is_double = randA == randB;
	}

  public int getVal() {
    return val;
  }

  public boolean getDouble() {
    return is_double;
  }
  
  public void setDouble() {
    is_double = false;
  }
}
