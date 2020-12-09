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

    ArrayList<CharacterAnimation> walkAnimation;

    // Animation for arrow on the head of player
    Animation<TextureRegion> arrow;  

    // Animation for dices
    TextureAtlas diceAtlas;
    TextureRegion diceRegion;
    Animation<TextureRegion> dice; 
    private boolean diceStart = false;
    private int diceVal;

    // Attribute for card
    private boolean chanceCard, propertyCard, travelCard;
    private String cardText, propName, propPrice, travelName, travelPrice;
    Texture chanceImg, propertyImg, travelImg;
    // Words will be draw on card picture
    BitmapFont word, word2;

    // Attribute for game handle
    Monopoly.GameStatus gameStatus = Monopoly.GameStatus.WAITING;
    private String currentPlayer;
    private Block[] boardRender;
    private boolean assignDone = false;

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

        // Create animation for dice
        diceAtlas = new TextureAtlas("dice.txt");
        dice = new Animation<TextureRegion>(0.1f, diceAtlas.getRegions());

        batch = new SpriteBatch();

        // Create chance card GUI
        chanceImg = new Texture("chance.jpg");
        travelImg = new Texture("travelCard.jpg");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("NerkoOne-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
        parameter.size = 40;
        parameter2.size = 30;
        parameter.color = parameter2.color = Color.BLACK;
        word = generator.generateFont(parameter);
        word2 = generator.generateFont(parameter2);
        generator.dispose();
        chanceCard = propertyCard = travelCard = false;
        
        // Create animation for arrow
        arrow = new Animation<TextureRegion> (0.5f, new TextureAtlas("arrow.txt").getRegions());
        walkAnimation = new ArrayList<CharacterAnimation>();

        // Create a new thread to run monopoly not disturb render GUI
        new Thread(new Runnable() {
          Monopoly monopoly;

          // Handle render and action at chance block
          private void handleChance(Monopoly.State state) {
            ChanceBlock chance = (ChanceBlock) state.board.getBoard()[state.current.position()];
            // Draw a card
            Card card = chance.draw();
            System.out.println(card.text());
            // Render card text to screen
            cardText = card.text();
            chanceCard = true;
            // Handle card action
            monopoly.drawCard(state.current, card);
            playerMove();
            try {
              Thread.sleep(3500);
            } catch (InterruptedException e) {}
            chanceCard = false;
          }

          // Handle render at property block
          private void handleProp(Monopoly.State state) {
            PropertyBlock prop = (PropertyBlock) state.board.getBoard()[state.current.position()];
            propName = prop.name();
            propPrice = prop.priceInfo();
            propertyCard = true;
            try {
              Thread.sleep(2500);
            } catch (InterruptedException e) {}
          }

          private void handleTravel(Monopoly.State state) {
            TravelBlock travel = (TravelBlock) state.board.getBoard()[state.current.position()];
            travelName = travel.name();
            travelPrice = travel.priceInfo();
            travelCard = true;
            try {
              Thread.sleep(2000);
            } catch (InterruptedException e) {}
          }

          // Render player move
          private void playerMove() {
            for (CharacterAnimation player : walkAnimation) {
              if (player.name.equals(currentPlayer)) {
                player.destination = monopoly.getState().current.position();
                break;
              }
            }
            // Thread sleep 1s to player move
            try {
              Thread.sleep(1000);
            } catch (InterruptedException e) {
              System.out.println("Thread interrupted");
            }
          }

          @Override
          public void run() {
            try {
              // Create an instance of Monopoly
              monopoly = new Monopoly();
            } catch (Exception e) {
              System.out.println(e);
            }

            // Initialize animation for each Player
            Gdx.app.postRunnable(new Runnable() {
              @Override
              public void run() {
                for (Player player : monopoly.getState().players) {
                  Animation<TextureRegion> walkAnimationIn = new Animation<TextureRegion>(0.05f, new TextureAtlas(String.format("%sRun.txt", player.name())).getRegions());
                  Animation<TextureRegion> standAnimationIn = new Animation<TextureRegion>(0.07f, new TextureAtlas(String.format("%sStand.txt", player.name())).getRegions());
                  Animation<TextureRegion> jumpAnimationIn = new Animation<TextureRegion>(0.07f, new TextureAtlas(String.format("%sJump.txt", player.name())).getRegions());
                  Animation<TextureRegion> dieAnimationIn = new Animation<TextureRegion>(0.1f, new TextureAtlas(String.format("%sDie.txt", player.name())).getRegions());
                  Texture flagIn = new Texture(String.format("%sFlag.png", player.name()));
                  Texture houseIn = new Texture(String.format("%sHouse.png", player.name()));
                  walkAnimation.add(new CharacterAnimation(batch, walkAnimationIn, standAnimationIn, jumpAnimationIn, dieAnimationIn, flagIn, houseIn, player.name()));
                }
              }
            });
            gameStatus = monopoly.getStatus();

            // Run
            int doubleCount = 0;
            Monopoly.State state = monopoly.getState();
            Block[] board = state.board.getBoard();
            while (state.players.size() > 1) {
              boardRender = board;
              state.current = state.players.peek();
              // Pass the current player who is playing
              currentPlayer = monopoly.getState().current.name();
              if (state.current.position() == state.board.busPos()) {
                int busNum = monopoly.busSelect(state.current);
                state.current.moveTo(busNum, state.board);

                playerMove();
                // Handle render card at ChanceBlock or handle card at
                // PropertyBlock
                if (board[state.current.position()] instanceof ChanceBlock) {
                  handleChance(state);
                } else if (board[state.current.position()] instanceof PropertyBlock) {
                  handleProp(state);
                } else if (board[state.current.position()] instanceof TravelBlock) {
                  handleTravel(state);
                }

                monopoly.handleBlock(state.current, board[state.current.position()]);
                propertyCard = travelCard = false;
                // Check lost
                if (monopoly.getLost()) {
                  monopoly.setLost();
                  doubleCount = 0;
                  Player loser = state.players.remove(); 
                  for (CharacterAnimation character : walkAnimation) {
                    if (character.name.equals(loser.name())) {
                      character.lost();
                      break;
                    }
                  }
                  continue;
                }
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
              if (monopoly.getDouble()) {
                doubleCount++;
              }
              if (doubleCount == 3) {
                System.out.println("Dice double 3 times. GO TO JAIL!");
                doubleCount = 0;
                state.current.toJail(state.board);
                playerMove();
                state.players.add(state.players.remove());
                continue;
              }
              playerMove();


              // Handle render card at ChanceBlock and card at Property
              if (board[state.current.position()] instanceof PropertyBlock) {
                handleProp(state);
              } else if (board[state.current.position()] instanceof ChanceBlock) {
                handleChance(state);
              } else if (board[state.current.position()] instanceof TravelBlock) {
                handleTravel(state);
              }

              // Handle Block at destination
              monopoly.handleBlock(state.current, board[state.current.position()]);
              propertyCard = travelCard = false;

              if (!monopoly.getDouble() && !monopoly.getLost()) {
                //System.out.println("Not play again and not lost");
                state.players.add(state.players.remove());
                doubleCount = 0;
              } else if (monopoly.getLost()) {
                //System.out.println("Lost");
                monopoly.setLost();
                doubleCount = 0;
                Player loser = state.players.remove(); 
                for (CharacterAnimation character : walkAnimation) {
                  if (character.name.equals(loser.name())) {
                    character.lost();
                    break;
                  }
                }
                continue;
              }
              monopoly.printState();
            }
            Player winner = monopoly.getState().players.remove();
            currentPlayer = winner.name();
            for (CharacterAnimation character : walkAnimation) {
              if (character.name.equals(winner.name())) {
                character.win();
                break;
              }
            }
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

        // Assign block sprite in order to an array 
        if (!assignDone) {
          int count = 0;
          for (int i = 0; i < 4; i++) {
            boardSprite[count] = sprites.get("" + boardName[i]);
            count++;
            for (int j = 0; j < 7; j++) {
              boardSprite[count] = sprites.get("" + boardName[i] + boardName[j]);
              count ++;
            }
          }
          assignDone = true;
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
      int size = walkAnimation.size();
      for (int i = 0; i < size; i++) {
        // Swap the current player to front
        if (walkAnimation.get(i).name.equals(currentPlayer)) {
          walkAnimation.add(walkAnimation.remove(i));
        }
        // Draw player
        walkAnimation.get(i).draw(boardSprite);
      }
      // Draw the arrow on the current player
      TextureRegion arrowFrame = arrow.getKeyFrame(stateTime, true);
      batch.draw(arrowFrame, walkAnimation.get(size-1).x + walkAnimation.get(size-1).getWidth()/2 - arrowFrame.getRegionWidth()/4, walkAnimation.get(size-1).y + walkAnimation.get(size-1).getHeight(), arrowFrame.getRegionWidth()/2, arrowFrame.getRegionHeight()/2);
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
      if (chanceCard) {
        float x = Gdx.graphics.getWidth()/2 - chanceImg.getWidth()/2;
        float y = Gdx.graphics.getHeight()/2 - chanceImg.getHeight()/2;
        batch.draw(chanceImg, x, y);
        word.draw(batch, cardText, x, y + chanceImg.getHeight()*2/3, chanceImg.getWidth()/2, Align.center, true);
      } else if (propertyCard) {
        propertyImg = new Texture(String.format("propCard%s.png", walkAnimation.get(walkAnimation.size()-1).destination));
        float x = Gdx.graphics.getWidth()/2 - propertyImg.getWidth()/2;
        float y = Gdx.graphics.getHeight()/2 - propertyImg.getHeight()/2;
        batch.draw(propertyImg, x, y);
        word.draw(batch, propName, x, y + propertyImg.getHeight() * 6/7, propertyImg.getWidth(), Align.center, false);
        word2.draw(batch, propPrice, x + propertyImg.getWidth() * 0.65f, y + propertyImg.getHeight() * 0.72f);
      } else if (travelCard) {
        float x = Gdx.graphics.getWidth()/2 - travelImg.getWidth()/2;
        float y = Gdx.graphics.getHeight()/2 - travelImg.getHeight()/2;
        batch.draw(travelImg, x, y);
        word.draw(batch, travelName, x, y + travelImg.getHeight()* 2/3, travelImg.getWidth(), Align.center, false);
        word2.draw(batch, travelPrice, x + travelImg.getWidth() * 0.7f, y + travelImg.getHeight() * 0.55f);
      }
    }

    private void renderHouse() {
      // Render flag if purchase prop and house if build
      for (int i = 0; i < boardRender.length; i++) {
        if (boardRender[i].isOwned()) {
          for (CharacterAnimation character : walkAnimation) {
            if (character.name.equals(boardRender[i].owner().name())) {
              Texture flag = character.flag;
              Texture house = character.house;
              float x, y;
              if ((i > 0 && i < 8) || (i > 16 && i < 24)) {
                x = boardSprite[i].getX() + boardSprite[i].getWidth() * 0.6f;
                y = boardSprite[i].getY() + boardSprite[i].getHeight() * 0.7f;
              } else {
                x = boardSprite[i].getX() + boardSprite[i].getWidth() * 0.2f;
                y = boardSprite[i].getY() + boardSprite[i].getHeight() * 0.65f;
              }
              if (boardRender[i] instanceof PropertyBlock) {
                PropertyBlock prop = (PropertyBlock) boardRender[i];
                if (prop.isOwned() && prop.numHouses() == 0) {
                  batch.draw(flag, x, y);
                } else {
                  float xHouse2, yHouse2, xHouse3, yHouse3, xHouse4, yHouse4;
                  yHouse2 = y - house.getHeight()/5;
                  yHouse3 = y - house.getHeight()/4.5f;
                  yHouse4 = yHouse3 - house.getHeight()/5;
                  if ((i > 0 && i < 8) || (i > 16 && i < 24)) {
                    xHouse2 = x + house.getWidth()/3;
                    xHouse3 = x - house.getWidth()/2.5f;
                    xHouse4 = xHouse3 + house.getWidth()/3;
                  } else {
                    xHouse2 = x - house.getWidth()/3;
                    xHouse3 = x + house.getWidth()/2.5f;
                    xHouse4 = xHouse3 - house.getWidth()/3;
                  }
                  switch (prop.numHouses()) {
                    case 1:
                      batch.draw(house, x, y);
                      break;
                    case 2:
                      batch.draw(house, x, y);
                      batch.draw(house, xHouse2, yHouse2);
                      break;
                    case 3:
                      batch.draw(house, x, y);
                      batch.draw(house, xHouse2, yHouse2);
                      batch.draw(house, xHouse3, yHouse3);
                      break;
                    case 4:
                      batch.draw(house, x, y);
                      batch.draw(house, xHouse2, yHouse2);
                      batch.draw(house, xHouse3, yHouse3);
                      batch.draw(house, xHouse4, yHouse4);
                      break;
                  }
                }
              } else {
                batch.draw(flag, x, y);
              }
              break;
            }
          }
        }
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
            // Render board all thing behind
            renderBack();
            renderRoadHigh();
            renderRoadLow();
            renderCarsHigh();
            renderCarsLow();
            renderBlocks();
            renderThings();
            renderHouse();
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
