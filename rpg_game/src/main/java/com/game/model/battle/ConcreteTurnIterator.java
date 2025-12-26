package com.game.model.battle;

import java.util.Iterator;
import java.util.List;

import com.game.model.character.CharacterPG;

public final class ConcreteTurnIterator implements TurnIterator {
    private final Iterator<CharacterPG> iterator;
    
    public ConcreteTurnIterator(List<CharacterPG> characters) {
        this.iterator= characters.iterator();
    }
    @Override
    public boolean hasCharacters() {
        return this.iterator.hasNext();
    }

    @Override
    public CharacterPG nextCharacter() {
        return this.iterator.next();
    }

}
