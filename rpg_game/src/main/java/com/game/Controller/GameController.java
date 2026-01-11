package com.game.controller;

import java.util.List;

import com.game.model.GameState;
import com.game.model.character.Job;
import com.game.model.creator.ContinueGame;
import com.game.model.creator.Game;
import com.game.model.map.Map1;
import com.game.model.map.Map2;
import com.game.model.map.Map3;
import com.game.model.story.FlagMap;
import com.game.model.story.StoryNodeLoader;

public class GameController {
    private Game game;
    private SaveManager saveManager;

    public GameController(Game game) {
        this.game = game;
        this.saveManager = new SaveManager();
    }

    public void start() {
        GameState.destroy();
        if (ViewManager.getInstance() != null)
            ViewManager.getInstance().destroyViews();
        ViewManager.getInstance().showNewGameView(this);
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

        new GameState.GameStateBuilder().setNPlayers(players)
                .enableAutoSave(autoSave).setMaps(List.of(new Map1(), new Map2(), new Map3()))
                .setFlagMap(new FlagMap())
                .setStoryNode(new StoryNodeLoader().start)
                .build();
        ViewManager.getInstance().showCharacterSelectionView(this);
    }

    // Viene chiamato quando selezioniamo un personaggio
    public void onCharacterSelected(Job job) {
        if (GameState.getInstance() != null)
            GameState.getInstance().selectCharacter(job);
    }

    // Avvia l'esplorazione della mappa
    public void startExploration() {
        if (GameState.getInstance() != null) {
            GameState.getInstance().createEnemy();
            GameState.getInstance().createParty();
            GameState.getInstance().createNpc();
            ViewManager.getInstance().showExplorationView();
        }
    }

    public void handleStoryChoice(String choice) {
        if (GameState.getInstance() != null)
            GameState.getInstance().applyChoices(choice);
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