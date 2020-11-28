package com.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.monopoly.MonopolyOOP;
import com.monopoly.WaitingRoom;
import com.monopoly.renderFunction.RenderCore;

import java.util.HashMap;

public class WelcomeScreen implements Screen {

    MonopolyOOP mono;


    // final loading high
    float loadingHigh = Gdx.graphics.getHeight()/2-40-84.3f, loadingLow = Gdx.graphics.getHeight()/2-190-84.3f;
    // var speed
    int speedRender0 = 0;

    // HashMap:
    private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    // TextureAtlas: to drawThing()
    public TextureAtlas welcomeScreenAtlas;

    int x = -200;

    public WelcomeScreen(MonopolyOOP mono) {
        this.mono = mono;

        mono.batch = new SpriteBatch();
        welcomeScreenAtlas = new TextureAtlas("welcomeScreen/welcomeScreen_.txt");
    }

    // render welcome screen ----------------------------------------------------------------
    public void renderWelcome() {
        // render background
        RenderCore renderWelcomeScreen = new RenderCore(sprites, welcomeScreenAtlas, mono.batch);
        renderWelcomeScreen.drawThing(0,0,0);

        //render monoLogo
        renderWelcomeScreen.drawThing(3, Gdx.graphics.getWidth()/2 - 560*1.2f, Gdx.graphics.getHeight()/2-100, 1.2f, 1.2f);

        // render loading
        if (x > 100) {
            renderWelcomeScreen.drawThing(2, 395, Gdx.graphics.getHeight()/2-200 + speedRender0, 0.1f, 0.1f);
        }

        if (x > 200) {
            renderWelcomeScreen.drawThing(1, 395+184.3f, Gdx.graphics.getHeight()/2-200 + speedRender0, 0.1f, 0.1f);
        }

        if (x > 300) {
            renderWelcomeScreen.drawThing(2, 395+184.3f*2, Gdx.graphics.getHeight()/2-200 + speedRender0, 0.1f, 0.1f);
        }

        if (x > 400) {
            renderWelcomeScreen.drawThing(1, 395+184.3f*3, Gdx.graphics.getHeight()/2-200 + speedRender0, 0.1f, 0.1f);
        }

        if (x > 500) {
            renderWelcomeScreen.drawThing(2, 395+184.3f*4, Gdx.graphics.getHeight()/2-200 + speedRender0, 0.1f, 0.1f);
        }

        if (x > 600) {
            renderWelcomeScreen.drawThing(1, 395+184.3f*5, Gdx.graphics.getHeight()/2-200 + speedRender0, 0.1f, 0.1f);
        }

        x += 1;
        if(x == 700) {
            dispose();
            mono.setScreen(new WaitingRoom(mono));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mono.batch.begin();
        renderWelcome();
        mono.batch.end();
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
