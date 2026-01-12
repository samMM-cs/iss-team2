package com.game.controller;

import java.util.List;

import com.game.model.GameState;
import com.game.model.character.Job;
import com.game.model.map.Map1;
import com.game.model.map.Map2;
import com.game.model.map.Map3;
import com.game.model.story.FlagMap;
import com.game.model.story.StoryNodeLoader;

public class NewGameController extends GameController {
    @Override
    public void start() {
        GameState.destroy();
        if (ViewManager.getInstance() != null)
            ViewManager.getInstance().destroyViews();
        ViewManager.getInstance().showNewGameView(this);
    }

    public void onNewGameConfirmed(int players, boolean autoSave) {
        if (players < 1 || players > 4) {
            System.out.println("Invalid number of players");
            return;
        }

        new GameState.GameStateBuilder().setNPlayers(players)
                .enableAutoSave(autoSave).setMaps(List.of(new Map1(), new Map2(), new Map3()))
                .setFlagMap(new FlagMap())
                .setStoryNode(StoryNodeLoader.load())
                .build();
        ViewManager.getInstance().showCharacterSelectionView(this);
    }

    // Viene chiamato quando selezioniamo un personaggio
    public void onCharacterSelected(Job job) {
        if (GameState.getInstance() != null)
            GameState.getInstance().selectCharacter(job);
    }
}
