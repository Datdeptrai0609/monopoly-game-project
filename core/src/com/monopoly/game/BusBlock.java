package com.monopoly.game;

public class BusBlock implements Block {
  private String name = "Bus";
  private final int pos;

  public BusBlock(int pos) {
    this.pos = pos;
  }

  public boolean isOwnable() {
    return false;
  }

  public boolean isOwned() {
    return false;
  }

  public int position() {
    return pos;
  }

  public String name() {
    return name;
  }

  public int cost() {
    return 0;
  }

  public int rent() {
    return 0;
  }

  public void setFestival(boolean stt) {
  }

  public boolean getFestival() {
    return false;
  }

  public Player owner() {
    return null;
  }

  public void purchase(Player player) {
  }

  public void reset() {
  }

  public String toString() {
    return name;
  }
}
