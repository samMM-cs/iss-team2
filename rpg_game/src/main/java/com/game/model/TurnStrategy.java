package com.game.model;

import java.util.List;

public interface TurnStrategy {
    //Works in-place
    public abstract void sortAction(List<Character> characters);
    public abstract TurnIterator getTurnIterator();
}
