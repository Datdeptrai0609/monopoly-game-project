package com.monopoly.gamePlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.monopoly.MonopolyOOP;
import com.monopoly.renderFunction.RenderCore;

import java.util.HashMap;
import java.util.Random;

public class GamePlay implements Screen {

    //Random
    Random random = new Random();

    // Player 3 render items
    int times3 = 0, ignore;
    int[] x_position_p3 = {1735, 1721, 1663, 1695, 1629, 1675, 1724, 1635, 1573, 1518, 1525, 1535},
            y_position_p3 = {934, 892, 925, 851, 885, 792, 777, 781, 828, 890, 809, 770}, ordinal3 = new int[12],
            x_position_p3v2 = new int[10], y_position_p3v2 = new int[10], ordinal3v2 = new int[10];

    MonopolyOOP mono = new MonopolyOOP();

    // Testing Parameter
    int X = 63;

    // HashMap:
    private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    public TextureAtlas backAtlas, textureAtlas, roadHigh, roadLow, carsHigh, carsLow, things, playerOne, playerTwo, playerThree, playerFour;

    // SPEED:
    private int SPEED = 0;

    public GamePlay (MonopolyOOP mono) {
        this.mono = mono;
    }

    @Override
    public void show() {

        textureAtlas = new TextureAtlas("playScreenAssets/blockFeature/block_Features.txt");
        backAtlas = new TextureAtlas("playScreenAssets/back/back_.txt");
        roadHigh = new TextureAtlas("playScreenAssets/roadHigh/roadHigh_.txt");
        roadLow = new TextureAtlas("playScreenAssets/roadLow/roadLow_.txt");
        carsHigh = new TextureAtlas("playScreenAssets/carsHigh/carsHigh_.txt");
        carsLow = new TextureAtlas("playScreenAssets/carsLow/carsLow_.txt");
        things = new TextureAtlas("playScreenAssets/otherThings/otherThings_.txt");

        playerOne = new TextureAtlas("playScreenAssets/PlayerOne/PlayerOne.txt/");
        playerTwo = new TextureAtlas("playScreenAssets/PlayerTwo/PlayerTwo.txt/");
        playerThree = new TextureAtlas("playScreenAssets/PlayerThree/PlayerThree.txt/");
        playerFour = new TextureAtlas("playScreenAssets/PlayerFour/PlayerFour.txt/");
    }

    // render game play ---------------------------------------------------------------------
    public void renderBack() {
        RenderCore renderBack = new RenderCore(sprites, backAtlas, mono.batch);
        //render background
        renderBack.drawThing(0, 0, 0);
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
    }


    // TODO: Render Player 1 Area
    private void renderPlayerOneArea() {
        RenderCore renderPlayerOneArea = new RenderCore(sprites, playerOne, mono.batch);
        renderPlayerOneArea.drawThing(0,-50,650, 0.6f, 0.6f);
        renderPlayerOneArea.drawThing(1,300,900);
    }

    // TODO: Render player 2 Area
    private void renderPlayerTwoArea() {
        RenderCore renderPlayerTwoArea = new RenderCore(sprites, playerTwo, mono.batch);
        for (int i = 0, x_position = 1740, y_position = 460; i < 9; i++, x_position -= 58, y_position -= 33) {
            renderPlayerTwoArea.drawThing(0,x_position,y_position);
        }
        renderPlayerTwoArea.drawThing(1, 1223,135);
        for (int i = 0, x_position = 1259, y_position = 102; i < 9; i++, x_position += 58, y_position -= 33) {
            renderPlayerTwoArea.drawThing(2,x_position,y_position);
        }
        renderPlayerTwoArea.drawThing(3,1300,-50, 0.6f, 0.6f);
    }

    // TODO: Render player 3 Area
    private void renderPlayerThreeArea() {
        RenderCore renderPlayerThreeArea = new RenderCore(sprites, playerThree, mono.batch);
        renderPlayerThreeArea.drawThing(0,1100,450, 0.6f, 0.6f);
        // Random Items
        if (times3*300<SPEED) {
            for (int i = 0; i < 12; i++) {
                ordinal3[i] = random.nextInt(3)+6;
            }
            ignore = random.nextInt(13);
            for (int i = 0; i<10; i++) {
                ordinal3v2[i] = random.nextInt(5) + 1;
                x_position_p3v2[i] = random.nextInt(501) + 1300;
                y_position_p3v2[i] = random.nextInt(401)+600;
            }
            times3++;
        }
        for (int i = 0; i < 12; i++) {
            if (i == ignore) {
            }
            else {
                renderPlayerThreeArea.drawThing(ordinal3[i], x_position_p3[i], y_position_p3[i]);
            }
        }
        for (int i = 0, count; i<10; i++) {
            renderPlayerThreeArea.drawThing(ordinal3v2[i], x_position_p3v2[i], y_position_p3v2[i]);
        }
    }

    // TODO: Render player 4 Area
    private void renderPlayerFourArea() {
        RenderCore renderPlayerFourArea = new RenderCore(sprites, playerFour, mono.batch);
        renderPlayerFourArea.drawThing(1,-350,-150, 0.6f, 0.6f);
        for (int i = 0, x_position = 0, y_position = 400; i < 8; i++, x_position += 108 , y_position -= 61 ) {
            renderPlayerFourArea.drawThing(0,x_position,y_position);
        }
    }

    // TODO: Render character area
    private void renderCharacterArea() {
        // Player One
        // Player Two
        // Player Three
        // Player Four
    }

    public void renderFullMap() {
        renderBack();
        renderPlayerOneArea();
        renderPlayerThreeArea();
        renderRoadHigh();
        renderRoadLow();
        renderCarsHigh();
        renderCarsLow();
        renderBlocks();
        renderPlayerTwoArea();
        renderPlayerFourArea();
        renderThings();
        SPEED++;
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
