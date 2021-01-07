package com.monopoly.game.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class ChanceCardGUI implements CardGUI {
  private Texture chanceImg;
  private SpriteBatch sb;
  private BitmapFont word;
  private boolean cardOn;
  private String cardText;
  private Sound cardSound;

  public ChanceCardGUI(SpriteBatch sb) {
    chanceImg = new Texture("card/chance.jpg");
    this.sb = sb;
    cardOn = false;
    this.word = new Word().word(40, Color.BLACK, "NerkoOne-Regular.ttf");
    cardSound = Gdx.audio.newSound(Gdx.files.internal("music/card.ogg"));
  }

  public void cardOn(boolean on) {
    if (on) {
      cardSound.play();
    }
    cardOn = on;
  }

  public boolean isOn() {
    return cardOn;
  }

  public void setContent(String content) {
    cardText = content;
  }

  public float getWidth() {
    return chanceImg.getWidth();
  }

  public void render(float moreWidth, boolean renderMore) {
    if (cardOn) {
      float x = Gdx.graphics.getWidth()/2 - chanceImg.getWidth()/2;
      float y = Gdx.graphics.getHeight()/2 - chanceImg.getHeight()/2;
      if (renderMore) {
        x -= moreWidth/2;
      }
      sb.draw(chanceImg, x, y);
      // Render word
      word.draw(sb, cardText, x, y + chanceImg.getHeight()*2/3, chanceImg.getWidth()/2, Align.center, true);
    }
  }

  public void dispose() {
    cardSound.dispose();
  }
}
