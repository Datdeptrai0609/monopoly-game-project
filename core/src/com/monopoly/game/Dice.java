package com.monopoly.game;

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
    int randA = rand.nextInt(6) + 1;

    int randB = rand.nextInt(6) + 1;

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
