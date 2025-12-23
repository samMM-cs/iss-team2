package com.game.model.battle;

import java.util.Comparator;
import java.util.List;

import com.game.model.character.CharacterPG;

public class StaticSpeedTurn implements TurnStrategy {
    @Override
    public void sortAction(List<CharacterPG> characters) {
        Comparator<CharacterPG> cmp= Comparator.comparingInt(c->c.getCurrentStats().getSpeed());
        characters.sort(cmp.reversed());
    }

    @Override
    public TurnIterator getTurnIterator() {
        return new ConcreteTurnIterator();
    }
}
