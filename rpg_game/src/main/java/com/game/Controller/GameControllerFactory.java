package com.game.controller;

public class GameControllerFactory {
    public static GameController createController(GameMode gameMode) {
        return switch(gameMode) {
            case NEW_GAME -> new NewGameController();
            case CONTINUE_GAME -> new ContinueGameController();
        };
    }
}
