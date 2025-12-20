package com.game.controller;

import com.game.view.gameview.NewGameView;
import com.game.model.GameState;
import com.game.view.CharacterSelectionView;
import com.game.view.mapview.ExplorationView;
import com.game.model.character.Job;
import com.game.model.creator.Game;
import com.game.model.creator.NewGame;

import javafx.stage.Stage;

public class GameController {
    private GameState gameState;
    private Game game;
    private Stage stage;

    public GameController(Stage stage, Game game) {
        this.stage = stage;
        this.game = game;
    }

    public void start() {
        if (stage == null) {
            System.err.println("Stage nullo in GameController");
            return;
        }
        if (game instanceof NewGame) {
            NewGameView view = new NewGameView(stage, this);
            view.show();
        } else
            startExploration();
    }

    public void onNewGameConfirmed(int players, boolean autoSave) {
        if (players < 1 || players > 4) {
            System.out.println("Invalid number of players");
            return;
        }

        gameState = new GameState.GameStateBuilder().setNPlayers(players).enableAutoSave(autoSave).build();
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
    }

    // Avvia l'esplorazione della mappa
    public void startExploration() {
        if (gameState != null && stage != null) {
            gameState.createEnemy();
            gameState.createParty();
            ExplorationView explorationView = new ExplorationView(stage, this, gameState);
            explorationView.showMap();
        }
    }

    public void handleStoryChoice(String choice) {
        if (gameState != null)
            gameState.applyChoices(choice);
    }
}