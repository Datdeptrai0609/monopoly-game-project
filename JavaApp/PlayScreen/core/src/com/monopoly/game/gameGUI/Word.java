package com.monopoly.game.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Word {
  FreeTypeFontGenerator generator;
  FreeTypeFontParameter parameter;

  public Word() {
    //generator = new FreeTypeFontGenerator(Gdx.files.internal("Font/NerkoOne-Regular.ttf"));
    parameter = new FreeTypeFontParameter();
  }

  public BitmapFont word(int size, Color color, String fontName) {
    generator = new FreeTypeFontGenerator(Gdx.files.internal(String.format("Font/%s", fontName)));
    parameter.size = size;
    parameter.color = color;
    return generator.generateFont(parameter);
  }
}
