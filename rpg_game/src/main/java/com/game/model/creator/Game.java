package com.game.model.creator;

import com.game.model.GameState;

public abstract class Game {
    
    protected GameState gameState;

    protected Game(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return this.gameState;
    }
}