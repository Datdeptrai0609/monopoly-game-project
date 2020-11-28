package com.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TestFont implements Screen {

    MonopolyOOP mono = new MonopolyOOP();

    BitmapFont font = new BitmapFont(Gdx.files.internal("Font/cooperBlackFont.fnt"));
    String text = "Kuerl Test!";

    public TestFont(MonopolyOOP mono) {
        this.mono = mono;

        mono.batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mono.batch.begin();

        font.draw(mono.batch, text, 100, 100);

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
