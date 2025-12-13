package com.game.model.battle;

import java.util.List;

import com.game.model.character.Character;

public class StaticSpeedTurn implements TurnStrategy {
    @Override
    public void sortAction(List<Character> characters) {
        return;
    }

    @Override
    public TurnIterator getTurnIterator() {
        return new ConcreteTurnIterator();
    }
}
