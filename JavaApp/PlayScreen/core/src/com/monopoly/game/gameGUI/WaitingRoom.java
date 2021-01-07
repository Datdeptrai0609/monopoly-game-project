package com.monopoly.game.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Align;
import com.monopoly.game.MonopolyGUI;
import com.monopoly.game.gameCore.Input;
import com.monopoly.game.mqtt.Publish;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class WaitingRoom implements Screen {
  public static final String PIN = Integer.toString(new Random().nextInt(9999 - 1000) + 1000);
  private boolean startBtnPressed = false;
  private boolean startGame = false;
  private MonopolyGUI gui;
  private SpriteBatch sb;
  private BitmapFont pinDisplay;
  // playerId: 0 -> 5
  private int[] playerIdSwapped = new int[4];
  private int[] playerId = new int[4];

  // Get the name of character
  private final String playerNameReceive[] = { "Mina", "Eng Veit", "Mei Mei", "Laughing", "Kuerl", "Tei Tei" };
  // Player connected player
  private int playerConnected = 0;
  private int[] playerConnectedOrder = { 0, 0, 0, 0 };
  private RenderCore renderAvatar;

  // Define parameters area
  // ------------------------------------------------------------------------------------------
  // For core game
  public int getPlayerConnected() {
    return playerConnected;
  }

  public void setPlayerConnected(int playerConnected) {
    this.playerConnected = playerConnected;
  }

  // For player Name
  private BitmapFont offPlayer = new BitmapFont(Gdx.files.internal("Font/commicOff.fnt"));
  public String[] playerName = { "Player 1", "Player 2", "Player 3", "Player 4" };
  private BitmapFont onPlayer = new BitmapFont(Gdx.files.internal("Font/commicOn.fnt")); // Player information render speed
  private int SPEED = 0;

  private BitmapFont font = new BitmapFont(Gdx.files.internal("Font/commicSanFont.fnt"));
  private String checkConnected = " players connected! . . .";

  // HashMap:
  private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

  private TextureAtlas waitingRoomAtlas, playerImage, playerOnAvatar;

  public WaitingRoom(MonopolyGUI gui) {
    this.gui = gui;
    sb = gui.batch;
    // pin = Integer.toString(rand.nextInt(9999 - 1000) + 1000);
  }

  // Layout: to get the width of text
  GlyphLayout layout = new GlyphLayout();

  @Override
  public void show() {
    pinDisplay = new Word().word(150, Color.valueOf("#ac5288"), "NerkoOne-Regular.ttf");

    waitingRoomAtlas = new TextureAtlas("waitingRoom/items/WaitingRoom.txt");
    playerImage = new TextureAtlas("waitingRoom/WaitingCharOff/charOff.txt");
    playerOnAvatar = new TextureAtlas("waitingRoom/WaitingCharOn/characterAvatar.txt");
    renderAvatar = new RenderCore(sprites, playerOnAvatar, sb);

    // TODO: initialize mqtt
    // onConnect thread
    new Thread(new Runnable() {
      @Override
      public void run() {
        Input inputConnection = new Input("onConnect");
        while (playerConnected < 4) {
          // Check connection
          int pinReceived = inputConnection.inputInt();
          if (pinReceived == Integer.parseInt(PIN)) {
            playerConnected++;
            try {
              new Publish().pub("onConnect/" + PIN, "1");
            } catch (MqttException e) {
            }
          }
        }
        inputConnection.getSubscribe().disconnect();
      }
    }).start();

    // playerid thread
    new Thread(new Runnable() {
      @Override
      public void run() {
        Input input = new Input(PIN + "/playerid");
        // Check playerId
        int i = 0;
        boolean duplicated = false;
        while (playerId[playerId.length - 1] == 0) {
          // Receive playerId
          int id = input.inputInt();
          // Check have already id in list
          for (int j = 0; j < 4; ++j) {
            if (id == playerId[j]) {
              duplicated = true;
              break;
            }
          }
          // Assign id to list
          if (id > 0 && id < 7 && !duplicated) {
            playerConnectedOrder[i] = 1;
            playerId[i] = id;
            i++;
          } else {
            duplicated = false;
          }
        }
        input.getSubscribe().disconnect();

        // Random order for player
        ArrayList<Integer> playerIdList = new ArrayList<Integer>();
        for (int id : playerId) {
          playerIdList.add(id);
        }
        // Shuffle the list of playerId
        java.util.Collections.shuffle(playerIdList);
        // Assign to swapped
        int index = 0;
        for (int id : playerIdList) {
          playerIdSwapped[index] = id;
          ++index;
        }

        while (true) {
          if (startBtnPressed) {
            // Publish press button and player order to mqtt
            try {
              new Publish().pub(PIN + "/connect/ready", "1");
              for (int k = 0; k < playerIdSwapped.length; k++) {
                try {
                  Thread.sleep(1500);
                } catch (InterruptedException e) {}
                new Publish().pub(PIN + "/connect/order/" + playerIdSwapped[k], Integer.toString(k + 1));
              }
            } catch (MqttException e) {}
            break;
          } 
          try {
            Thread.sleep(0);
          } catch (InterruptedException e) {}
        }
      }
    }).start();

    // Confirm to play game thread
    new Thread(new Runnable(){
      @Override
      public void run() {
        // Waiting for player confirm receive order
        Input inputConfirm = new Input(PIN + "/turn/confirm");
        int countConfirm = 0;
        while (countConfirm < playerIdSwapped.length) {
          if (inputConfirm.inputBool()) {
            countConfirm++;
          }
        }
        inputConfirm.getSubscribe().disconnect();
        startGame = true;
      }
    }).start();
  }

  // Function for render player character image

  // Render waiting MQTT client
  // --------------------------------------------------------------------------------------
  public void renderWaitingRoom() {
    // (float)(Gdx.graphics.getWidth()/renderWaitingRoom.getSpritesWidth(0)),
    // (float)(Gdx.graphics.getHeight()/renderWaitingRoom.getSpritesHeight(0)) :
    // ratio
    RenderCore renderWaitingRoom = new RenderCore(sprites, waitingRoomAtlas, sb);
    // Render background
    renderWaitingRoom.drawThing(0, 0, 0, (float) (Gdx.graphics.getWidth() / renderWaitingRoom.getSpritesWidth(0)),
        (float) (Gdx.graphics.getHeight() / renderWaitingRoom.getSpritesHeight(0)));

    // Render Player Avatar
    RenderCore renderPlayerImage = new RenderCore(sprites, playerImage, sb);

    // Render player Information Status
    for (float i = 0, compareVar = 50, times = 1, x_position = 146.16f,
        y_position = Gdx.graphics.getHeight() / 2 - 220; i < 4; i++, x_position = 146.16f + 413.46f * (times - 1)) {

      // Render Player Status
      if (SPEED > compareVar * times && playerConnectedOrder[(int) times - 1] == 1) {
        renderWaitingRoom.drawThing(6, x_position, y_position, 0.95f, 0.95f);
      }
      if (SPEED > compareVar * times && playerConnectedOrder[(int) times - 1] == 0) {
        renderWaitingRoom.drawThing(5, x_position, y_position, 0.95f, 0.95f);
      }
      // Render Player Avatar
      if (SPEED > compareVar * times && playerConnectedOrder[(int) times - 1] == 0) {
        renderPlayerImage.drawThing(0, x_position + 10, (float) (y_position + renderWaitingRoom.getSpritesHeight(6) / 2
            - renderPlayerImage.getSpritesHeight(0) / 2 * 0.21), 0.21f, 0.21f);
      }
      // Render Player Avatar after choose
      if (SPEED > compareVar * times && playerConnectedOrder[(int) times - 1] == 1) {
        int index = playerId[(int) times - 1] - 1;
        renderAvatar.drawThing(index, x_position + 10, (float) (y_position
            + renderWaitingRoom.getSpritesHeight(6) * 1.15f / 2 - renderAvatar.getSpritesHeight(index) / 2 * 0.7f),
            0.7f, 0.7f);
      }

      // Render Name Block
      if (SPEED > compareVar * times) {
        renderWaitingRoom.drawThing(7, x_position, y_position + 70, 0.945f, 0.945f);
      }
      times++;

      // Render Game Play Button
      if (SPEED > compareVar * 4 && playerConnectedOrder[3] == 0) {
        renderWaitingRoom.drawThing(3,
            (float) ((Gdx.graphics.getWidth() / 2 - renderWaitingRoom.getSpritesWidth(3) / 2 * 0.6)), 80, 0.6f, 0.6f);
      }
      if (SPEED > compareVar * 4 && playerConnectedOrder[3] == 1) {
        // System.out.println(playerConnectedOrder[3]);
        renderWaitingRoom.drawThing(4,
            (float) ((Gdx.graphics.getWidth() / 2 - renderWaitingRoom.getSpritesWidth(3) / 2 * 0.6)), 80, 0.6f, 0.6f);
        if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2 + renderWaitingRoom.getSpritesWidth(3)
            && Gdx.input.getX() > Gdx.graphics.getWidth() / 2 - renderWaitingRoom.getSpritesWidth(3)
            && Gdx.input.getY() < Gdx.graphics.getHeight() - 100
            && Gdx.input.getY() > Gdx.graphics.getHeight() - 100 - renderWaitingRoom.getSpritesHeight(3)) {
          renderWaitingRoom.drawThing(4,
              (float) ((Gdx.graphics.getWidth() / 2 - renderWaitingRoom.getSpritesWidth(3) / 2 * 0.7)), 60, 0.7f, 0.7f);
          if (Gdx.input.isTouched()) {
            startBtnPressed = true;
          }
        }
      }
    }

    // layout.setText(onPlayer, playerName[1]);
    // onPlayer.draw(sb, playerName[1], 279.09f - layout.width/2,
    // Gdx.graphics.getHeight()/2 - 105);

    // Render player name
    for (float i = 0, compareVar = 50, times = 1, x = 146.16f + 133.65f,
        y = Gdx.graphics.getHeight() / 2 - 105; i < 4; i++, x = (2 * i + 1) * 133.65f + (i + 1) * 146.16f, times++) {

      if (SPEED > compareVar * times && playerConnectedOrder[(int) i] == 1) {
        layout.setText(onPlayer, playerNameReceive[playerId[(int) i] - 1]);
        onPlayer.draw(sb, playerNameReceive[playerId[(int) i] - 1], x - layout.width / 2, y);
      }
      if (SPEED > compareVar * times && playerConnectedOrder[(int) i] == 0) {
        layout.setText(onPlayer, playerName[(int) i]);
        offPlayer.draw(sb, playerName[(int) i], x - layout.width / 2, y);
      }
    }
    if (SPEED > 50 * 4) {
    } else {
      SPEED++;
    }

    // Render Status
    renderStatus();
    // Change screen to game play
    if (startBtnPressed && startGame) {
      dispose();
      gui.setScreen(new MonopolyPlay(gui, playerIdSwapped));
      return;
    }
  }

  // Render Status Area
  public void renderStatus() {
    // (float)(Gdx.graphics.getWidth()/renderWaitingRoom.getSpritesWidth(0)),
    // (float)(Gdx.graphics.getHeight()/renderWaitingRoom.getSpritesHeight(0)) :
    // ratio
    RenderCore renderWaitingRoom = new RenderCore(sprites, waitingRoomAtlas, sb);

    // for (int i = 0; i < 4; i++) {
    // if (playerConnectedOrder[i] == 1) {
    // playerConnected++;
    // }
    // }

    // Render connected player status
    if (playerConnected == 4) {
      renderWaitingRoom.drawThing(2, 0, 900, 0.9f, 0.9f);
    } else {
      renderWaitingRoom.drawThing(1, 0, 900, 0.9f, 0.9f);
    }

    font.draw(sb, Integer.toString(playerConnected) + checkConnected, 30, 950);
    // playerConnected = 0;
  }

  // Render Player Image
  public void renderPlayerImage(float x_position, float y_position) {
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    sb.begin();
    renderWaitingRoom();
    // Render Pin
    pinDisplay.draw(sb, String.format("PIN: %s", PIN), 0, Gdx.graphics.getHeight() * 0.97f, Gdx.graphics.getWidth(),
        Align.center, false);
    sb.end();
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
    offPlayer.dispose();
    onPlayer.dispose();
    font.dispose();
    waitingRoomAtlas.dispose();
    playerImage.dispose();
  }
}
