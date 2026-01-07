package com.game.model;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.game.model.character.*;

public class GameStateMemento implements Serializable {
    public int nPlayers;
    public boolean autoSaveEnabled;

    public Party party;
    public WorldPosition worldPosition;
    public Map<Event, Boolean> storyFlags;
    public List<Enemy> enemies;

    // public Inventory inventory;

    public String mapId; // es: "Map1"

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = MerchantNPC.class, name = "MerchantNPC"),
            @JsonSubTypes.Type(value = MovesNPC.class, name = "MovesNPC")
    })
    public List<NPC> npc;

    public GameStateMemento(GameState gameState) {
        // this.selectedCharacters = new ArrayList<>(gameState.getParty());
        this.party = gameState.getParty();
        this.enemies = gameState.getEnemies();
        this.worldPosition = gameState.worldPosition;
        this.storyFlags = new HashMap<>(gameState.storyFlags);
        this.nPlayers = gameState.nPlayers;
        this.autoSaveEnabled = gameState.autoSaveEnabled;
        // this.inventory = gameState.inventory;
        this.mapId = gameState.getMap() != null ? gameState.getMap().getClass().getName() : null;
        this.npc = gameState.getNpc();
    }

    public GameStateMemento() {
    }

    @Override
    public String toString() {
        return "GameStateMemento{" +
                "nPlayers=" + nPlayers +
                ", autoSaveEnabled=" + autoSaveEnabled +
                ", mapId=" + mapId +
                ", worldPosition=" + (worldPosition != null ? worldPosition.toString() : "null") +
                ", party=" + (party != null ? party.toString() : "null") +
                ", enemies=" + (enemies != null ? enemies.size() + " enemies" : "null") +
                ", npc=" + (npc != null ? npc.size() + " npcs" : "null") +
                ", storyFlags=" + (storyFlags != null ? storyFlags.size() + " flags" : "null") +
                '}';
    }
}
