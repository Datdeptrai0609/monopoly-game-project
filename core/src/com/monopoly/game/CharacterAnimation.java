package com.monopoly.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class CharacterAnimation {
  SpriteBatch sb;
  Animation<TextureRegion> walk;
  Animation<TextureRegion> stand;
  TextureRegion animationFrame;
  String name;
  Rectangle character;
  int position, destination, step;
  float x, y, angle;
  
  public CharacterAnimation(SpriteBatch sb, Animation<TextureRegion> walkAnimation, Animation<TextureRegion> standAnimation, String name) {
    this.sb = sb;
    this.walk = walkAnimation;
    this.stand = standAnimation;
    this.name = name;
    position = destination = 0;
    step = 1;
  }

  public float getWidth() {
    return animationFrame.getRegionWidth()/3;
  }

  public float getHeight() {
    return animationFrame.getRegionHeight()/3;
  }

  public void draw(float stateTime, Sprite[] board) {
    float xPosition = board[position].getX() + board[position].getWidth()/3;
    float yPosition = board[position].getY() + board[position].getHeight()/2;

    float xPositionNext, yPositionNext;
    // Handle the position between block 31 and 0
    if (position + step == board.length) {
      xPositionNext = board[0].getX() + board[0].getWidth()/3;
      yPositionNext = board[0].getY() + board[0].getHeight()/2;
    } else {
      xPositionNext = board[position + step].getX() + board[position + step].getWidth()/3;
      yPositionNext = board[position + step].getY() + board[position + step].getHeight()/2;
    }
    // Set coordinates at start of the game
    if (position == 0 && destination == 0) {
      x = xPosition;
      y = yPosition;
      animationFrame = stand.getKeyFrame(stateTime, true);
      sb.draw(animationFrame, x, y, animationFrame.getRegionWidth()/3, animationFrame.getRegionHeight()/3);
    } 
    // Come to the destination change to stand animation
    else if (position == destination) {
      animationFrame = stand.getKeyFrame(stateTime, true);
      sb.draw(animationFrame, x, y, animationFrame.getRegionWidth()/3, animationFrame.getRegionHeight()/3);
    } 
    // While player don't come to the next block continue walkAnimation and
    // increase x and y
    else if (Math.abs(x - xPositionNext) > 3 && Math.abs(y - yPositionNext) > 3) {
      angle = (float) Math.atan2(yPositionNext - yPosition, xPositionNext - xPosition);
      animationFrame = walk.getKeyFrame(stateTime, true);
      // Flip the animation by X
      if (position >= 0 && position < 8 || position >= 24) {
        if (!animationFrame.isFlipX()) {
          animationFrame.flip(true, false);
        }
      } else {
        if (animationFrame.isFlipX()) {
          animationFrame.flip(true, false);
        }
      }
      sb.draw(animationFrame, x, y, animationFrame.getRegionWidth()/3, animationFrame.getRegionHeight()/3);
      // Animation move to the next block
      x += (float) Math.cos(angle)*350*Gdx.graphics.getDeltaTime();
      y += (float) Math.sin(angle)*350*Gdx.graphics.getDeltaTime();
    } else {
      // If come to the next block, set position to this block
      position++;
      if (position == 32) {
        position = 0;
      }
      sb.draw(animationFrame, x, y, animationFrame.getRegionWidth()/3, animationFrame.getRegionHeight()/3);
    } 
  }
}
