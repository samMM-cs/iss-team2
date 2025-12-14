package com.game.model;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import com.game.model.character.*;

//May need Builder design pattern
public class GameState {
    public final int nPlayers;
    public boolean autoSaveEnabled;
    public List<Job> selectedCharacters = new ArrayList<>();
    private Party party;

    public String currentScene;
    public Map<Event, Boolean> storyFlags;
    public List<String> playerChoices;
    public WorldPosition worldPosition;

    public GameState(int nPlayers, boolean autoSaveEnabled) {
        this.nPlayers = nPlayers;
        this.autoSaveEnabled = autoSaveEnabled;
    }

    public List<Player> createParty() {
        if (!allCharactersSelected() || this.party != null)
            return null;

        List<Player> members = new ArrayList<>();
        for (int i = 0; i < selectedCharacters.size(); i++) {
            Job selectedJob = selectedCharacters.get(i);
            Position pos = new Position(i * Job.SIZE, i * Job.SIZE);

            Player p = new Player(selectedJob, pos);

            if (i > 0) {
                Player leader = members.get(i - 1);
                p.setFollower(leader);
            }
            members.add(p);
        }
        return members;
    }

    public int getNPlayers() {
        return this.nPlayers;
    }

    public Party getParty() {
        return party;
    }

    public boolean isAutoSaveEnable() {
        return autoSaveEnabled;
    }

    public void selectCharacter(Job job) {
        selectedCharacters.add(job);
        if (selectedCharacters.size() == nPlayers)
            return;
    }

    public boolean allCharactersSelected() {
        return selectedCharacters.size() == nPlayers;
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

    public void setFlag(Event flag) {
        this.storyFlags.replace(flag, true);
    }

    public boolean getFlag(Event flag) {
        return this.storyFlags.get(flag);
    }

    public List<Job> getSelectedCharacters() {
        return selectedCharacters;
    }
}
