package com.game.model.battle;

import java.util.List;

import com.game.model.character.CharacterPG;

public class StaticSpeedTurn implements TurnStrategy {
    @Override
    public void sortAction(List<CharacterPG> characters) {
        return;
    }

    @Override
    public TurnIterator getTurnIterator() {
        return new ConcreteTurnIterator();
    }
}
