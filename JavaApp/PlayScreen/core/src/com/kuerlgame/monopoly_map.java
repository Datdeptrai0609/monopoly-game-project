package com.kuerlgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class monopoly_map extends ApplicationAdapter {

    TextureAtlas textureAtlas, backAtlas, roadHigh, roadLow, carsHigh, carsLow, things;

    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    SpriteBatch batch;

    @Override
    public void create() {

        textureAtlas = new TextureAtlas("playScreenAssets/block_Features.txt");
        backAtlas = new TextureAtlas("playScreenAssets/back_.txt");
        roadHigh = new TextureAtlas("playScreenAssets/roadHigh_.txt");
        roadLow = new TextureAtlas("playScreenAssets/roadLow_.txt");
        carsHigh = new TextureAtlas("playScreenAssets/carsHigh_.txt");
        carsLow = new TextureAtlas("playScreenAssets/carsLow_.txt");
        things = new TextureAtlas("playScreenAssets/otherThings_.txt");

        batch = new SpriteBatch();
    }

    private void renderBack() {
        Array<TextureAtlas.AtlasRegion> regions = backAtlas.getRegions();
        //render background
        drawThing(regions, backAtlas, 0, 0, 0);
        //render School
        drawThing(regions, backAtlas, 1, 315, 700, 0.8f, 0.8f);
    }

    private void renderRoadHigh() {
        Array<TextureAtlas.AtlasRegion> regions = roadHigh.getRegions();
        //render roadHigh a_Left
        for (int i = 0, x = -50, y = 850; i < 14; i++) {
            drawThing(regions, roadHigh, 0, x, y);
            x += 47;
            y -= 27;
        }
        //render roadHigh d_Right
        for (int i = 0, x = 1351+47*3, y = 900+27*3; i < 17; i++) {
            drawThing(regions, roadHigh, 3, x, y);
            x -= 47;
            y -= 27;
        }
        //render roadHigh c_Range
        drawThing(regions, roadHigh, 2, 608+47, 472+27, 0.62f, 0.62f);
        //render roadHigh b_To
        drawThing(regions, roadHigh, 1, 608,472);
    }

    private void renderRoadLow() {
        Array<TextureAtlas.AtlasRegion> regions = roadLow.getRegions();
        //render roadLow
        for (int i = 0, x= -52, y= 310; i < 3; i++) {
            drawThing(regions, roadLow, 0, x, y);
            x += 610;
            y -= 351;
        }
    }

    private void renderCarsHigh() {
        Array<TextureAtlas.AtlasRegion> regions = carsHigh.getRegions();
        //render cars for high road
        drawThing(regions, carsHigh, 0, 100, 745);
        drawThing(regions, carsHigh, 1, 300, 696);
        drawThing(regions, carsHigh, 2, 600, 525);
        drawThing(regions, carsHigh, 3, 900, 655);
        drawThing(regions, carsHigh, 3, 1155, 800);
    }

    private void renderCarsLow() {
        Array<TextureAtlas.AtlasRegion> regions = carsLow.getRegions();
        // render cars for Low road
        drawThing(regions, carsLow, 0, 60, 660);
        drawThing(regions, carsLow, 1, 600, 390);
        drawThing(regions, carsLow, 2, 560, 390);
        drawThing(regions, carsLow, 3, 905, 210);
        drawThing(regions, carsLow, 0, 845, 210);
        drawThing(regions, carsLow, 3, 1230, 20);
    }

    private void renderBlocks() {
        Array<AtlasRegion> regions;
        // render c
        drawThing(regions = textureAtlas.getRegions(), textureAtlas, regions.size/4*2, (Gdx.graphics.getWidth()/2)-176, Gdx.graphics.getHeight()-206, 0.9f, 0.9f);
        // render c list
        for (int i = regions.size/4*2+1, x_position_blocks_small = 900, y_position_blocks_small = Gdx.graphics.getHeight()-250; i < regions.size/4*3; i++) {
            drawThing(regions = textureAtlas.getRegions(), textureAtlas, i, x_position_blocks_small, y_position_blocks_small);
            x_position_blocks_small+=73;
            y_position_blocks_small-=42;
        }
        // render d
        drawThing(regions = textureAtlas.getRegions(), textureAtlas, regions.size/4*3, 1410, Gdx.graphics.getHeight()/2-114, 0.9f, 0.9f);
        //render d list
        for (int i = regions.size/4*3+1, x_position_blocks_small = 1337, y_position_blocks_small = Gdx.graphics.getHeight()/2-132; i < regions.size; i++) {
            drawThing(regions = textureAtlas.getRegions(), textureAtlas, i, x_position_blocks_small, y_position_blocks_small);
            x_position_blocks_small-=73;
            y_position_blocks_small-=42;
        }
        //render b list
        for (int i = regions.size/4*2-1, x_position_blocks_small = 680, y_position_blocks_small = Gdx.graphics.getHeight()-251; i > regions.size/4*1; i--) {
            drawThing(regions = textureAtlas.getRegions(), textureAtlas, i, x_position_blocks_small, y_position_blocks_small);
            x_position_blocks_small-=73;
            y_position_blocks_small-=42;
        }
        // render b
        drawThing(regions = textureAtlas.getRegions(), textureAtlas, regions.size/4*1, 36, Gdx.graphics.getHeight()/2-105, 0.9f, 0.9f);
        // render a list
        for (int i = regions.size/4*1-1, x_position_blocks_small = 240, y_position_blocks_small = Gdx.graphics.getHeight()/2-133; i > regions.size/4*0; i--) {
            drawThing(regions = textureAtlas.getRegions(), textureAtlas, i, x_position_blocks_small, y_position_blocks_small);
            x_position_blocks_small+=73;
            y_position_blocks_small-=42;
        }
        //render a
        drawThing(regions = textureAtlas.getRegions(), textureAtlas, regions.size/4*0, (Gdx.graphics.getWidth()/2)-189, -12, 0.9f, 0.9f);
    }

    private void renderThings() {
        Array<TextureAtlas.AtlasRegion> regions = things.getRegions();
        //render otherThings
        //mountain
        drawThing(regions, things, 7, 700, 250);
        // adv
        drawThing(regions, things, 5, 910, 241);
        // fence
        drawThing(regions, things, 4, 1120, 90);
        drawThing(regions, things, 4, 1227, 30);
        // light SE
        drawThing(regions, things, 0, 50, 685);
        drawThing(regions, things, 0, 900, 195);
        drawThing(regions, things, 0, 380, 496);
        drawThing(regions, things, 0, 1190, 30);
        // light NE
        drawThing(regions, things, 1, 1039, Gdx.graphics.getHeight()-122);
        drawThing(regions, things, 1, 1544, 588);
        // tree and trees
        drawThing(regions, things, 2, 1332, 23);
        drawThing(regions, things, 3, 580, 440, 0.7f, 0.7f);
        // mountain
        drawThing(regions, things, 6, -20, 50);
    }

    protected void drawThing(Array<TextureAtlas.AtlasRegion> regions, TextureAtlas drawAtlas, int texture_ordinal, float x_position, float y_position, float x_Scale, float y_Scale) {
        TextureAtlas.AtlasRegion region = regions.get(texture_ordinal);
        Sprite sprite = drawAtlas.createSprite(region.name);
        sprite.setPosition(x_position, y_position);
        sprite.setSize(sprite.getWidth()*x_Scale, sprite.getHeight()*y_Scale);
        sprites.put(region.name, sprite);
        sprite.draw(batch);
    }

    protected void drawThing(Array<TextureAtlas.AtlasRegion> regions, TextureAtlas drawAtlas, int texture_ordinal, float x_position, float y_position) {
        TextureAtlas.AtlasRegion region = regions.get(texture_ordinal);
        Sprite sprite = drawAtlas.createSprite(region.name);
        sprite.setPosition(x_position, y_position);
        sprites.put(region.name, sprite);
        sprite.draw(batch);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        renderBack();
        renderRoadHigh();
        renderRoadLow();
        renderCarsHigh();
        renderCarsLow();
        renderBlocks();
        renderThings();

        batch.end();
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
        sprites.clear();
    }
}
