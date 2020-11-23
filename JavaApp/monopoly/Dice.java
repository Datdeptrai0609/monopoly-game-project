package monopoly;

import java.util.Random;

public class Dice {
  private final Random rand;
  private int val = 0;
  private boolean isDouble = false;
  private int maxValOneDice = 6;

  public Dice() {
    rand = new Random();
  }

  // Roll the dice and check double
  public void roll() {
    int randA = rand.nextInt(maxValOneDice) + 1;

    int randB = rand.nextInt(maxValOneDice) + 1;

    val = randA + randB;
    isDouble = randA == randB;
  }

  public int getVal() {
    return val;
  }

  public boolean getDouble() {
    return isDouble;
  }

  public void setDouble() {
    isDouble = false;
  }
}
