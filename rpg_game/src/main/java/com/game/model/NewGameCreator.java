package com.game.model;

//import com.game.model.Game;

public class NewGameCreator extends GameCreator{
    public Game createGame() {
        return new NewGame();
    }
}