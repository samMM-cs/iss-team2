package com.game.model.creator;

import com.game.model.GameState;

public class NewGameCreator extends GameCreator{
    public Game createGame() {
        return new NewGame(GameState.getInstance());
    }
}