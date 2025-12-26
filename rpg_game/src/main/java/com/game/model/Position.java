package com.game.model;

public record Position(double x, double y) {

    public static Position Origin = new Position(0, 0);

    public Position add(double x, double y) {
        return new Position(this.x + x, this.y + y);
    }

    public Position add(Position pos) {
        return add(pos.x, pos.y);
    }

    public Position sub(Position pos) {
        return add(-pos.x, -pos.y);
    }

    public Position scale(double s) {
        return new Position(x * s, y * s);
    }

    public Position clamp(double xLimit, double yLimit) {
        return clamp(0, 0, xLimit, yLimit);
    }

    public double max() {
        return Math.max(x, y);
    }

    public Position clamp(double xMin, double yMin, double xMax, double yMax) {
        double xMin_ = Math.min(xMin, xMax);
        double yMin_ = Math.min(yMin, yMax);
        double xMax_ = Math.max(xMin, xMax);
        double yMax_ = Math.max(yMin, yMax);
        return new Position(Math.round(Math.clamp(x, xMin_, xMax_)),
                Math.round(Math.clamp(y, yMin_, yMax_)));
    }

    public Position clamp(Position posMin, Position posMax) {
        return clamp(posMin.x, posMin.y, posMax.x, posMax.y);
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
