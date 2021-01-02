package com.monopoly.game.gameGUI;

public interface CardGUI {
  void cardOn(boolean on);
  boolean isOn();
  float getWidth();
  void render(float moreWidth, boolean renderMore);
}
