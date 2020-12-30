package com.monopoly.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.monopoly.game.gameGUI.MonopolyPlay;

public class MonopolyGUI extends Game {

  public SpriteBatch batch;

  @Override
  public void create() {
    batch = new SpriteBatch();
    this.setScreen(new MonopolyPlay(this));
  }

  @Override
  public void render() {
    super.render();
  }

  @Override
  public void dispose() {
    super.dispose();
  }
}
