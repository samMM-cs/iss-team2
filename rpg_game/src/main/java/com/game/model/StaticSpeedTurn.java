package com.game.model;

import java.util.List;

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
