package com.game.controller;

import com.game.model.creator.GameCreator;
import com.game.model.creator.NewGameCreator;
import com.game.model.creator.ContinueGameCreator;
import com.game.model.creator.Game;

import javafx.event.ActionEvent;

public class MainMenuController {

    public MainMenuController() {
    }

    public void onNewGame(ActionEvent event) {
        System.out.println("Avvio nuova partita");

        GameCreator gameCreator = new NewGameCreator();
        Game newGame = gameCreator.createGame();
        GameController gameController = new GameController(newGame);
        gameController.start();
    }

    public void onResumeGame(ActionEvent event) {
        System.out.println("Riprendi partita");
        GameCreator gameCreator = new ContinueGameCreator();
        Game continueGame = gameCreator.createGame();
        GameController gameController = new GameController(continueGame);
        gameController.resume();

    }

    public void onExit(ActionEvent event) {
        // Chiusura del gioco
        ViewManager.getInstance().exit();
    }
}