package com.game.model;

//Here only to resolve dependence
public class WorldPosition {
    private int x;
    private int y;

    public WorldPosition() {
        this.x = 0;
        this.y = 0;
    }

    public WorldPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(WorldPosition pos) {
        this.x = pos.getX();
        this.y = pos.getY();
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {

        return "(" + x + "," + y + ")";
    }
}
