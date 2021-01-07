package com.monopoly.game.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Aircraft {
  private Texture aircraftImg;
  private Texture effect;
  private Rectangle aircraft;
  private SpriteBatch sb;
  private long launchTime;

  public Aircraft (SpriteBatch sb) {
    aircraftImg = new Texture("aircraft/rocket.png");
    effect = new Texture("aircraft/effect.png"); 
    this.sb = sb;
    aircraft = new Rectangle();
    aircraft.x = Gdx.graphics.getWidth() * 0.4f;
    aircraft.y = Gdx.graphics.getHeight() * 0.4f;
  }

  public void launch() {
    aircraft.y += Gdx.graphics.getDeltaTime();
    launchTime = TimeUtils.nanoTime();
  }

  public void render() {
    sb.draw(aircraftImg, aircraft.x, aircraft.y, aircraftImg.getWidth() * 0.8f, aircraftImg.getHeight() * 0.8f);
  }
}
