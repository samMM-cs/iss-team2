package com.game.model;

import java.util.ArrayList;
import java.util.List;

import com.game.model.character.Enemy;
import com.game.model.character.HasSpriteAndPosition;
import com.game.model.character.Player;
import com.game.model.map.Map1;
import com.game.model.story.Flag;
import com.game.model.story.FlagMap;
import com.game.model.story.StoryNode;
import com.game.model.character.Party;
import com.game.model.character.Job;
import com.game.model.character.NPC;
import com.game.model.character.Inventory;

public class GameState {
    public final int nPlayers;
    public final boolean autoSaveEnabled;
    public final List<Job> selectedCharacters;
    private final List<Enemy> enemies;
    private final List<NPC> npc;
    public Party party;
    public Inventory inventory;
    public final WorldPosition worldPosition;
    private static GameState instance;
    private final List<HasSpriteAndPosition> sprites = new ArrayList<>();
    public com.game.model.map.Map map;
    public String mapId;
    public FlagMap flagMap;
    public StoryNode currentStoryNode;
    

    // Costruttore privato, il Builder lo costruisce
    private GameState(GameStateBuilder builder) {
        this.nPlayers = builder.nPlayers;
        this.autoSaveEnabled = builder.autoSaveEnabled;
        this.selectedCharacters = builder.selectedCharacters;
        this.inventory = builder.inventory;
        this.worldPosition = builder.worldPosition;
        this.enemies = new ArrayList<>();
        this.npc = new ArrayList<>();
        this.map = builder.map;
    }

    // Costruttore privato, usato dal memento
    private GameState(GameStateMemento memento) {
        this.party = memento.party;
        this.nPlayers = memento.party.getMembers().size();
        this.enemies = memento.enemies;
        this.worldPosition = memento.worldPosition;
        if (memento.mapId != null) {
            try {
                Class<?> mapClass = Class.forName(memento.mapId);
                this.map = (com.game.model.map.Map) mapClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            this.map = new Map1();
        this.autoSaveEnabled = memento.autoSaveEnabled;
        this.selectedCharacters = memento.party.getMembers().stream().map(Player::getJob).toList();
        this.inventory = null;
        this.npc = memento.npc;
    }

    public static GameState getInstance() {
        return GameState.instance;
    }

    public static void destroy() {
        instance = null;
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
        Job.initAllMoves();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < selectedCharacters.size(); i++) {
            Job selectedJob = selectedCharacters.get(i);

            Player p = new Player(selectedJob, map.getPlayerPosition(selectedCharacters.size(), i));
            if (i > 0)
                p.subscribeToFollowed(players.get(i - 1));
            players.add(p);
        }
        this.party = new Party(players);
    }

    public void createEnemy() {
        enemies.clear();
        Job.initAllMoves();
        enemies.addAll(map.getEnemies());
    }

    public void createNpc() {
        this.npc.clear();

        Job.initAllMoves();
        this.npc.addAll(map.getNpcs());
    }

    public List<HasSpriteAndPosition> getSpritesAndPositions() {
        if (sprites.size() != party.getMembers().size() + enemies.size() + npc.size()) {
            sprites.clear();
            sprites.addAll(this.party.getMembers());
            sprites.addAll(this.enemies);
            sprites.addAll(this.npc);
        }
        return sprites;
    }

    public void applyChoices(String choice) {

    }

    public com.game.model.map.Map getMap() {
        return map;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setFlag(Flag flag) {
        this.flagMap.setFlag(flag, true);
    }

    public boolean isFlagSet(Flag flag) {
        return this.flagMap.getFlag(flag);
    }

    public FlagMap getFlagMap() {
        return this.flagMap;
    }

    public StoryNode getCurrentStoryNode() {
        return this.currentStoryNode;
    }

    public void setCurrentStoryNode(StoryNode newStoryNode) {
        this.currentStoryNode = newStoryNode;
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

    public List<NPC> getNpc() {
        return npc;
    }

    public Party getParty() {
        return party;
    }

    public WorldPosition getWorldPosition() {
        return worldPosition;
    }

    public GameStateMemento saveToMemento() {
        return new GameStateMemento(this);
    }

    // ----------------------------------------------------------------------------------------

    public static class GameStateBuilder {
        public com.game.model.map.Map map;

        public int nPlayers = 2;
        public boolean autoSaveEnabled = false;
        public List<Job> selectedCharacters = new ArrayList<>();
        public List<Enemy> enemies = new ArrayList<>();
        public List<NPC> npc = new ArrayList<>();
        public Inventory inventory;
        public FlagMap flagMap = new FlagMap();
        public StoryNode storyNode;
        public WorldPosition worldPosition = new WorldPosition();

        public GameStateBuilder setMap(com.game.model.map.Map map) {
            this.map = map;
            return this;
        }

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

        public GameStateBuilder setNpc(List<NPC> npc) {
            this.npc = npc;
            return this;
        }

        public GameStateBuilder setInventory() {
            this.inventory = new Inventory();
            return this;
        }

        public GameStateBuilder setFlagMap(FlagMap flagMap) {
            this.flagMap = flagMap;
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

        public GameState restoreFromMemento(GameStateMemento memento) {
            // System.out.println(memento.toString());
            GameState.instance = new GameState(memento);
            return instance;
        }

    }
}