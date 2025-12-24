package com.game.model.battle;

import java.util.List;

import com.game.model.character.CharacterPG;

public interface TurnStrategy {
    /**
     * Sort List characters in place based on chosen TurnStrategy
     */
    public abstract void sortAction();

    public abstract TurnIterator getTurnIterator();
}
