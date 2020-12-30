package com.monopoly.game.gameGUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HouseFlag {
  SpriteBatch sb;
  Texture house, flag;

  public HouseFlag(SpriteBatch sb, Texture house, Texture flag) {
    this.sb = sb;
    this.house = house;
    this.flag = flag;
  }

  public void render(Sprite[] boardSprite, int position, int numHouse) {
    // Flag and first house x y
    float x, y;
    if ((position > 0 && position < 8) || (position > 16 && position < 24)) {
      x = boardSprite[position].getX() + boardSprite[position].getWidth() * 0.6f;
      y = boardSprite[position].getY() + boardSprite[position].getHeight() * 0.7f;
    } else {
      x = boardSprite[position].getX() + boardSprite[position].getWidth() * 0.2f;
      y = boardSprite[position].getY() + boardSprite[position].getHeight() * 0.65f;
    }

    if (numHouse == 0) {
      sb.draw(flag, x, y);
    } else {
      // Set the x y of house
      float xHouse2, yHouse2, xHouse3, yHouse3, xHouse4, yHouse4;
      yHouse2 = y - house.getHeight()/5;
      yHouse3 = y - house.getHeight()/4.5f;
      yHouse4 = yHouse3 - house.getHeight()/5;
      if ((position > 0 && position < 8) || (position > 16 && position < 24)) {
        xHouse2 = x + house.getWidth()/3;
        xHouse3 = x - house.getWidth()/2.5f;
        xHouse4 = xHouse3 + house.getWidth()/3;
      } else {
        xHouse2 = x - house.getWidth()/3;
        xHouse3 = x + house.getWidth()/2.5f;
        xHouse4 = xHouse3 - house.getWidth()/3;
      }

      // Render house
      switch (numHouse) {
        case 1:
          sb.draw(house, x, y);
          break;
        case 2:
          sb.draw(house, x, y);
          sb.draw(house, xHouse2, yHouse2);
          break;
        case 3:
          sb.draw(house, x, y);
          sb.draw(house, xHouse2, yHouse2);
          sb.draw(house, xHouse3, yHouse3);
          break;
        case 4:
          sb.draw(house, x, y);
          sb.draw(house, xHouse2, yHouse2);
          sb.draw(house, xHouse3, yHouse3);
          sb.draw(house, xHouse4, yHouse4);
          break;
      }
    }
  }
}
