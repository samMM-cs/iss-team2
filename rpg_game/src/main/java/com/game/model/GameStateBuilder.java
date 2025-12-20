package com.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.game.model.character.Enemy;
import com.game.model.character.Job;

public class GameStateBuilder {
    public int nPlayers = 2;
    public boolean autoSaveEnabled = false;
    public List<Job> selectedCharacters = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    public Map<Event, Boolean> storyFlags = new HashMap<>();
    public WorldPosition worldPosition = new WorldPosition();

    public GameStateBuilder setNPlayers(int nPlayers) {
        this.nPlayers = nPlayers;
        return this;
    }

    public GameStateBuilder enableAutoSave(boolean autoSave) {
        this.autoSaveEnabled = autoSave;
        return this;
    }

    public GameStateBuilder setSelectedCharacters(List<Job> characters) {
        this.selectedCharacters = characters;
        return this;
    }

    public GameStateBuilder setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
        return this;
    }

    public GameStateBuilder setStoryFlags(Map<Event, Boolean> flags) {
        this.storyFlags = flags;
        return this;
    }

    public GameStateBuilder setWorldPosition(WorldPosition pos) {
        this.worldPosition = pos;
        return this;
    }

    public boolean allCharactersSelected() {
        return selectedCharacters.size() == nPlayers;
    }

    public GameState build() {
        return new GameState(this);
    }
}
