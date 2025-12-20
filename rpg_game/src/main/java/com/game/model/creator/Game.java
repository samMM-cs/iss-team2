package com.game.model.creator;

public abstract class Game {
    
    protected String gameState;

    protected Game(String gameState) {
        this.gameState = gameState;
    }

    public String getGameState() {
        return this.gameState;
    }
}