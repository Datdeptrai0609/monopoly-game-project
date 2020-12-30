package com.monopoly.game.gameCore;

public class JailBlock implements Block {

  private final int pos;
  private final String name = "Jail";

  public JailBlock(int pos) {
    this.pos = pos;
  }

  public String name() {
    return name;
  }

  public boolean isOwned() {
    return false;
  }

  public int position() {
    return pos;
  }

  public boolean isOwnable() {
    return false;
  }

  public int cost() {
    return 0;
  }

  public void purchase(Player player) {
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

  public void reset() {
  }

  public String toString() {
    return name;
  }
}
