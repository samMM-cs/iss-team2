package com.game.model;

import java.util.Map;
import java.util.List;

//May need Builder design pattern
public class GameState {
    public final int nPlayers;
    //public final List<Job> jobs;
    public boolean autoSaveEnabled;
    public String currentScene;
    public Map<Event, Boolean> storyFlags;
    public List<String> playerChoices;
    public WorldPosition worldPosition;

    public GameState(int nPlayers, boolean autoSaveEnabled) {
        this.nPlayers= nPlayers;
        this.autoSaveEnabled= autoSaveEnabled;
    }

    public int getNPlayers() {
        return this.nPlayers;
    }

    public void setAutoSave(boolean flag) {
        this.autoSaveEnabled= flag;
    }

    public void setCurrentScene(String currentScene) {
        this.currentScene = currentScene;
    }

    public void setStoryFlags(Map<Event, Boolean> storyFlags) {
        this.storyFlags = storyFlags;
    }

    public void setPlayerChoices(List<String> playerChoices) {
        this.playerChoices = playerChoices;
    }

    public void setWorldPosition(WorldPosition worldPosition) {
        this.worldPosition = worldPosition;
    }

    public void applyChoices(String choice) {

    }

    public void setFlag (Event flag) {
        this.storyFlags.replace(flag, true);
    }

    public boolean getFlag(Event flag) {
        return this.storyFlags.get(flag);
    }
}
