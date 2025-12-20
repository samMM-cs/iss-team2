package com.game.model;

public record Position(double x, double y) {

    public Position add(double x, double y) {
        return new Position(this.x + x, this.y + y);
    }

    public Position add(Position pos) {
        return add(pos.x, pos.y);
    }

    public Position scale(double s) {
        return new Position(x * s, y * s);
    }

    public Position clamp(double xLimit, double yLimit) {
        return clamp(0, 0, xLimit, yLimit);
    }

    public Position clamp(double xMin, double xMax, double xLimit, double yLimit) {
        return new Position(Math.round(Math.clamp(x, xMin, xLimit)),
                Math.round(Math.clamp(y, xMax, yLimit)));
    }

    public boolean isInside(Position posLimit) {
        double xLimit = posLimit.x;
        double yLimit = posLimit.y;
        return 0 <= x && x <= xLimit && 0 <= y && y <= yLimit;
    }

    @Override
    public String toString() {
        return "Position(" + x + ", " + y + ")";
    }

}
