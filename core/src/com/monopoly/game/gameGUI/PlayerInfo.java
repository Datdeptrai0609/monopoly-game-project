package com.monopoly.game.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerInfo {
  private int id;
  private int money;
  private Texture playerImg; 
  private Texture backGround;
  private SpriteBatch sb;
  private float xBackGround, yBackGround, xPlayer, yPlayer;

  public PlayerInfo(int id, String name, int money, SpriteBatch sb) {
    this.id = id;
    this.money = money;
    playerImg = new Texture(String.format("character/%sInfo.png", name));
    backGround = new Texture(String.format("playScreenAssets/Character/k%d.png", id));
    this.sb = sb;
  }

  public void render() {
    switch (id) {
      case 0:
        xBackGround = yBackGround = 0f;
        break;
      case 1:
        xBackGround = 0f;
        yBackGround = Gdx.graphics.getHeight() - backGround.getHeight() * 0.7f;
        break;
      case 2:
        xBackGround = Gdx.graphics.getWidth() - backGround.getWidth() * 0.7f;
        yBackGround = Gdx.graphics.getHeight() - backGround.getHeight() * 0.7f;
        break;
      case 3:
        xBackGround = Gdx.graphics.getWidth() - backGround.getWidth() * 0.7f;
        yBackGround = 0f;
        break;
      default:
        return;
    }
    sb.draw(backGround, xBackGround, yBackGround, backGround.getWidth() * 0.7f, backGround.getHeight() * 0.7f);
  }
}
