package com.monopoly.game.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.monopoly.game.MonopolyGUI;

import java.util.HashMap;

public class WaitingRoom implements Screen {
  private MonopolyGUI gui;
  private SpriteBatch sb;
  // playerId: 0 -> 5
  private int[] playerId = { 1, 3, 2, 5 };

  // Get the name of character
  public final String playerNameReceive[] = { "Mina", "Eng Veit", "Mei Mei", "Laughing", "Kuerl", "Tei Tei" };
  // Player connected player
  public int playerConnected = 0;
  public int[] playerConnectedOrder = { 1, 1, 1, 1 };

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
  BitmapFont offPlayer = new BitmapFont(Gdx.files.internal("Font/commicOff.fnt"));
  public String[] playerName = { "Player 1", "Player 2", "Player 3", "Player 4" };
  BitmapFont onPlayer = new BitmapFont(Gdx.files.internal("Font/commicOn.fnt"));
  // Player information render speed
  private int SPEED = 0;

  private BitmapFont font = new BitmapFont(Gdx.files.internal("Font/commicSanFont.fnt"));
  private String checkConnected = " players connected! . . .";

  // HashMap:
  private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

  TextureAtlas waitingRoomAtlas, playerImage;

  public WaitingRoom(MonopolyGUI gui) {
    this.gui = gui;
    sb = gui.batch;

    waitingRoomAtlas = new TextureAtlas("waitingRoom/items/WaitingRoom.txt");
    playerImage = new TextureAtlas("waitingRoom/WaitingCharOff/charOff.txt");
  }

  // Layout: to get the width of text
  GlyphLayout layout = new GlyphLayout();

  @Override
  public void show() {
    //// TODO: initialize mqtt

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
            dispose();
            gui.setScreen(new MonopolyPlay(gui, playerId));
            return;
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
        layout.setText(onPlayer, playerName[(int) i]);
        onPlayer.draw(sb, playerName[(int) i], x - layout.width / 2, y);
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
  }

  // Render Status Area
  public void renderStatus() {
    // (float)(Gdx.graphics.getWidth()/renderWaitingRoom.getSpritesWidth(0)),
    // (float)(Gdx.graphics.getHeight()/renderWaitingRoom.getSpritesHeight(0)) :
    // ratio
    RenderCore renderWaitingRoom = new RenderCore(sprites, waitingRoomAtlas, sb);

    for (int i = 0; i < 4; i++) {
      if (playerConnectedOrder[i] == 1) {
        playerConnected++;
      }
    }

    // Render connected player status
    if (playerConnected == 4) {
      renderWaitingRoom.drawThing(2, 0, 900, 0.9f, 0.9f);
    } else {
      renderWaitingRoom.drawThing(1, 0, 900, 0.9f, 0.9f);
    }

    font.draw(sb, Integer.toString(playerConnected) + checkConnected, 30, 950);
    playerConnected = 0;
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
    WelcomeScreen.waitingMusic.dispose();
  }
}
