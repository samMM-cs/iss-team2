package com.game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.game.model.character.Enemy;
import com.game.model.character.Player;
//import com.game.model.creator.Game;
import com.game.model.character.Party;
import com.game.model.character.Job;
import com.game.model.character.Inventory;

public class GameState {
    private final int nPlayers;
    private final boolean autoSaveEnabled;
    private final List<Job> selectedCharacters;
    private final List<Enemy> enemies;
    private Party party;
    private Inventory inventory;
    private final Map<Event, Boolean> storyFlags;
    private final WorldPosition worldPosition;
    private static GameState instance;

    // Costruttore privato, il Builder lo costruisce
    private GameState(GameStateBuilder builder) {
        this.nPlayers = builder.nPlayers;
        this.autoSaveEnabled = builder.autoSaveEnabled;
        this.selectedCharacters = builder.selectedCharacters;
        this.inventory = builder.inventory;
        this.storyFlags = builder.storyFlags;
        this.worldPosition = builder.worldPosition;
        this.enemies = new ArrayList<>();
    }

    public static GameState getInstance() {
        return GameState.instance;
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
            if (i > 0)
                p.subscribeToFollowed(players.get(i - 1));
            players.add(p);
        }
        this.party = new Party(players);
    }

    public void createEnemy() {
        enemies.clear();

        List<Job> enemiesJob = List.of(Job.GOBLIN, Job.GOBLIN2);
        List<Position> pos = List.of(new Position(2, 2), new Position(12, 8));
        for (int i = 0; i < enemiesJob.size(); i++) {
            Position newPos = new Position(pos.get(i).x(), pos.get(i).y());
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

    // ----------------------------------------------------------------------------------------

    public static class GameStateBuilder {
        public int nPlayers = 2;
        public boolean autoSaveEnabled = false;
        public List<Job> selectedCharacters = new ArrayList<>();
        public List<Enemy> enemies = new ArrayList<>();
        public Inventory inventory;
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

        public GameStateBuilder setInventory() {
            this.inventory = new Inventory();
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
            if (GameState.instance == null) {
                GameState.instance = new GameState(this);
            }
            return GameState.instance;
        }
    }
}