package com.game.model.battle;

import com.game.model.character.Character;

public class ConcreteTurnIterator implements TurnIterator {
    @Override
    public boolean hasCharacters() {
        return false;
    }

    @Override
    public Character nextCharacter() {
        return null;
    }

}
