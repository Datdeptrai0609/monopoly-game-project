package com.monopoly.gamePlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.monopoly.MonopolyOOP;
import com.monopoly.renderFunction.RenderCore;

import java.util.HashMap;

public class WelcomeScreen implements Screen {

    // Define variable ------------------------------------------------------------------------------------------------------------------------------------------
    // Call MonopolyOOP
    MonopolyOOP monopoly;

    // HashMap:
    private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    // TextureAtlas: to drawThing(): Define at README.md
    public TextureAtlas welcomeScreenAtlas, loadingBar;

    // Variable for Loading  Animation
    private int LOADING_SPEED_GAME_PLAY = 0;

    // Constructor
    public WelcomeScreen(MonopolyOOP monopoly) {
        this.monopoly = monopoly;

        monopoly.batch = new SpriteBatch();
        welcomeScreenAtlas = new TextureAtlas("welcomeScreen/welcomeScreen.txt");
        loadingBar = new TextureAtlas("welcomeScreen/loadingBar/loadingBar.txt");
    }

    // Render welcome screen -------------------------------------------------------------------------------------------------------------------------------------
    public void renderWelcome() {
        RenderCore renderWelcomeScreen = new RenderCore(sprites, welcomeScreenAtlas, monopoly.batch);
        // Render Background
        renderWelcomeScreen.drawThing( 0, 0, 0, (float) (Gdx.graphics.getWidth()/renderWelcomeScreen.getSpritesWidth(0)), (float) (Gdx.graphics.getHeight()/renderWelcomeScreen.getSpritesHeight(0)));

        // Render MonoLogo
        renderWelcomeScreen.drawThing( 3, (float) (Gdx.graphics.getWidth()/2 - renderWelcomeScreen.getSpritesWidth(3)/2), (float) (Gdx.graphics.getHeight()/2 - renderWelcomeScreen.getSpritesHeight(3)/2));

        // Render Logos:
        renderWelcomeScreen.drawThing(2, 15, Gdx.graphics.getHeight() - 130, 0.12f, 0.12f);
        renderWelcomeScreen.drawThing(1, 150, Gdx.graphics.getHeight() - 130, 0.12f, 0.12f);

        RenderCore renderLoadingBar = new RenderCore(sprites, loadingBar, monopoly.batch);
        // Render loading
        for (float i = 0, compareVar = 50, times = 1;
             i < 7;
             i++) {
            if (LOADING_SPEED_GAME_PLAY > compareVar*times && i != 4) {
                renderLoadingBar.drawThing((int) i, (float) (Gdx.graphics.getWidth()/2 - renderLoadingBar.getSpritesWidth((int) i)*0.6/2), Gdx.graphics.getHeight()/2 - 350, 0.6f, 0.6f);
            }
            if (LOADING_SPEED_GAME_PLAY > compareVar*times && i == 4) {
                renderLoadingBar.drawThing((int) i, (float) (Gdx.graphics.getWidth()/2 - renderLoadingBar.getSpritesWidth((int) i)*0.6/2), Gdx.graphics.getHeight()/2 - 350 - 25*0.6f, 0.6f, 0.6f);
            }
            times++;
        }

        LOADING_SPEED_GAME_PLAY += 1;

        // Handle change screen to WaitingRoom(auto), 75 is compareVar in render loading loop
        if(LOADING_SPEED_GAME_PLAY == 50*9) {
            dispose();
            monopoly.setScreen(new WaitingRoom(monopoly));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        monopoly.batch.begin();
        renderWelcome();
        monopoly.batch.end();
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
