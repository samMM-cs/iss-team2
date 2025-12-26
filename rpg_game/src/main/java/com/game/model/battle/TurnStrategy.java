package com.game.model.battle;

public interface TurnStrategy {
    /**
     * Sort List characters in place based on chosen TurnStrategy
     */
    public abstract void sortAction();

    public abstract TurnIterator getTurnIterator();
}
