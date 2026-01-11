package com.game.controller;

import com.game.model.creator.GameCreator;
import com.game.model.creator.NewGameCreator;
import com.game.model.creator.ContinueGameCreator;
import com.game.model.creator.Game;

import javafx.event.ActionEvent;

public class MainMenuController {
    public GameCreator newGameCreator = new NewGameCreator();
    public GameCreator continueGameCreator = new ContinueGameCreator();
    public ViewManager viewManager = ViewManager.getInstance();

    public MainMenuController() {
    }

    public GameController createGameController(Game game) {
        return new GameController(game);
    }

    public void onNewGame(ActionEvent event) {
        Game newGame = newGameCreator.createGame();
        createGameController(newGame).start();
    }

    public void onResumeGame(ActionEvent event) {
        Game continueGame = continueGameCreator.createGame();
        createGameController(continueGame).resume();
    }

    public void onExit(ActionEvent event) {
        viewManager.exit();
    }
}