package com.game.controller;

import com.game.model.GameState;
import com.game.model.character.Job;
import com.game.model.creator.Game;
import com.game.model.creator.NewGame;

public class GameController {
    private GameState gameState;
    private Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void start() {
        if (game instanceof NewGame)
            ViewManager.getInstance().showNewGameView(this);
        else
            startExploration();
    }

    public void onNewGameConfirmed(int players, boolean autoSave) {
        if (players < 1 || players > 4) {
            System.out.println("Invalid number of players");
            return;
        }

        gameState = new GameState.GameStateBuilder().setNPlayers(players).enableAutoSave(autoSave).build();
        ViewManager.getInstance().showCharacterSelectionView(this);
    }

    // Viene chiamato quando selezioniamo un personaggio
    public void onCharacterSelected(Job job) {
        if (gameState != null)
            gameState.selectCharacter(job);
    }

    // Avvia l'esplorazione della mappa
    public void startExploration() {
        if (gameState != null) {
            gameState.createEnemy();
            gameState.createParty();
            gameState.createNpc();
            ViewManager.getInstance().showExplorationView();
        }
    }

    public void handleStoryChoice(String choice) {
        if (gameState != null)
            gameState.applyChoices(choice);
    }
}