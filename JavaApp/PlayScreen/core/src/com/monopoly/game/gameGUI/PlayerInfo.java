package com.monopoly.game.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class PlayerInfo {
  private int id;
  private String name;
  private int money;
  private Texture playerImg; 
  private Texture backGround;
  private SpriteBatch sb;
  private BitmapFont word;
  private float xBackGround, yBackGround, xPlayer, yPlayer;
  private float scale;

  public PlayerInfo(int id, String name, int money, SpriteBatch sb) {
    this.id = id;
    this.name = name;
    this.money = money;
    playerImg = new Texture(String.format("character/%sInfo.png", name));
    backGround = new Texture(String.format("playScreenAssets/Character/k%d.png", id));
    word = new Word().word(60, Color.WHITE, "NerkoOne-Regular.ttf");
    this.sb = sb;
    scale = 0.7f;
  }

  public void updateMoney(int money) {
    this.money = money;
  }

  public String getName() {
    return name;
  }

  public void render() {
    switch (id) {
      case 0:
        xBackGround = yBackGround = 0f;
        break;
      case 1:
        xBackGround = 0f;
        yBackGround = Gdx.graphics.getHeight() - backGround.getHeight() * scale;
        break;
      case 2:
        xBackGround = Gdx.graphics.getWidth() - backGround.getWidth() * scale;
        yBackGround = Gdx.graphics.getHeight() - backGround.getHeight() * scale;
        break;
      case 3:
        xBackGround = Gdx.graphics.getWidth() - backGround.getWidth() * scale;
        yBackGround = 0f;
        break;
      default:
        return;
    }
    xPlayer = xBackGround + backGround.getWidth() * scale / 2 - playerImg.getWidth() * scale / 2;
    yPlayer = yBackGround + backGround.getHeight() * scale / 4;
    sb.draw(backGround, xBackGround, yBackGround, backGround.getWidth() * scale, backGround.getHeight() * scale);
    sb.draw(playerImg, xPlayer, yPlayer, playerImg.getWidth() * scale, playerImg.getHeight() * scale);
    word.draw(sb, String.format("$ %d", money), xBackGround, yBackGround + backGround.getWidth()*0.2f, backGround.getWidth()*scale, Align.center, false);
  }
}
