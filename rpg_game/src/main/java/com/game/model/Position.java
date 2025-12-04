package com.game.model;

public class Position {
  private int x;
  private int y;

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Position add(int x, int y) {
    return new Position(this.x + x, this.y + y);
  }

  public Position clamp(double xLimit, double yLimit) {
    return new Position((int) Math.round(Math.clamp(x, 0, xLimit)),
        (int) Math.round(Math.clamp(y, 0, yLimit)));
  }

}
