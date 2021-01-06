package com.monopoly.game.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.monopoly.game.gameCore.*;
import com.monopoly.game.MonopolyGUI;

import java.util.ArrayList;
import java.util.Queue;

public class MonopolyPlay implements Screen {
  private int[] playerId;
  // Board and background
  private GamePlay boardScreen;
  private Sprite[] boardSprite;

  // PlayerInfo
  private PlayerInfo[] listOfInfos;

  // Character Animation
  private ArrayList<CharacterAnimation> characterAnimation;

  // Animation for dices
  private DiceGUI dice;
// GUI for card
  private ChanceCardGUI chanceGUI;
  private PropCardGUI propGUI;
  private TravelCardGUI travelGUI;
  private CardGUI[] listOfCards; // Save all card as an array for easy handling
  private GreenBoard board2Select;

  // Name of blocks for player select
  private String selectBlockText;

  // Attribute for game handle
  private String currentPlayer;
  private Queue<Player> listOfPlayers;
  private Block[] board;

  private SpriteBatch batch;

  // music
  private Music playingMusic;

  public MonopolyPlay(MonopolyGUI gui, int[] playerId) {
    batch = gui.batch;
    this.playerId = playerId;
  }

  @Override
  public void show() {
    // Setup music
    playingMusic = Gdx.audio.newMusic(Gdx.files.internal("music/PlayingMusic.mp3"));
    playingMusic.setLooping(true);
    playingMusic.play();

    listOfInfos = new PlayerInfo[4];
    characterAnimation = new ArrayList<CharacterAnimation>();

    // Create boardScreen
    boardScreen = new GamePlay(batch);
    // Create dice Animation
    dice = new DiceGUI(batch);
    // Create word object to draw word in game
    //Word word = new Word();

    // Create card GUI
    chanceGUI = new ChanceCardGUI(batch);
    propGUI = new PropCardGUI(batch);
    travelGUI = new TravelCardGUI(batch);
    listOfCards = new CardGUI[] { chanceGUI, propGUI, travelGUI };

    board2Select = new GreenBoard(batch);

    // Create a new thread to run monopoly not disturb render GUI
    new Thread(new Runnable() {
      Monopoly monopoly;

      // Create BlockSelect callback function to handle render
      final class BlockSelect implements Select {
        public Block select(Player player) {
          selectBlockText = "";
          System.out.println("You own the following properties:");
          Iterable<Block> props = player.properties();

          // List of properties to select
          int counter = 1;
          for (Block bl : props) {
            String str = counter++ + ")" + bl.name();
            System.out.println(str);
            selectBlockText += (str + "   ");
          }

          // Find width of card which go belong with board2Select
          float widthCard = 0f;
          for (CardGUI card : listOfCards) {
            // If not any card on => player in festival
            if (card.isOn()) {
              widthCard = card.getWidth();
              break;
            }
          }
          board2Select.setCardAlong(widthCard);
          board2Select.boardOn(true);

          while (true) {
            int propNum = player.inputInt();
            int propState = 1;

            for (Block bl : props) {
              if (propState++ == propNum) {
                return bl;
              }
            }

            System.out.println("Please select a valid property.");
          }
        }
      }

      // Handle render and action at chance block
      private void handleChance(Monopoly.State state) {
        ChanceBlock chance = (ChanceBlock) state.board.getBoard()[state.current.position()];
        // Draw a card
        Card card = chance.draw();
        System.out.println(card.text());
        // Render card text to screen 
        chanceGUI.setContent(card.text());
        chanceGUI.cardOn(true);
        // Handle card action
        monopoly.drawCard(state.current, card, new BlockSelect());
        playerMove();
        try {
          Thread.sleep(3500);
        } catch (InterruptedException e) {
        }
      }

      // Handle render at property block
      private void handleProp(Monopoly.State state) {
        PropertyBlock prop = (PropertyBlock) state.board.getBoard()[state.current.position()];
        propGUI.setContent(prop.position(), prop.isOwned(), prop.name(), prop.priceInfo());
        propGUI.cardOn(true);
        try {
          Thread.sleep(3500);
        } catch (InterruptedException e) {
        }
      }

      private void handleTravel(Monopoly.State state) {
        TravelBlock travel = (TravelBlock) state.board.getBoard()[state.current.position()];
        travelGUI.setContent(travel.isOwned(), travel.name(), travel.priceInfo());
        travelGUI.cardOn(true);
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
      }

      // Render player move
      private void playerMove() {
        for (CharacterAnimation player : characterAnimation) {
          if (player.name().equals(currentPlayer)) {
            player.setDestinate(monopoly.getState().current.position());
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

      private void turnOffCards() {
        for (CardGUI card : listOfCards) {
          card.cardOn(false);
        }
        board2Select.boardOn(false);
      }

      @Override
      public void run() {
        try {
          // Create an instance of Monopoly
          monopoly = new Monopoly(playerId);
        } catch (Exception e) {
          System.out.println(e);
        }

        // Initialize animation for each Player
        Gdx.app.postRunnable(new Runnable() {
          @Override
          public void run() {
            int i = 0;
            for (Player player : monopoly.getState().players) {
              // Initialize playerInfo before
              listOfInfos[i] = new PlayerInfo(i, player.name(), player.getMoney(), batch);
              ++i;
              // Animation
              Animation<TextureRegion> characterAnimationIn = new Animation<TextureRegion>(0.05f,
                  new TextureAtlas(String.format("character/%sRun.txt", player.name())).getRegions());
              Animation<TextureRegion> standAnimationIn = new Animation<TextureRegion>(0.07f,
                  new TextureAtlas(String.format("character/%sStand.txt", player.name())).getRegions());
              Animation<TextureRegion> jumpAnimationIn = new Animation<TextureRegion>(0.1f,
                  new TextureAtlas(String.format("character/%sJump.txt", player.name())).getRegions());
              Animation<TextureRegion> dieAnimationIn = new Animation<TextureRegion>(0.1f,
                  new TextureAtlas(String.format("character/%sDie.txt", player.name())).getRegions());
              Texture flagIn = new Texture(String.format("house/%sFlag.png", player.name()));
              Texture houseIn = new Texture(String.format("house/%sHouse.png", player.name()));
              characterAnimation.add(new CharacterAnimation(batch, characterAnimationIn, standAnimationIn,
                  jumpAnimationIn, dieAnimationIn, flagIn, houseIn, player.name()));
            }
          }
        });

        // Run
        int doubleCount = 0;
        Monopoly.State state = monopoly.getState();
        board = state.board.getBoard();
        boardSprite = boardScreen.boardSprite();
        while (state.players.size() > 1) {
          listOfPlayers = state.players;
          state.current = state.players.peek();
          // Pass the current player who is playing
          currentPlayer = monopoly.getState().current.name();

          // Swap character animation current to front
          Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
              int size = characterAnimation.size();
              for (int i = 0; i < size; ++i) {
                if (characterAnimation.get(i).name().equals(currentPlayer)) {
                  characterAnimation.add(characterAnimation.remove(i));
                  break;
                }
              }
            }
          });

          // Handle action if player in BusBlock
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

            monopoly.handleBlock(state.current, board[state.current.position()], new BlockSelect());
            turnOffCards();
            // Check lost
            if (monopoly.getLost()) {
              monopoly.setLost();
              doubleCount = 0;
              Player loser = state.players.remove();
              for (CharacterAnimation character : characterAnimation) {
                if (character.name().equals(loser.name())) {
                  character.lost();
                  break;
                }
              }
              continue;
            }
            state.players.add(state.players.remove());
            continue;
          } else if (state.current.inJail() && state.current.getMoney() >= 50) {
            System.out.println("Would you like to get out of jail using cash?");
            if (state.current.inputBool()) {
              state.current.excMoney(-50);
              state.current.leaveJail();
            }
          }

          dice.setDiceRoll(true);
          monopoly.turn();
          // Pass the current player who is playing
          dice.setDiceVal(monopoly.getDice().getVal());
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
          monopoly.handleBlock(state.current, board[state.current.position()], new BlockSelect());
          turnOffCards();

          if (!monopoly.getDouble() && !monopoly.getLost()) {
            state.players.add(state.players.remove());
            doubleCount = 0;
          } else if (monopoly.getLost()) {
            monopoly.setLost();
            doubleCount = 0;
            Player loser = state.players.remove();
            for (CharacterAnimation character : characterAnimation) {
              if (character.name().equals(loser.name())) {
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
        for (CharacterAnimation character : characterAnimation) {
          if (character.name().equals(winner.name())) {
            character.win();
            break;
          }
        }
        System.out.println("Winner is: " + winner.name());
      }
    }).start();
  }

  private void renderPlayer() {
    int size = characterAnimation.size();
    for (int i = 0; i < size; i++) {
      // Draw player
      characterAnimation.get(i).draw(boardSprite, currentPlayer);
    }
  }

  private void renderCard() {
    for (CardGUI card : listOfCards) {
      card.render(board2Select.getWidth(), board2Select.isOn());
    }
    // Render board2Select
    board2Select.render(selectBlockText);
  }

  private void renderHouse() {
    // Render flag if purchase prop and house if build
    for (int i = 0; i < board.length; i++) {
      Block block = board[i];
      if (block.isOwned()) {
        for (CharacterAnimation character : characterAnimation) {
          if (character.name().equals(block.owner().name())) {
            int numHouse = 0;
            if (block instanceof PropertyBlock) {
              PropertyBlock prop = (PropertyBlock) block;
              numHouse = prop.numHouses();
            }
            character.houseFlag.render(boardSprite, i, numHouse);
            break;
          }
        }
      }
    }
  }

  private void renderInfo() {
    for (PlayerInfo info : listOfInfos) {
      if (info != null) {
        for (Player player : listOfPlayers) {
          if (player.name().equals(info.getName())) {
            info.updateMoney(player.getMoney());
            break;
          }
        }
        info.render();
      }
    }
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    batch.begin();
    // Render board game behind anything
    boardScreen.render();
    renderInfo();
    if (boardSprite != null) {
      renderHouse();
      // Render Player and dice
      renderPlayer();
      dice.render();
      renderCard();
    }
    batch.end();
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
    dispose();
  }

  @Override
  public void dispose() {
    dice.dispose();
    playingMusic.dispose();
  }
}
