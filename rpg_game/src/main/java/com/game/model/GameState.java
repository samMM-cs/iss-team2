package com.game.model;

import java.util.Map;
import java.util.List;

public class GameState {
    boolean autoSaveEnabled;
    String currentScene;
    Map<Event, Boolean> storyFlags;
    List<String> playerChoices;
    WorldPosition worldPosition;

    public void applyChoices(String choice) {

    }

    public void setFlag (Event flag) {
        this.storyFlags.replace(flag, true);
    }

    public boolean getFlag(Event flag) {
        return this.storyFlags.get(flag);
    }
}
