package com.monopoly.game.gameGUI;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class RenderCore {

  private HashMap<String, Sprite> sprites;
  private TextureAtlas textureAtlas;
  private SpriteBatch batch;

  public RenderCore(HashMap<String, Sprite> sprites, TextureAtlas textureAtlas, SpriteBatch batch) {
    this.sprites = sprites;
    this.textureAtlas = textureAtlas;
    this.batch = batch;
  }

  public double getSpritesWidth(int texture_ordinal) {
    Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();
    TextureAtlas.AtlasRegion region = regions.get(texture_ordinal);
    Sprite sprite = textureAtlas.createSprite(region.name);
    return sprite.getWidth();
  }

  public double getSpritesHeight(int texture_ordinal) {
    Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();
    TextureAtlas.AtlasRegion region = regions.get(texture_ordinal);
    Sprite sprite = textureAtlas.createSprite(region.name);
    return sprite.getHeight();
  }

  public void drawThing(int texture_ordinal, float x_position, float y_position) {
    Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();
    TextureAtlas.AtlasRegion region = regions.get(texture_ordinal);
    Sprite sprite = textureAtlas.createSprite(region.name);
    sprite.setPosition(x_position, y_position);
    sprites.put(region.name, sprite);
    sprite.draw(batch);
  }

  public void drawThing(int texture_ordinal, float x_position, float y_position, float x_Scale, float y_Scale) {
    Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();
    TextureAtlas.AtlasRegion region = regions.get(texture_ordinal);
    Sprite sprite = textureAtlas.createSprite(region.name);
    sprite.setPosition(x_position, y_position);
    sprite.setSize(sprite.getWidth() * x_Scale, sprite.getHeight() * y_Scale);
    sprites.put(region.name, sprite);
    sprite.draw(batch);
  }

  public int getRegionsAtlas() {
    Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();
    return regions.size;
  }

  public HashMap<String, Sprite> sprites() {
    return sprites;
  }
}
