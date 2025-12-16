package com.game.model.battle;

import java.util.List;

import com.game.model.character.Enemy;
import com.game.model.character.Player;

public class Battle{
    private List<Player> party;
    private List<Enemy> enemies;
    private int turnIndex;

    public Battle(List<Player> party, List<Enemy> enemies, int turnIndex) {
        this.party = party;
        this.enemies = enemies;
        this.turnIndex = turnIndex;
    }

    public final List<Enemy> getEnemies() {
        return this.enemies;
    }

    public final List<Player> getParty() {
        return this.party;
    }

    public final int getTurnIndex() {
        return this.turnIndex;
    }
}