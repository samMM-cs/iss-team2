package com.game.model.battle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.game.model.character.CharacterPG;
import com.game.model.character.Enemy;
import com.game.model.character.Party;

public final class StaticSpeedTurn implements TurnStrategy {
    private final List<CharacterPG> characters;

    public StaticSpeedTurn(List<CharacterPG> characters) {
        this.characters= characters;
    }

    public StaticSpeedTurn(Party party, Enemy enemy) {
        this.characters= new ArrayList<>();
        characters.add(enemy);
        characters.addAll(party.getMembers());
    }

    @Override
    public void sortAction() {
        Comparator<CharacterPG> cmp= Comparator.comparingInt(c->c.getCurrentStats().getSpeed());
        this.characters.sort(cmp.reversed());
    }

    @Override
    public TurnIterator getTurnIterator() {
        return new ConcreteTurnIterator(this.characters);
    }

    /**
     * Only for test
     * @return this.characters
     */
    public List<CharacterPG> getCharacters() {
        return this.characters;
    }
}
