package com.game.controller;

import com.game.model.GameState;
import com.game.model.character.Job;
import com.game.model.creator.Game;
import com.game.model.creator.NewGame;
import com.game.model.map.Map1;

public class GameController {
    private Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void start() {
        if (game instanceof NewGame) {
            GameState.destroy();
            ViewManager.getInstance().destroyViews();
            ViewManager.getInstance().showNewGameView(this);
        } else
            startExploration();
    }

    public void onNewGameConfirmed(int players, boolean autoSave) {
        if (players < 1 || players > 4) {
            System.out.println("Invalid number of players");
            return;
        }

        new GameState.GameStateBuilder().setNPlayers(players)
                .enableAutoSave(autoSave).setMap(new Map1())
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
            ViewManager.getInstance().showExplorationView(GameState.getInstance().getMap());
        }
    }

    public void handleStoryChoice(String choice) {
        if (GameState.getInstance() != null)
            GameState.getInstance().applyChoices(choice);
    }
}