package com.monopoly.gamePlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.monopoly.MonopolyOOP;
import com.monopoly.renderFunction.RenderCore;

import java.util.HashMap;

public class WaitingRoom implements Screen {

    private MonopolyOOP monopoly;

    // Define parameters area ------------------------------------------------------------------------------------------
        // Player connected player
        public int playerConnected = 0;
        public int[] playerConnectedOrder = {0, 0, 0, 0};
            // For core game
            public int getPlayerConnected() {
                return playerConnected;
            }

            public void setPlayerConnected(int playerConnected) {
                this.playerConnected = playerConnected;
            }

            // For player Name
            BitmapFont offPlayer = new BitmapFont(Gdx.files.internal("Font/commicOff.fnt"));
            public String[] playerName = {"Player 1", "Player 2", "Player 3", "Player 4"};
            BitmapFont onPlayer = new BitmapFont(Gdx.files.internal("Font/commicOn.fnt"));
        // Player information render speed
        private int SPEED = 0;

    private BitmapFont font = new BitmapFont(Gdx.files.internal("Font/commicSanFont.fnt"));
    private String checkConnected = " players connected! . . .";

    // HashMap:
    private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

    TextureAtlas waitingRoomAtlas;

    public WaitingRoom(MonopolyOOP monopoly) {
        this.monopoly = monopoly;

        waitingRoomAtlas = new TextureAtlas("waitingRoom/waitingRoom_.txt");
    }

    // Layout: to get the width of text
    GlyphLayout layout = new GlyphLayout();

    @Override
    public void show() {

    }

    // render waiting MQTT client --------------------------------------------------------------------------------------
    public void renderWaitingRoom() {
        // (float)(Gdx.graphics.getWidth()/renderWaitingRoom.getSpritesWidth(0)), (float)(Gdx.graphics.getHeight()/renderWaitingRoom.getSpritesHeight(0)) : ratio
        RenderCore renderWaitingRoom = new RenderCore(sprites, waitingRoomAtlas, monopoly.batch);
        // Render background
        renderWaitingRoom.drawThing(0, 0, 0, (float)(Gdx.graphics.getWidth()/renderWaitingRoom.getSpritesWidth(0)), (float)(Gdx.graphics.getHeight()/renderWaitingRoom.getSpritesHeight(0)));
        // Render connected player status
        if (getPlayerConnected() == 4) {
            renderWaitingRoom.drawThing(2, 0, 900, (float)(Gdx.graphics.getWidth()/renderWaitingRoom.getSpritesWidth(0)), (float)(Gdx.graphics.getHeight()/renderWaitingRoom.getSpritesHeight(0)));
        }
        else {
            renderWaitingRoom.drawThing(1, 0, 900, (float)(Gdx.graphics.getWidth()/renderWaitingRoom.getSpritesWidth(0)), (float)(Gdx.graphics.getHeight()/renderWaitingRoom.getSpritesHeight(0)));
        }

        // Render player Information Status
        for (float i = 0, compareVar = 50, times = 1, x_position = 146.16f, y_position = Gdx.graphics.getHeight()/2-190;
             i < 4;
             i++, x_position = 146.16f + 413.46f*(times - 1)) {
            if (SPEED > compareVar*times && playerConnectedOrder[(int) times - 1] == 1) {
                renderWaitingRoom.drawThing(6, x_position, y_position, (float)(Gdx.graphics.getWidth()/renderWaitingRoom.getSpritesWidth(0)), (float)(Gdx.graphics.getHeight()/renderWaitingRoom.getSpritesHeight(0)));
                System.out.println(renderWaitingRoom.getSpritesWidth(6)*(float)(Gdx.graphics.getWidth()/renderWaitingRoom.getSpritesWidth(0)));
            }
            if (SPEED > compareVar*times && playerConnectedOrder[(int) times - 1] == 0) {
                renderWaitingRoom.drawThing(5, x_position, y_position, (float)(Gdx.graphics.getWidth()/renderWaitingRoom.getSpritesWidth(0)), (float)(Gdx.graphics.getHeight()/renderWaitingRoom.getSpritesHeight(0)));
            }
            times++;
            if (SPEED > compareVar*4 && playerConnectedOrder[3] == 0 ) {
                renderWaitingRoom.drawThing(3, (float) (Gdx.graphics.getWidth()/2 - renderWaitingRoom.getSpritesWidth(3)/2), 100, (float)(Gdx.graphics.getWidth()/renderWaitingRoom.getSpritesWidth(0)), (float)(Gdx.graphics.getHeight()/renderWaitingRoom.getSpritesHeight(0)));
            }
            if (SPEED > compareVar*4 && playerConnectedOrder[3] == 1 ) {
                System.out.println(playerConnectedOrder[3]);
                renderWaitingRoom.drawThing(4, (float) (Gdx.graphics.getWidth()/2 - renderWaitingRoom.getSpritesWidth(3)/2), 100, (float)(Gdx.graphics.getWidth()/renderWaitingRoom.getSpritesWidth(0)), (float)(Gdx.graphics.getHeight()/renderWaitingRoom.getSpritesHeight(0)));
                if (Gdx.input.getX() < Gdx.graphics.getWidth()/2 + renderWaitingRoom.getSpritesWidth(3) && Gdx.input.getX() > Gdx.graphics.getWidth()/2 - renderWaitingRoom.getSpritesWidth(3)
                        && Gdx.input.getY() < Gdx.graphics.getHeight() - 100 && Gdx.input.getY() > Gdx.graphics.getHeight() - 100 - renderWaitingRoom.getSpritesHeight(3)
                ) {
                    renderWaitingRoom.drawThing(4, (float) (Gdx.graphics.getWidth()/2 - renderWaitingRoom.getSpritesWidth(3)/2*1.2f), 90.55f, 1.2f, 1.2f);
                    if (Gdx.input.isTouched()) {
                        dispose();
                        monopoly.setScreen(new GamePlay(monopoly));
                    }
                }
            }
        }

//        layout.setText(onPlayer, playerName[1]);
//        onPlayer.draw(monopoly.batch, playerName[1], 279.09f - layout.width/2, Gdx.graphics.getHeight()/2 - 105);

        // Render player name
        for (float i = 0, compareVar = 50, times = 1, x = 146.16f + 133.65f, y = Gdx.graphics.getHeight()/2 - 105; i < 4;
             i++, x = (2*i+1)*133.65f + (i+1)*146.16f, times++) {
            if (SPEED > compareVar*times && playerConnectedOrder[(int) i] == 1) {
                layout.setText(onPlayer, playerName[(int) i]);
                onPlayer.draw(monopoly.batch, playerName[(int) i], x - layout.width/2, y);
            }
            if (SPEED > compareVar*times && playerConnectedOrder[(int) i] == 0) {
                layout.setText(onPlayer, playerName[(int) i]);
                offPlayer.draw(monopoly.batch, playerName[(int) i], x - layout.width/2, y);
            }
        }
        if (SPEED>50*4) {}
        else {SPEED++;}
    }

    // Render Status Area
    public void renderStatus() {
        font.draw(monopoly.batch, Integer.toString(playerConnected) + checkConnected, 30, 950);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        monopoly.batch.begin();
        renderWaitingRoom();
        renderStatus();
        monopoly.batch.end();
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
