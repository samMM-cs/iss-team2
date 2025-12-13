package com.game.model.battle;

import com.game.model.character.Character;

public interface TurnIterator {
    public abstract boolean hasCharacters();

    public abstract Character nextCharacter();
}