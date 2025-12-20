package com.game.model.creator;

public class ContinueGameCreator extends GameCreator {
    public Game createGame() {
        return new ContinueGame();
    }
}
