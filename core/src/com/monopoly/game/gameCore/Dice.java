package com.monopoly.game.gameCore;

import java.util.Random;

public class Dice {
  private final Random rand;
  private int val = 0;
  private boolean isDouble = false;

  public Dice() {
    rand = new Random();
  }

  // Roll the dice and check double
  public void roll() {
    val = rand.nextInt(6) + 1;

    isDouble = (val == 1 || val == 6);
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
