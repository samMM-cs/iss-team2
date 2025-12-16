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

  public boolean isInside(Position posLimit) {
    int xLimit = posLimit.getX();
    int yLimit = posLimit.getY();
    return 0 <= x && x <= xLimit && 0 <= y && y <= yLimit;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return "Position(" + x + ", " + y + ")";
  }

}
