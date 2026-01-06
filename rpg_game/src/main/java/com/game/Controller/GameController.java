package com.game.controller;

import com.game.model.GameState;
import com.game.model.character.Job;
import com.game.model.creator.ContinueGame;
import com.game.model.creator.Game;
import com.game.model.creator.NewGame;
import com.game.model.map.Map1;

public class GameController {
    private GameState gameState;
    private Game game;
    private SaveManager saveManager;

    public GameController(Game game) {
        this.game = game;
        this.saveManager = new SaveManager();
    }

    public void start() {
        if (game instanceof NewGame)
            ViewManager.getInstance().showNewGameView(this);
        else
            startExploration();

    }

    public void resume() {
        if (game instanceof ContinueGame)
            ViewManager.getInstance().showContinueGameView(this);
    }

    public void onNewGameConfirmed(int players, boolean autoSave) {
        if (players < 1 || players > 4) {
            System.out.println("Invalid number of players");
            return;
        }

        gameState = new GameState.GameStateBuilder().setNPlayers(players).enableAutoSave(autoSave).setMap(new Map1())
                .build();
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
            ViewManager.getInstance().showExplorationView(gameState.getMap(), this);
        }
    }

    public void handleStoryChoice(String choice) {
        if (gameState != null)
            gameState.applyChoices(choice);
    }

    public void saveGame(int slot) {
        if (gameState == null)
            return;
        try {
            saveManager.saveGame(slot, gameState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGame(int slot) {
        try {
            gameState = new GameState.GameStateBuilder().setMap(new Map1()).build();
            saveManager.loadGame(slot, gameState);
            ViewManager.getInstance().showExplorationView(gameState.getMap(), this);
            System.out.println("Game loaded successfully." + slot);
        } catch (Exception e) {
            System.out.println("Load failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }
}