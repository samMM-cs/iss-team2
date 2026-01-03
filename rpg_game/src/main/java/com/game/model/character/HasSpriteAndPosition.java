package com.game.model.character;

import com.game.model.Position;

import javafx.scene.image.ImageView;

public interface HasSpriteAndPosition {
  public ImageView getSprite();

  public Position getPosition();
}
