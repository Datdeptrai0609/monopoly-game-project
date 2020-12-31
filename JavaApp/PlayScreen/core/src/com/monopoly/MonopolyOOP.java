package com.monopoly;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.monopoly.gamePlay.GamePlay;
import com.monopoly.gamePlay.WaitingRoom;
import com.monopoly.gamePlay.WelcomeScreen;

public class MonopolyOOP extends Game {

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new GamePlay(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}