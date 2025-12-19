package com.game.controller;

import com.game.model.creator.GameCreator;
import com.game.model.creator.NewGameCreator;
import com.game.model.creator.Game;

import javafx.event.ActionEvent;
import javafx.stage.*;

public class MainMenuController {
    private Stage stage;

    public MainMenuController(Stage stage) {
        this.stage = stage;
    }

    public void onNewGame(ActionEvent event) {
        // Carica schermata di gioco
        if (stage == null) {
            System.err.println("Stage null in MainMenuController");
            return;
        }
        System.out.println("Avvio nuova partita");

        GameCreator gameCreator = new NewGameCreator();
        Game newGame = gameCreator.createGame();
        GameController gameController = new GameController(stage,newGame);
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
        stage.close();
    }
}