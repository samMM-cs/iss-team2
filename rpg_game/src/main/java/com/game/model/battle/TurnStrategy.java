package com.game.model.battle;

import java.util.List;

import com.game.model.character.CharacterPG;

public interface TurnStrategy {
    // Works in-place
    public abstract void sortAction(List<CharacterPG> characters);

    public abstract TurnIterator getTurnIterator();
}
