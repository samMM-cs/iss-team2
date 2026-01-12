package com.game.controller;

import com.game.model.GameState;

public abstract class GameController {
    private SaveManager saveManager = new SaveManager();

    public abstract void start();

    // Avvia l'esplorazione della mappa
    public void startExploration() {
        if (GameState.getInstance() != null) {
            GameState.getInstance().createEnemy();
            GameState.getInstance().createParty();
            GameState.getInstance().createNpc();
            ViewManager.getInstance().showExplorationView();
        }
    }

    public void saveGame(int slot) {
        try {
            saveManager.saveGame(slot, GameState.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGame(int slot) {
        try {
            saveManager.loadGame(slot);
            ViewManager.getInstance().showExplorationView();
            System.out.println("Game loaded successfully." + slot);
        } catch (Exception e) {
            System.out.println("Load failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadFromAutosave() {
        try {
            new GameState.GameStateBuilder().build();
            saveManager.loadGameFromAutoSave();
            ViewManager.getInstance().showExplorationView();
            System.out.println("Autosave loaded successfully.");
        } catch (Exception e) {
            System.out.println("Load failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }

}