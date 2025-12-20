package com.game.model.battle;

import com.game.model.character.CharacterPG;

public interface TurnIterator {
    public abstract boolean hasCharacters();

    public abstract CharacterPG nextCharacter();
}