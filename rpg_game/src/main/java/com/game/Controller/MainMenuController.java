package com.game.controller;

import com.game.model.creator.GameCreator;
import com.game.model.creator.NewGameCreator;
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

    public void resumeGame(ActionEvent event) {
        System.out.println("Seleziona lo slot");
    }

    public void onSettings(ActionEvent event) {
        // Carica la schermata settings
        System.out.println("Impostazioni");
    }

    public void onExit(ActionEvent event) {
        // Chiusura del gioco
        ViewManager.getInstance().exit();
    }
}