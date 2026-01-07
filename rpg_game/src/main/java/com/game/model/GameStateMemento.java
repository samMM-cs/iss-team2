package com.game.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import com.game.model.character.*;

public class GameStateMemento implements Serializable {
    public int nPlayers;
    public boolean autoSaveEnabled;

    public List<Job> selectedCharacters;
    public WorldPosition worldPosition;
    public Map<Event, Boolean> storyFlags;

    public Inventory inventory;

    public String mapId; // es: "Map1"

    public GameStateMemento(GameState gameState) {
        this.selectedCharacters = new ArrayList<>(gameState.selectedCharacters);
        this.worldPosition = gameState.worldPosition;
        this.storyFlags = new HashMap<>(gameState.storyFlags);
        this.nPlayers = gameState.nPlayers;
        this.autoSaveEnabled = gameState.autoSaveEnabled;
        this.inventory = gameState.inventory;
        this.mapId = gameState.getMap() != null ? gameState.getMap().getClass().getName() : null;
    }

    public GameStateMemento() {
    }
}
