package com.monopoly.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DiceGUI {
  TextureAtlas diceAtlas;
  TextureRegion diceRegion;
  Animation<TextureRegion> dice;
  SpriteBatch sb;
  private int diceVal;
  private boolean diceRoll;

  public DiceGUI(SpriteBatch sb) {
    diceAtlas = new TextureAtlas("dice.txt");
    dice = new Animation<TextureRegion>(0.1f, diceAtlas.getRegions());
    this.sb = sb;
    diceRoll = false;
  }

  public void setDiceVal(int val) {
    diceVal = val;
    diceRoll = false;
  }

  public void setDiceRoll(boolean roll) {
    diceRoll = roll;
  }

  public void render(float stateTime) {
    TextureRegion diceFrame = dice.getKeyFrame(stateTime, true);
    if (diceRoll) {
      diceFrame = dice.getKeyFrame(stateTime, true);
    } else {
      diceFrame = diceAtlas.findRegion(String.format("dice%d", diceVal));
    }
    sb.draw(diceFrame, Gdx.graphics.getWidth()/2 - diceFrame.getRegionWidth()/2, Gdx.graphics.getHeight()/2 - diceFrame.getRegionHeight()/2, diceFrame.getRegionWidth()*2, diceFrame.getRegionHeight()*2);
  }
}
