package com.monopoly.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;

public class MonopolyGUI extends ApplicationAdapter {

    TextureAtlas textureAtlas, backAtlas, roadHigh, roadLow, carsHigh, carsLow, things;
    // Animation for player
    Animation<TextureRegion> playerOneWalk, playerTwoWalk, playerThreeWalk, playerFourWalk;
    Animation<TextureRegion> playerOneStand, playerTwoStand, playerThreeStand, playerFourStand;
    CharacterAnimation[] walkAnimation = new CharacterAnimation[4];

    // Animation for arrow
    Animation<TextureRegion> arrow;  

    // Animation for dices
    TextureAtlas diceAtlas;
    TextureRegion diceRegion;
    Animation<TextureRegion> dice; 
    private boolean diceStart = false;
    private int diceVal;

    // Attribute for card
    private boolean cardStart;
    private String cardText;
    Texture chance;
    BitmapFont word;

    // Attribute for game handle
    Monopoly.GameStatus gameStatus = Monopoly.GameStatus.WAITING;
    private String currentPlayer;

    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    Sprite[] boardSprite = new Sprite[32];
    final char[] boardName = "abcdefg".toCharArray();
    Sprite waitingScreen;

    SpriteBatch batch;
    float stateTime;

    @Override
    public void create() {

        textureAtlas = new TextureAtlas("playScreenAssets/block_Features.txt"); backAtlas = new TextureAtlas("playScreenAssets/back_.txt");
        roadHigh = new TextureAtlas("playScreenAssets/roadHigh_.txt");
        roadLow = new TextureAtlas("playScreenAssets/roadLow_.txt");
        carsHigh = new TextureAtlas("playScreenAssets/carsHigh_.txt");
        carsLow = new TextureAtlas("playScreenAssets/carsLow_.txt");
        things = new TextureAtlas("playScreenAssets/otherThings_.txt");
        waitingScreen = new Sprite(new Texture(Gdx.files.internal("WaitingScreen.jpg")));

        // Create player walk animation
        playerOneWalk = new Animation<TextureRegion>(0.07f, new TextureAtlas("girlWalk.txt").getRegions());
        playerTwoWalk = new Animation<TextureRegion>(0.07f, new TextureAtlas("catWalk.txt").getRegions());
        playerThreeWalk = new Animation<TextureRegion>(0.07f, new TextureAtlas("ninjaWalk.txt").getRegions());
        playerFourWalk = new Animation<TextureRegion>(0.07f, new TextureAtlas("satanWalk.txt").getRegions());
        final ArrayList<Animation<TextureRegion>> listPlayerImg = new ArrayList<Animation<TextureRegion>>();
        final ArrayList<Animation<TextureRegion>> listPlayerStandImg = new ArrayList<Animation<TextureRegion>>();
        listPlayerImg.add(playerOneWalk);
        listPlayerImg.add(playerTwoWalk);
        listPlayerImg.add(playerThreeWalk);
        listPlayerImg.add(playerFourWalk);

        // Create player stand animation
        playerOneStand = new Animation<TextureRegion>(0.1f, new TextureAtlas("girlStand.txt").getRegions());
        playerTwoStand = new Animation<TextureRegion>(0.1f, new TextureAtlas("catStand.txt").getRegions());
        playerThreeStand = new Animation<TextureRegion>(0.1f, new TextureAtlas("ninjaStand.txt").getRegions());
        playerFourStand = new Animation<TextureRegion>(0.1f, new TextureAtlas("satanStand.txt").getRegions());
        listPlayerStandImg.add(playerOneStand);
        listPlayerStandImg.add(playerTwoStand);
        listPlayerStandImg.add(playerThreeStand);
        listPlayerStandImg.add(playerFourStand);

        // Create animation for dice
        diceAtlas = new TextureAtlas("dice.txt");
        dice = new Animation<TextureRegion>(0.1f, diceAtlas.getRegions());

        batch = new SpriteBatch();

        // Create chance card GUI
        chance = new Texture("chance.jpg");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("NerkoOne-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 40;
        parameter.color = Color.BLACK;
        word = generator.generateFont(parameter);
        generator.dispose();
        
        // Create animation for arrow
        arrow = new Animation<TextureRegion> (0.5f, new TextureAtlas("arrow.txt").getRegions());
        
        // Create a new thread to run monopoly not disturb render GUI
        new Thread(new Runnable() {
          Monopoly monopoly;
          @Override
          public void run() {
            try {
              // Create an instance of Monopoly
              monopoly = new Monopoly();
            } catch (Exception e) {
              System.out.println(e);
            }

            // Initialize animation for each Player
            int i = 0;
            for (Player player : monopoly.getState().players) {
              walkAnimation[i] = new CharacterAnimation(batch, listPlayerImg.get(i), listPlayerStandImg.get(i), player.name());
              i++;
            }
            gameStatus = monopoly.getStatus();

            // Run
            int doubleCount = 0;
            while (monopoly.getState().players.size() > 1) {
              Monopoly.State state = monopoly.getState();
              state.current = state.players.peek();
              // Pass the current player who is playing
              currentPlayer = monopoly.getState().current.name();
              if (state.current.position() == state.board.busPos()) {
                int busNum = monopoly.busSelect(state.current);
                state.current.moveTo(busNum, state.board);
                for (CharacterAnimation player : walkAnimation) {
                  if (player.name.equals(currentPlayer)) {
                    player.destination = monopoly.getState().current.position();
                    break;
                  }
                }
                try {
                  Thread.sleep(1000);
                } catch (InterruptedException e) {}
                // Handle render card at ChanceBlock
                if (state.board.getBoard()[state.current.position()] instanceof ChanceBlock) {
                  cardText = monopoly.drawCard(state.current, (ChanceBlock) state.board.getBoard()[state.current.position()]);
                  cardStart = true;
                  try {
                    Thread.sleep(3500);
                  } catch (InterruptedException e) {}
                  cardStart = false;
                }
                monopoly.handleBlock(state.current, state.board.getBoard()[state.current.position()]);
                state.players.add(state.players.remove());
                continue;
              } else if (state.current.inJail()) {
                System.out.println("Would you like to get out of jail using cash?");
                if (state.current.inputBool()) {
                  state.current.excMoney(-50);
                  state.current.leaveJail();
                }
              }

              diceStart = true;
              monopoly.turn();
              // Pass the current player who is playing
              diceVal = monopoly.getDice().getVal();
              diceStart = false;
              for (CharacterAnimation player : walkAnimation) {
                if (player.name.equals(currentPlayer)) {
                  player.destination = monopoly.getState().current.position();
                  break;
                }
              }
              // Stop thread 1s to make sure data send correctly
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
              }

              if (monopoly.getDouble()) {
                doubleCount++;
              }
              if (doubleCount == 3) {
                System.out.println("Dice double 3 times. GO TO JAIL!");
                doubleCount = 0;
                state.current.toJail(state.board);
                for (CharacterAnimation player : walkAnimation) {
                  if (player.name.equals(currentPlayer)) {
                    player.destination = monopoly.getState().current.position();
                    break;
                  }
                }
                try {
                  Thread.sleep(1000);
                } catch (InterruptedException e) {}
                state.players.add(state.players.remove());
                continue;
              }

              // Handle render card at ChanceBlock
              if (state.board.getBoard()[state.current.position()] instanceof ChanceBlock) {
                cardText = monopoly.drawCard(state.current, (ChanceBlock) state.board.getBoard()[state.current.position()]);
                cardStart = true;
                try {
                  Thread.sleep(3500);
                } catch (InterruptedException e) {}
                cardStart = false;
              }

              // Handle Block at destination
              monopoly.handleBlock(state.current, state.board.getBoard()[state.current.position()]);
              // Handle change position in handleBlock()
              for (CharacterAnimation player : walkAnimation) {
                if (player.name.equals(currentPlayer)) {
                  player.destination = monopoly.getState().current.position();
                  break;
                }
              }
              // Stop thread 1s to make sure data send correctly
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
              }

              if (!monopoly.getDouble() && !monopoly.getLost()) {
                //System.out.println("Not play again and not lost");
                state.players.add(state.players.remove());
                doubleCount = 0;
              } else if (monopoly.getLost()) {
                //System.out.println("Lost");
                monopoly.setLost();
                doubleCount = 0;
                state.players.remove();
                continue;
              }
              monopoly.printState();
            }
            Player winner = monopoly.getState().players.remove();
            System.out.println("Winner is: " + winner.name());
          }
        }).start();
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
            drawThing(regions = textureAtlas.getRegions(), textureAtlas, i, x_position_blocks_small, y_position_blocks_small); x_position_blocks_small-=73;
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

        // Hash map Block Sprite to Integer
        int count = 0;
        for (int i = 0; i < 4; i++) {
          boardSprite[count] = sprites.get("" + boardName[i]);
          count++;
          for (int j = 0; j < 7; j++) {
            boardSprite[count] = sprites.get("" + boardName[i] + boardName[j]);
            count ++;
          }
        }
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

    private void renderPlayer() {
      for (int i = 0; i < 4; i++) {
        // Swap the current player to front
        if (walkAnimation[i].name.equals(currentPlayer)) {
          CharacterAnimation tmp = walkAnimation[3];
          walkAnimation[3] = walkAnimation[i];
          walkAnimation[i] = tmp;
        }
        // Draw player
        walkAnimation[i].draw(stateTime, boardSprite);
      }
      // Draw the arrow on the current player
      TextureRegion arrowFrame = arrow.getKeyFrame(stateTime, true);
      batch.draw(arrowFrame, walkAnimation[3].x + walkAnimation[3].getWidth()/2 - arrowFrame.getRegionWidth()/4, walkAnimation[3].y + walkAnimation[3].getHeight(), arrowFrame.getRegionWidth()/2, arrowFrame.getRegionHeight()/2);
    }

    private void renderDice() {
      TextureRegion diceFrame = dice.getKeyFrame(stateTime, true);
      if (diceStart) {
        diceFrame = dice.getKeyFrame(stateTime, true);
      }
      else {
        diceFrame = diceAtlas.findRegion(String.format("dice%d", diceVal)); 
      }
      batch.draw(diceFrame, Gdx.graphics.getWidth()/2 - diceFrame.getRegionWidth()/2, Gdx.graphics.getHeight()/2 - diceFrame.getRegionHeight()/2, diceFrame.getRegionWidth()*2, diceFrame.getRegionHeight()*2);
    }

    private void renderCard() {
      if (cardStart) {
        float x = Gdx.graphics.getWidth()/2 - chance.getWidth()/2;
        float y = Gdx.graphics.getHeight()/2 - chance.getHeight()/2;
        batch.draw(chance, x, y);
        word.draw(batch, cardText, x, y + chance.getHeight()*2/3, chance.getWidth()/2, Align.center, true);
      }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        batch.begin();
        switch (gameStatus) {
          case WAITING:
            waitingScreen.draw(batch);
            break;
          case PLAYING:
            // Render all thing behind
            renderBack();
            renderRoadHigh();
            renderRoadLow();
            renderCarsHigh();
            renderCarsLow();
            renderBlocks();
            renderThings();
            // Render Player and dice
            renderPlayer();
            renderDice();
            renderCard();
        }
        batch.end();
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
        sprites.clear();
    }
}
