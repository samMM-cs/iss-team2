package com.game.model.battle;

import com.game.model.character.CharacterPG;

public class ConcreteTurnIterator implements TurnIterator {
    @Override
    public boolean hasCharacters() {
        return false;
    }

    @Override
    public CharacterPG nextCharacter() {
        return null;
    }

}
