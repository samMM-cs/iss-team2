package com.game.model.creator;

public class NewGameCreator extends GameCreator{
    public Game createGame() {
        return new NewGame();
    }
}