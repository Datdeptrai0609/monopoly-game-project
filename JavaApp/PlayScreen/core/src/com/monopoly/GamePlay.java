package com.monopoly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.monopoly.renderFunction.RenderCore;

import java.util.HashMap;

public class GamePlay implements Screen {

    MonopolyOOP mono = new MonopolyOOP();

    // HashMap:
    private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    public TextureAtlas backAtlas, textureAtlas, roadHigh, roadLow, carsHigh, carsLow, things;


    public GamePlay (MonopolyOOP mono) {
        this.mono = mono;
    }

    @Override
    public void show() {

        textureAtlas = new TextureAtlas("playScreenAssets/block_Features.txt");
        backAtlas = new TextureAtlas("playScreenAssets/back_.txt");
        roadHigh = new TextureAtlas("playScreenAssets/roadHigh_.txt");
        roadLow = new TextureAtlas("playScreenAssets/roadLow_.txt");
        carsHigh = new TextureAtlas("playScreenAssets/carsHigh_.txt");
        carsLow = new TextureAtlas("playScreenAssets/carsLow_.txt");
        things = new TextureAtlas("playScreenAssets/otherThings_.txt");
    }

    // render game play ---------------------------------------------------------------------
    public void renderBack() {
        RenderCore renderBack = new RenderCore(sprites, backAtlas, mono.batch);
        //render background
        renderBack.drawThing(0, 0, 0);
        //render School
        renderBack.drawThing(1, 315, 700, 0.8f, 0.8f);
    }
    private void renderRoadHigh() {
        RenderCore renderCore = new RenderCore(sprites, roadHigh, mono.batch);
        //render roadHigh a_Left
        for (int i = 0, x = -50, y = 850; i < 14; i++) {
            renderCore.drawThing(0, x, y);
            x += 47;
            y -= 27;
        }
        //render roadHigh d_Right
        for (int i = 0, x = 1351+47*3, y = 900+27*3; i < 17; i++) {
            renderCore.drawThing(3, x, y);
            x -= 47;
            y -= 27;
        }
        //render roadHigh c_Range
        renderCore.drawThing(2, 608+47, 472+27, 0.62f, 0.62f);
        //render roadHigh b_To
        renderCore.drawThing(1, 608,472);
    }
    private void renderRoadLow() {
        RenderCore renderCore = new RenderCore(sprites, roadLow, mono.batch);
        //render roadLow
        for (int i = 0, x= -52, y= 310; i < 3; i++) {
            renderCore.drawThing(0, x, y);
            x += 610;
            y -= 351;
        }
    }
    private void renderCarsHigh() {
        RenderCore renderCore = new RenderCore(sprites, carsHigh, mono.batch);
        //render cars for high road
        renderCore.drawThing(0, 100, 745);
        renderCore.drawThing(1, 300, 696);
        renderCore.drawThing(2, 600, 525);
        renderCore.drawThing(3, 900, 655);
        renderCore.drawThing(3, 1155, 800);
    }
    private void renderCarsLow() {
        RenderCore renderCore = new RenderCore(sprites, carsLow, mono.batch);
        // render cars for Low road
        renderCore.drawThing(0, 60, 660);
        renderCore.drawThing(1, 600, 390);
        renderCore.drawThing(2, 560, 390);
        renderCore.drawThing(3, 905, 210);
        renderCore.drawThing(0, 845, 210);
        renderCore.drawThing(3, 1230, 20);
    }
    private void renderBlocks() {
        RenderCore renderCore = new RenderCore(sprites, textureAtlas, mono.batch);
        // render c
        renderCore.drawThing(renderCore.getRegionsAtlas()/4*2, (Gdx.graphics.getWidth()/2)-176, Gdx.graphics.getHeight()-206, 0.9f, 0.9f);
        // render c list
        for (int i = renderCore.getRegionsAtlas()/4*2+1, x_position_blocks_small = 900, y_position_blocks_small = Gdx.graphics.getHeight()-250; i < renderCore.getRegionsAtlas()/4*3; i++) {
            renderCore.drawThing(i, x_position_blocks_small, y_position_blocks_small);
            x_position_blocks_small+=73;
            y_position_blocks_small-=42;
        }
        // render d
        renderCore.drawThing(renderCore.getRegionsAtlas()/4*3,1410, Gdx.graphics.getHeight()/2-114, 0.9f, 0.9f);
        //render d list
        for (int i = renderCore.getRegionsAtlas()/4*3+1, x_position_blocks_small = 1337, y_position_blocks_small = Gdx.graphics.getHeight()/2-132; i < renderCore.getRegionsAtlas(); i++) {
            renderCore.drawThing(i, x_position_blocks_small, y_position_blocks_small);
            x_position_blocks_small-=73;
            y_position_blocks_small-=42;
        }
        //render b list
        for (int i = renderCore.getRegionsAtlas()/4*2-1, x_position_blocks_small = 680, y_position_blocks_small = Gdx.graphics.getHeight()-251; i > renderCore.getRegionsAtlas()/4*1; i--) {
            renderCore.drawThing(i, x_position_blocks_small, y_position_blocks_small);
            x_position_blocks_small-=73;
            y_position_blocks_small-=42;
        }
        // render b
        renderCore.drawThing(renderCore.getRegionsAtlas()/4*1, 36, Gdx.graphics.getHeight()/2-105, 0.9f, 0.9f);
        // render a list
        for (int i = renderCore.getRegionsAtlas()/4*1-1, x_position_blocks_small = 240, y_position_blocks_small = Gdx.graphics.getHeight()/2-133; i > renderCore.getRegionsAtlas()/4*0; i--) {
            renderCore.drawThing(i, x_position_blocks_small, y_position_blocks_small);
            x_position_blocks_small+=73;
            y_position_blocks_small-=42;
        }
        //render a
        renderCore.drawThing(renderCore.getRegionsAtlas()/4*0, (Gdx.graphics.getWidth()/2)-189, -12, 0.9f, 0.9f);
    }
    private void renderThings() {
        RenderCore renderCore = new RenderCore(sprites, things, mono.batch);
        //render otherThings
        //mountain
        renderCore.drawThing(7, 700, 250);
        // adv
        renderCore.drawThing(5, 910, 241);
        // fence
        renderCore.drawThing(4, 1120, 90);
        renderCore.drawThing(4, 1227, 30);
        // light SE
        renderCore.drawThing(0, 50, 685);
        renderCore.drawThing(0, 900, 195);
        renderCore.drawThing(0, 380, 496);
        renderCore.drawThing(0, 1190, 30);
        // light NE
        renderCore.drawThing(1, 1039, Gdx.graphics.getHeight()-122);
        renderCore.drawThing(1, 1544, 588);
        // tree and trees
        renderCore.drawThing(2, 1332, 23);
        renderCore.drawThing(3, 580, 440, 0.7f, 0.7f);
        // mountain
        renderCore.drawThing(6, -20, 50);
    }
    public void renderFullMap() {
        renderBack();
        renderRoadHigh();
        renderRoadLow();
        renderCarsHigh();
        renderCarsLow();
        renderBlocks();
        renderThings();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mono.batch.begin();
        renderFullMap();
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
