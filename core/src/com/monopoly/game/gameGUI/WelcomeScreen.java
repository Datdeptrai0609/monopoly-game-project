package com.monopoly.game.gameGUI;

import com.monopoly.game.MonopolyGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

public class WelcomeScreen implements Screen {

  MonopolyGUI gui;
  private SpriteBatch sb;

  // HashMap:
  private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

  // TextureAtlas: to drawThing(): Define at README.md
  public TextureAtlas welcomeScreenAtlas, loadingBar;

  // Variable for Loading Animation
  private int LOADING_SPEED_GAME_PLAY = 0;

  // Constructor
  public WelcomeScreen(MonopolyGUI gui) {
    this.gui = gui;

    sb = gui.batch;
    welcomeScreenAtlas = new TextureAtlas("welcomeScreen/welcomeScreen.txt");
    loadingBar = new TextureAtlas("welcomeScreen/loadingBar/loadingBar.txt");
  }

  // Render welcome screen
  // -------------------------------------------------------------------------------------------
  public void renderWelcome() {
    RenderCore renderWelcomeScreen = new RenderCore(sprites, welcomeScreenAtlas, sb);
    // Render Background
    renderWelcomeScreen.drawThing(0, 0, 0, (float) (Gdx.graphics.getWidth() / renderWelcomeScreen.getSpritesWidth(0)),
        (float) (Gdx.graphics.getHeight() / renderWelcomeScreen.getSpritesHeight(0)));

    // Render MonoLogo
    renderWelcomeScreen.drawThing(3, (float) (Gdx.graphics.getWidth() / 2 - renderWelcomeScreen.getSpritesWidth(3) / 2),
        (float) (Gdx.graphics.getHeight() / 2 - renderWelcomeScreen.getSpritesHeight(3) / 2) + 100);

    // Render Logos:
    renderWelcomeScreen.drawThing(2, 15, Gdx.graphics.getHeight() - 130, 0.12f, 0.12f);
    renderWelcomeScreen.drawThing(1, 150, Gdx.graphics.getHeight() - 130, 0.12f, 0.12f);

    RenderCore renderLoadingBar = new RenderCore(sprites, loadingBar, sb);
    // Render loading
    for (int i = 0, compareVar = 50, times = 1; i < 5; i++) {
      if (i == 0 && LOADING_SPEED_GAME_PLAY > compareVar * times) {
        renderLoadingBar.drawThing((int) 0,
            (float) (Gdx.graphics.getWidth() / 2 - renderLoadingBar.getSpritesWidth((int) 0) * 0.6 / 2),
            Gdx.graphics.getHeight() / 2 - 300, 0.6f, 0.6f);
      }
      if (i == 1 && LOADING_SPEED_GAME_PLAY > compareVar * times) {
        renderLoadingBar.drawThing(1,
            (float) (Gdx.graphics.getWidth() / 2 - renderLoadingBar.getSpritesWidth((int) 0) * 0.6 / 2 + 9),
            (float) (Gdx.graphics.getHeight() / 2 - 300 + renderLoadingBar.getSpritesHeight(0) * 0.6 / 2
                - renderLoadingBar.getSpritesHeight(1) * 0.6 / 2),
            0.6f, 0.6f);
      }
      if (i > 1 && LOADING_SPEED_GAME_PLAY > compareVar * times) {
        renderLoadingBar.drawThing(2,
            (float) (Gdx.graphics.getWidth() / 2 - renderLoadingBar.getSpritesWidth((int) 0) * 0.6 / 2 + 9
                + 443 * (i - 1) * 0.6),
            (float) (Gdx.graphics.getHeight() / 2 - 300 + renderLoadingBar.getSpritesHeight(0) * 0.6 / 2
                - renderLoadingBar.getSpritesHeight(1) * 0.6 / 2),
            0.6f, 0.6f);
      }
      times++;
    }

    LOADING_SPEED_GAME_PLAY += 1;

    // Handle change screen to WaitingRoom(auto), 75 is compareVar in render loading
    // loop
    if (LOADING_SPEED_GAME_PLAY == 50 * 6) {
      dispose();
      gui.setScreen(new MonopolyPlay(gui));
    }
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    sb.begin();
    renderWelcome();
    sb.end();
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {

  }
}
