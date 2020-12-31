package com.monopoly.game.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Random;

public class GamePlay {
  private SpriteBatch sb;
  // Get board Sprites
  final char[] boardName = "abcdefg".toCharArray();
  private Sprite[] boardSprite = new Sprite[32];
  private boolean assignDone = false;

  // Random effects:
  private Random random = new Random();

  // Player 3 render items: Constants: Coordinates
  private int times3 = 0, ignore;
  private int[] x_position_p3 = { 1635, 1621, 1563, 1595, 1529, 1575, 1624, 1535, 1473, 1418, 1425, 1435 },
      y_position_p3 = { 834, 792, 825, 751, 785, 892, 677, 681, 728, 790, 709, 670 }, ordinal3 = new int[12],
      x_position_p3v2 = new int[10], y_position_p3v2 = new int[10], ordinal3v2 = new int[10];

  // Character: Constants: Coordinates
  private float[] x_characterCoordinates = { 140 * 0.7f, 140 * 0.7f, 1800 - 140 * 0.7f, 1800 - 140 * 0.7f },
      y_characterCoordinates = { 70, 1000 - 428 * 0.7f + 100 * 0.7f, 1000 - 428 * 0.7f + 100 * 0.7f, 70 },
      y_moneyCoordinates = { 55, 1000 - 428 * 0.7f + 75 * 0.7f, 1000 - 428 * 0.7f + 75 * 0.7f, 55 };

  // Render money:
  // Money Render
  private BitmapFont moneyPlayer = new BitmapFont(Gdx.files.internal("Font/commicMoney.fnt"));
  // Layout: to get the width of text
  private GlyphLayout layout = new GlyphLayout();
  // Money parameters:
  private int[] money = { 2000, 2000, 2000, 2000 };

  // HashMap:
  private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

  // Sprites:
  public TextureAtlas backAtlas, textureAtlas, handleOnStatus, roadHigh, roadLow, carsHigh, carsLow, things, playerOne,
      playerTwo, playerThree, playerFour;
  public TextureAtlas characterAtlas, kAtlas;

  // SPEED:
  private int SPEED = 0;

  // Constructor:
  public GamePlay(SpriteBatch sb) {
    this.sb = sb;

    textureAtlas = new TextureAtlas("playScreenAssets/blockFeature/block_Features.txt");
    handleOnStatus = new TextureAtlas("playScreenAssets/blockFeature/handleOnStatus/handleOnStatus.txt");

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

    characterAtlas = new TextureAtlas("playScreenAssets/Character/Character.txt");
    kAtlas = new TextureAtlas("playScreenAssets/Character/K.txt");
  }

  // Render game play
  // ------------------------------------------------------------------------------------------------
  // Render Maps: w/out player area
  public void renderBack() {
    RenderCore renderBack = new RenderCore(sprites, backAtlas, sb);
    // render background
    renderBack.drawThing(0, 0, 0);
  }

  private void renderRoadHigh() {
    RenderCore renderCore = new RenderCore(sprites, roadHigh, sb);
    // render roadHigh a_Left
    for (int i = 0, x = -50, y = 850; i < 14; i++) {
      renderCore.drawThing(0, x, y);
      x += 47;
      y -= 27;
    }
    // render roadHigh d_Right
    for (int i = 0, x = 1351 + 47 * 3, y = 900 + 27 * 3; i < 17; i++) {
      renderCore.drawThing(3, x, y);
      x -= 47;
      y -= 27;
    }
    // render roadHigh c_Range
    renderCore.drawThing(2, 608 + 47, 472 + 27, 0.62f, 0.62f);
    // render roadHigh b_To
    renderCore.drawThing(1, 608, 472);
  }

  private void renderRoadLow() {
    RenderCore renderCore = new RenderCore(sprites, roadLow, sb);
    // render roadLow
    for (int i = 0, x = -52, y = 310; i < 3; i++) {
      renderCore.drawThing(0, x, y);
      x += 610;
      y -= 351;
    }
  }

  private void renderCarsHigh() {
    RenderCore renderCore = new RenderCore(sprites, carsHigh, sb);
    // render cars for high road
    renderCore.drawThing(0, 100, 745);
    renderCore.drawThing(1, 300, 696);
    renderCore.drawThing(2, 600, 525);
    renderCore.drawThing(3, 900, 655);
    renderCore.drawThing(3, 1155, 800);
  }

  private void renderCarsLow() {
    RenderCore renderCore = new RenderCore(sprites, carsLow, sb);
    // render cars for Low road
    renderCore.drawThing(0, 60, 660);
    renderCore.drawThing(1, 600, 390);
    renderCore.drawThing(2, 560, 390);
    renderCore.drawThing(3, 905, 210);
    renderCore.drawThing(0, 845, 210);
    renderCore.drawThing(3, 1230, 20);
  }

  private void renderBlocks() {
    RenderCore renderCore = new RenderCore(sprites, textureAtlas, sb);
    // render c
    renderCore.drawThing(renderCore.getRegionsAtlas() / 4 * 2, (Gdx.graphics.getWidth() / 2) - 176,
        Gdx.graphics.getHeight() - 206, 0.9f, 0.9f);
    // render c list
    for (int i = renderCore.getRegionsAtlas() / 4 * 2 + 1, x_position_blocks_small = 900,
        y_position_blocks_small = Gdx.graphics.getHeight() - 250; i < renderCore.getRegionsAtlas() / 4 * 3; i++) {
      renderCore.drawThing(i, x_position_blocks_small, y_position_blocks_small);
      x_position_blocks_small += 73;
      y_position_blocks_small -= 42;
      // at block 3
    }
    // render d
    renderCore.drawThing(renderCore.getRegionsAtlas() / 4 * 3, 1410, Gdx.graphics.getHeight() / 2 - 114, 0.9f, 0.9f);
    // render d list
    for (int i = renderCore.getRegionsAtlas() / 4 * 3 + 1, x_position_blocks_small = 1337,
        y_position_blocks_small = Gdx.graphics.getHeight() / 2 - 132; i < renderCore.getRegionsAtlas(); i++) {
      renderCore.drawThing(i, x_position_blocks_small, y_position_blocks_small);
      x_position_blocks_small -= 73;
      y_position_blocks_small -= 42;
      // at block 5

    }
    // render b list
    for (int i = renderCore.getRegionsAtlas() / 4 * 2 - 1, x_position_blocks_small = 680,
        y_position_blocks_small = Gdx.graphics.getHeight() - 251; i > renderCore.getRegionsAtlas() / 4 * 1; i--) {
      renderCore.drawThing(i, x_position_blocks_small, y_position_blocks_small);
      x_position_blocks_small -= 73;
      y_position_blocks_small -= 42;
      // at block 3
    }
    // render b
    renderCore.drawThing(renderCore.getRegionsAtlas() / 4 * 1, 36, Gdx.graphics.getHeight() / 2 - 105, 0.9f, 0.9f);
    // render a list
    for (int i = renderCore.getRegionsAtlas() / 4 * 1 - 1, x_position_blocks_small = 240,
        y_position_blocks_small = Gdx.graphics.getHeight() / 2 - 133; i > renderCore.getRegionsAtlas() / 4 * 0; i--) {
      renderCore.drawThing(i, x_position_blocks_small, y_position_blocks_small);
      x_position_blocks_small += 73;
      y_position_blocks_small -= 42;
      // at block 1

    }
    // render a
    renderCore.drawThing(renderCore.getRegionsAtlas() / 4 * 0, (Gdx.graphics.getWidth() / 2) - 189, -12, 0.9f, 0.9f);

    // Render done assign board Sprite to array
    if (!assignDone) {
      addBoardSprite(renderCore);
    }
  }

  private void addBoardSprite(RenderCore renderCore) {
    HashMap<String, Sprite> sprites = renderCore.sprites();
    // Assign block sprite in order to an array 
    int count = 0;
    for (int i = 0; i < 4; i++) {
      boardSprite[count] = sprites.get("" + boardName[i]);
      count++;
      for (int j = 0; j < 7; j++) {
        boardSprite[count] = sprites.get("" + boardName[i] + boardName[j]);
        count ++;
      }
      assignDone = true;
    }
  }

  public Sprite[] boardSprite() {
    return boardSprite;
  }

  private void renderThings() {
    RenderCore renderCore = new RenderCore(sprites, things, sb);
    // render otherThings
    // mountain
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
    renderCore.drawThing(1, 1039, Gdx.graphics.getHeight() - 122);
    renderCore.drawThing(1, 1544, 588);
    // tree and trees
    renderCore.drawThing(2, 1332, 23);
    renderCore.drawThing(3, 580, 440, 0.7f, 0.7f);
  }

  // Render player area:
  // TODO: Render Player 1 Area
  private void renderPlayerOneArea() {
    RenderCore renderPlayerOneArea = new RenderCore(sprites, playerOne, sb);
    renderPlayerOneArea.drawThing(0, -50, 650, 0.6f, 0.6f);
    renderPlayerOneArea.drawThing(1, 500, 900);
  }

  // TODO: Render player 2 Area
  private void renderPlayerTwoArea() {
    RenderCore renderPlayerTwoArea = new RenderCore(sprites, playerTwo, sb);
    for (int i = 0, x_position = 1740, y_position = 460; i < 9; i++, x_position -= 58, y_position -= 33) {
      renderPlayerTwoArea.drawThing(0, x_position, y_position);
    }
    renderPlayerTwoArea.drawThing(1, 1223, 135);
    for (int i = 0, x_position = 1259, y_position = 102; i < 9; i++, x_position += 58, y_position -= 33) {
      renderPlayerTwoArea.drawThing(2, x_position, y_position);
    }
    renderPlayerTwoArea.drawThing(3, 1300, -50, 0.6f, 0.6f);
  }

  // TODO: Render player 3 Area
  private void renderPlayerThreeArea() {
    RenderCore renderPlayerThreeArea = new RenderCore(sprites, playerThree, sb);
    renderPlayerThreeArea.drawThing(0, 1100, 450, 0.6f, 0.6f);
    // Random Items
    if (times3 * 300 < SPEED) {
      for (int i = 0; i < 12; i++) {
        ordinal3[i] = random.nextInt(3) + 6;
      }
      ignore = random.nextInt(13);
      for (int i = 0; i < 10; i++) {
        ordinal3v2[i] = random.nextInt(5) + 1;
        x_position_p3v2[i] = random.nextInt(501) + 1300;
        y_position_p3v2[i] = random.nextInt(401) + 600;
      }
      times3++;
    }
    for (int i = 0; i < 12; i++) {
      if (i == ignore) {
      } else {
        renderPlayerThreeArea.drawThing(ordinal3[i], x_position_p3[i], y_position_p3[i]);
      }
    }
    for (int i = 0, count; i < 10; i++) {
      renderPlayerThreeArea.drawThing(ordinal3v2[i], x_position_p3v2[i], y_position_p3v2[i]);
    }
  }

  // TODO: Render player 4 Area
  private void renderPlayerFourArea() {
    RenderCore renderPlayerFourArea = new RenderCore(sprites, playerFour, sb);
    renderPlayerFourArea.drawThing(1, -350, -150, 0.6f, 0.6f);
    for (int i = 0, x_position = 0, y_position = 400; i < 8; i++, x_position += 108, y_position -= 61) {
      renderPlayerFourArea.drawThing(0, x_position, y_position);
    }
  }

  // Render character:
  // TODO: Render character area:
  //private void setCharacterPlace(RenderCore render, int ordinal) {
    //int playerIdVal = playerId[ordinal];
    //render.drawThing(playerIdVal, (float) (x_characterCoordinates[ordinal] - render.getSpritesWidth(playerIdVal) / 2 * 0.7),
        //y_characterCoordinates[ordinal], 0.7f, 0.7f);
    //layout.setText(moneyPlayer, Integer.toString(money[0]) + "$");
    //moneyPlayer.draw(
        //sb, Integer.toString(money[0]) + "$", (float) (x_characterCoordinates[ordinal]
            //- render.getSpritesWidth(ordinal) / 2 * 0.7 + render.getSpritesWidth(1) / 2 * 0.7 - layout.width / 2),
        //y_moneyCoordinates[ordinal]);
  //}

  private void renderCharacterArea() {
    RenderCore renderK = new RenderCore(sprites, kAtlas, sb);
    if (SPEED > 30)
      renderK.drawThing(0, 0, 0, 0.7f, 0.7f);
    if (SPEED > 60)
      renderK.drawThing(1, 0, (float) (Gdx.graphics.getHeight() - renderK.getSpritesHeight(1) * 0.7), 0.7f, 0.7f);
    if (SPEED > 90)
      renderK.drawThing(2, (float) (Gdx.graphics.getWidth() - renderK.getSpritesWidth(2) * 0.7),
          (float) (Gdx.graphics.getHeight() - renderK.getSpritesHeight(2) * 0.7), 0.7f, 0.7f);
    if (SPEED > 120)
      renderK.drawThing(3, (float) (Gdx.graphics.getWidth() - renderK.getSpritesWidth(3) * 0.7), 0, 0.7f, 0.7f);

    //RenderCore renderCharacter = new RenderCore(sprites, characterAtlas, sb);
    //// Player One and Money
    //if (SPEED > 40)
      //setCharacterPlace(renderCharacter, 0);
    //// Player Two and Money
    //if (SPEED > 80)
      //setCharacterPlace(renderCharacter, 1);
    //// Player Three and Money
    //if (SPEED > 120)
      //setCharacterPlace(renderCharacter, 2);
    //// Player Four and Money
    //if (SPEED > 160)
      //setCharacterPlace(renderCharacter, 3);
  }

  // To put in render():
  public void render() {
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
    renderCharacterArea();
    SPEED++;
  }
}
