package com.game.controller;

import com.game.view.gameview.NewGameView;
import com.game.model.GameState;
import com.game.view.CharacterSelectionView;
import com.game.view.mapview.ExplorationView;
import com.game.model.character.Job;

import javafx.stage.Stage;

public class GameController {
    private GameState gameState;
    private Stage stage;


    public GameController(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        if (stage == null) {
            System.err.println("Stage nullo in GameController");
            return;
        }
        NewGameView view = new NewGameView(stage, this);
        view.show();
    }

    public void onNewGameConfirmed(int players, boolean autoSave) {
        if (players < 1 || players > 4) {
            System.out.println("Invalid number of players");
            return;
        }

        gameState = new GameState(players, autoSave);
        goToCharacterSelection(); // Passa alla selezione dei personaggi
    }

    // Mostra la schermata per la selezione dei personaggi
    public void goToCharacterSelection() {
        if (stage == null)
            return;
        CharacterSelectionView charView = new CharacterSelectionView(stage, this);
        charView.show();
    }

    // Viene chiamato quando selezioniamo un personaggio
    public void onCharacterSelected(Job job) {
        if (gameState != null)
            gameState.selectCharacter(job);
        if (gameState.allCharactersSelected()) {
            gameState.createParty();
            startExploration();
        }
    }

    // Avvia l'esplorazione della mappa
    public void startExploration() {
        if (gameState != null) {
            ExplorationView explorationView = new ExplorationView(stage, this, gameState);
            explorationView.showMap();
        }
    }

    public void handleStoryChoice(String choice) {
    }
}