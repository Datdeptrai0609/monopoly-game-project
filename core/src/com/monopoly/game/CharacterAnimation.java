package com.monopoly.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class CharacterAnimation {
  SpriteBatch sb;
  Animation<TextureRegion> walk;
  Animation<TextureRegion> stand;
  Animation<TextureRegion> jump;
  Animation<TextureRegion> die;
  Texture flag, house;
  TextureRegion animationFrame;
  String name;
  Rectangle character;
  int position, destination;
  float x, y, angle, stateTime;
  private boolean lost, win;
  
  public CharacterAnimation(SpriteBatch sb, Animation<TextureRegion> walkAnimation, Animation<TextureRegion> standAnimation, Animation<TextureRegion> jumpAnimation, Animation<TextureRegion> dieAnimation, Texture flag, Texture house, String name) {
    this.sb = sb;
    this.walk = walkAnimation;
    this.stand = standAnimation;
    this.jump = jumpAnimation;
    this.die = dieAnimation;
    this.name = name;
    this.flag = flag;
    this.house = house;
    win = lost = false;
    position = destination = 0;
  }

  public float getWidth() {
    return animationFrame.getRegionWidth()/3;
  }

  public float getHeight() {
    return animationFrame.getRegionHeight()/3;
  }

  // Return the coordinate x, y for player moving
  private float[] coordinates(Sprite[] board, int position) {
    float[] coord = new float[2];
    if (position > 0 && position < 8 || position > 16 && position < 24) {
      coord[0] = board[position].getX() + board[position].getWidth()/9;
      coord[1] = board[position].getY() + board[position].getHeight()/3;
    } else if (position == 0 || position == 8 || position == 16 || position == 24) {
      coord[0] = board[position].getX() + board[position].getWidth()/3;
      coord[1] = board[position].getY() + board[position].getHeight()/3;
    } else {
      coord[0] = board[position].getX() + board[position].getWidth()/2.7f;
      coord[1] = board[position].getY() + board[position].getHeight()/3;
    }
    return coord;
  }

  public void lost() {
    lost = true;
    stateTime = 0f;
  }
   
  public void win() {
    win = true;
    stateTime = 0f;
  }

  public void draw(Sprite[] board) {
    if (lost) {
      if (!die.isAnimationFinished(stateTime)) {
        animationFrame = die.getKeyFrame(stateTime);
      }
    } else if (win) {
      animationFrame = jump.getKeyFrame(stateTime, true);
    } else {
      float xPosition = coordinates(board, position)[0]; 
      float yPosition = coordinates(board, position)[1];

      int posNext = position + 1;
      // Handle the position between block 31 and 0
      if (position + 1 == board.length) {
        posNext = 0;
      }
      float xPositionNext = coordinates(board, posNext)[0]; 
      float yPositionNext = coordinates(board, posNext)[1];
      // Set coordinates at start of the game
      if (position == 0 && destination == 0) {
        x = xPosition;
        y = yPosition;
        animationFrame = stand.getKeyFrame(stateTime, true);
      } 
      // Come to the destination change to stand animation
      else if (position == destination) {
        animationFrame = stand.getKeyFrame(stateTime, true);
      } 
      // While player don't come to the next block continue walkAnimation and
      // increase x and y
      else if (Math.abs(x - xPositionNext) > 4 && Math.abs(y - yPositionNext) > 4) {
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
        // Animation move to the next block
        x += (float) Math.cos(angle)*350*Gdx.graphics.getDeltaTime();
        y += (float) Math.sin(angle)*350*Gdx.graphics.getDeltaTime();
      } else {
        // If come to the next block, set position to this block
        position++;
        if (position == 32) {
          position = 0;
        }
      } 
    }
    sb.draw(animationFrame, x, y, animationFrame.getRegionWidth()/3, animationFrame.getRegionHeight()/3);
    stateTime += Gdx.graphics.getDeltaTime();
  }
}
