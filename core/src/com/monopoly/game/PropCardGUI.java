package com.monopoly.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class PropCardGUI implements CardGUI{
  Texture bought;
  SpriteBatch sb;
  Texture propertyImg;
  BitmapFont wordName, wordPrice;
  private boolean cardOn;
  private String name, price;
  private boolean isBought;
  private int position;

  public PropCardGUI(SpriteBatch sb, Word word) {
    this.sb = sb;
    bought = new Texture("bought.png");
    cardOn = false;
    isBought = false;
    wordName = word.word(40, Color.BLACK);
    wordPrice = word.word(30, Color.BLACK);
  }

  public void setContent(int pos, boolean bought, String name, String price) {
    position = pos;
    isBought = bought;
    this.name = name;
    this.price = price;
  }

  public void cardOn(boolean on) {
    cardOn = on;
  }

  public boolean isOn() {
    return cardOn;
  }

  public float getWidth() {
    return propertyImg.getWidth();
  }

  public void render(float moreWidth, boolean renderMore) {
    if (cardOn) {
      propertyImg = new Texture(String.format("propCard%s.png", position));
      float x = Gdx.graphics.getWidth()/2 - propertyImg.getWidth()/2;
      float y = Gdx.graphics.getHeight()/2 - propertyImg.getHeight()/2;
      if (renderMore) {
        x -= moreWidth/2;
      }
      sb.draw(propertyImg, x, y);
      // render bought icon
      if (isBought) {
        sb.draw(bought, x + propertyImg.getWidth() - bought.getWidth(), y);
      }
      wordName.draw(sb, name, x, y + propertyImg.getHeight()*6/7, propertyImg.getWidth(), Align.center, false);
      wordPrice.draw(sb, price, x + propertyImg.getWidth() * 0.65f, y + propertyImg.getHeight()*0.72f);
    }
  }
}
