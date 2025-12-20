package com.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.game.model.character.Enemy;
import com.game.model.character.Player;
import com.game.model.character.Party;
import com.game.model.character.Job;

public class GameState {
    private final int nPlayers;
    private final boolean autoSaveEnabled;
    private final List<Job> selectedCharacters;
    private final List<Enemy> enemies;
    private Party party;
    private final Map<Event, Boolean> storyFlags;
    private final WorldPosition worldPosition;

    // Costruttore privato, il Builder lo costruisce
    GameState(GameStateBuilder builder) {
        this.nPlayers = builder.nPlayers;
        this.autoSaveEnabled = builder.autoSaveEnabled;
        this.selectedCharacters = builder.selectedCharacters;
        this.storyFlags = builder.storyFlags;
        this.worldPosition = builder.worldPosition;
        this.enemies = new ArrayList<>();
    }

    public int getNPlayers() {
        return this.nPlayers;
    }

    public boolean isAutoSaveEnable() {
        return autoSaveEnabled;
    }

    public void selectCharacter(Job job) {
        if (selectedCharacters.size() == nPlayers)
            return;
        selectedCharacters.add(job);
    }

    public boolean allCharactersSelected() {
        return selectedCharacters.size() == nPlayers;
    }

    public void createParty() {
        if (!allCharactersSelected())
            return;
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < selectedCharacters.size(); i++) {
            Job selectedJob = selectedCharacters.get(i);
            Position pos = new Position(selectedCharacters.size() - i - 1, 7);

            Player p = new Player(selectedJob, pos);
            if (i > 0) {
                p.subscribeToFollowed(players.get(i - 1));
            }
            players.add(p);
        }
        this.party = new Party(players);
    }

    public void createEnemy() {
        enemies.clear();

        List<Job> enemiesJob = List.of(Job.GOBLIN, Job.GOBLIN2);
        List<Position> pos = List.of(new Position(2, 2), new Position(12, 8));
        for (int i = 0; i < enemiesJob.size(); i++) {
            Position newPos = new Position(pos.get(i).getX(), pos.get(i).getY());
            Enemy e = new Enemy(enemiesJob.get(i), newPos);

            enemies.add(e);
        }
    }
    public void applyChoices(String choice) {

    }

    public void setFlag(Event flag) {
        this.storyFlags.replace(flag, true);
    }

    public boolean getFlag(Event flag) {
        return this.storyFlags.get(flag);
    }

    public boolean isAutoSaveEnabled() {
        return autoSaveEnabled;
    }

    public List<Job> getSelectedCharacters() {
        return selectedCharacters;
    }

    public List<Enemy> getEnemies() {
        return this.enemies;
    }

    public Party getParty() {
        return party;
    }

    public Map<Event, Boolean> getStoryFlags() {
        return storyFlags;
    }

    public WorldPosition getWorldPosition() {
        return worldPosition;
    }
}