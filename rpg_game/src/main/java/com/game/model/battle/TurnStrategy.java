package com.game.model.battle;

import java.util.List;

import com.game.model.character.Character;

public interface TurnStrategy {
    // Works in-place
    public abstract void sortAction(List<Character> characters);

    public abstract TurnIterator getTurnIterator();
}
