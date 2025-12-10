package com.game.model;

public interface TurnIterator {
    public abstract boolean hasCharacters();
    public abstract Character nextCharacter();
}