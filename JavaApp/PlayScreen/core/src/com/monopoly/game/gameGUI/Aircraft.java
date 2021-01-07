package com.monopoly.game.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class Aircraft {
  private Texture aircraftImg;
  private Texture effect;
  private SpriteBatch sb;
  // private long launchTime, landTime;
  private long time;
  private boolean isLaunching, isLanding;
  private long duration;
  private float x, y;

  private enum Status {
    LAUNCH, LAND
  };

  private Status aircraftStatus;

  public Aircraft(SpriteBatch sb) {
    aircraftImg = new Texture("aircraft/rocket.png");
    effect = new Texture("aircraft/effect.png");
    this.sb = sb;
    duration = Long.valueOf(10) * 1000000000;
    x = Gdx.graphics.getWidth() * 0.4f;
    y = Gdx.graphics.getHeight() * 0.4f;
    time = TimeUtils.nanoTime();
    isLaunching = isLanding = false;
    aircraftStatus = Status.LAUNCH;
  }

  public void render() {
    sb.draw(aircraftImg, x, y, aircraftImg.getWidth() * 0.8f, aircraftImg.getHeight() * 0.8f);
    // Handle render
    if (isLaunching) {
      sb.draw(effect, x + (aircraftImg.getWidth() * 0.8f - effect.getWidth() * 2) / 2, y - effect.getHeight() * 2 + 10,
          effect.getWidth() * 2, effect.getHeight() * 2);
      y += 250 * Gdx.graphics.getDeltaTime();
      if (y > Gdx.graphics.getHeight()) {
        isLaunching = false;
        time = TimeUtils.nanoTime();
        aircraftStatus = Status.LAND;
      }
    }
    if (isLanding) {
      sb.draw(effect, x + (aircraftImg.getWidth() * 0.8f - effect.getWidth() * 2) / 2, y - effect.getHeight() * 2 + 10,
          effect.getWidth() * 2, effect.getHeight() * 2);
      y -= 200 * Gdx.graphics.getDeltaTime();
      if (y < Gdx.graphics.getHeight() * 0.4f) {
        isLanding = false;
        time = TimeUtils.nanoTime();
        aircraftStatus = Status.LAUNCH;
      }
    }

    // Check time
    if (TimeUtils.nanoTime() - time > duration) {
      switch (aircraftStatus) {
        case LAUNCH:
          isLaunching = true;
          break;
        case LAND:
          isLanding = true;
          break;
      }
    }
  }
}
