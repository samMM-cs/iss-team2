package com.game.model.creator;

import com.game.model.GameState;

public class ContinueGameCreator extends GameCreator {
    public Game createGame() {
        return new ContinueGame(GameState.getInstance());
    }
}
