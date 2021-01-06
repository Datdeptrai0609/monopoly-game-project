package com.monopoly.game.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
  private float stateTime;

  // Music
  private Music diceMusic;
  private Sound diceDoneMusic;

  public DiceGUI(SpriteBatch sb) {
    diceAtlas = new TextureAtlas("dice.txt");
    dice = new Animation<TextureRegion>(0.1f, diceAtlas.getRegions());
    this.sb = sb;
    diceRoll = true;
    stateTime = 0f;
    diceMusic = Gdx.audio.newMusic(Gdx.files.internal("music/dice.ogg"));
    diceDoneMusic = Gdx.audio.newSound(Gdx.files.internal("music/diceDone.ogg"));
  }

  public void setDiceVal(int val) {
    diceVal = val;
    diceRoll = false;
    diceMusic.stop();
    diceDoneMusic.play();
  }

  public void setDiceRoll(boolean roll) {
    diceRoll = roll;
  }

  public void render() {
    TextureRegion diceFrame = dice.getKeyFrame(stateTime, true);
    if (diceRoll) {
      if (!diceMusic.isPlaying()) {
        diceMusic.play();
      }
      diceFrame = dice.getKeyFrame(stateTime, true);
      stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
    } else {
      diceFrame = diceAtlas.findRegion(String.format("dice%d", diceVal));
      stateTime = 0f;
    }
    sb.draw(diceFrame, Gdx.graphics.getWidth()/2 - diceFrame.getRegionWidth()/2, Gdx.graphics.getHeight()/2 - diceFrame.getRegionHeight()/2, diceFrame.getRegionWidth()*2, diceFrame.getRegionHeight()*2);
  }

  public void dispose() {
    diceMusic.dispose();
    diceDoneMusic.dispose();
  }
}
