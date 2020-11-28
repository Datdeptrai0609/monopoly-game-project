package com.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.monopoly.renderFunction.RenderCore;

import java.util.HashMap;

public class WaitingRoom implements Screen {

    MonopolyOOP mono = new MonopolyOOP();

    // get number of player connected:
    int playerConnected = 0;

    //debug
    float x = 0;

    // font
    BitmapFont font = new BitmapFont(Gdx.files.internal("Font/cooperBlackFont.fnt"));
    String text = " player connected!";

    // HashMap:
    private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    TextureAtlas waitingRoomAtlas;

    Texture playBtnOn, playBtnOff;

    public WaitingRoom(MonopolyOOP mono) {
        this.mono = mono;

        waitingRoomAtlas = new TextureAtlas("waitingRoom/waitingRoom_.txt");

        playBtnOn = new Texture(Gdx.files.internal("waitingRoom/vidplay.png"));
        playBtnOff = new Texture(Gdx.files.internal("waitingRoom/vidplayoff.png"));
    }

    @Override
    public void show() {

    }

    // render waiting MQTT client -----------------------------------------------------------
    public void renderWaitingRoom() {
        RenderCore renderCore = new RenderCore(sprites, waitingRoomAtlas, mono.batch);
        renderCore.drawThing(0,0,0);
        for (float i = 0, x = 140, y = 375, texture_ordinal = 2; i < 4; i++) {
            renderCore.drawThing((int)texture_ordinal,x,y, 0.7f, 0.7f);
            x += 126.4f + 292;
        }
        // put the function to render character and color here!

        // put the counter here
        font.draw(mono.batch, Integer.toString(playerConnected) + text, 25, 980);

        if (playerConnected == 4) {
            mono.batch.draw(playBtnOn, Gdx.graphics.getWidth()/2 - 200, 70, 450, 229);
        }
        else {
            mono.batch.draw(playBtnOff, Gdx.graphics.getWidth()/2 - 200, 70, 450, 229);
        }
        if (playerConnected == 4) {
            if (Gdx.input.getX() < Gdx.graphics.getWidth()/2 + 229/2 && Gdx.input.getX() > Gdx.graphics.getWidth()/2 - 229/2
                    && Gdx.input.getY() < Gdx.graphics.getHeight() - 70 && Gdx.input.getY() > Gdx.graphics.getHeight() - 70 - 229
            ) {
                if (Gdx.input.isTouched()) {
                    dispose();
                    mono.setScreen(new GamePlay(mono));
                }
            }
        }
        x += 0.5f;
        //debug
        if (x==50) {
            playerConnected = 1;
        }
        if (x==100) {
            playerConnected = 2;
        }
        if (x == 150) {
            playerConnected = 3;
        }
        if (x == 200) {
            playerConnected = 4;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mono.batch.begin();
        renderWaitingRoom();
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
