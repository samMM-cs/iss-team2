package com.game.model;

public class Position {
  private int x;
  private int y;

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Position add(Position position) {
    this.x += position.getX();
    this.y += position.getY();
    return this;
  }

}
