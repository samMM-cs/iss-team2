package com.game.model.character;

public enum Job {

    WARRIOR(0, 0, 40, 4, 3, 0, 6, true),
    ROGUE(4, 1, 40, 4, 3, 0, 6, true),
    MAGE(1, 0, 60, 6, 3, 0, 4, true),
    ARCHER(2, 0, 40, 4, 3, 0, 4, true),
    // Enemy
    GOBLIN(0, 0, 30, 3, 3, 0, 4, false),
    GOBLIN2(0, 1, 20, 4, 3, 0, 2, false);

    public static final int SIZE = 32;

    private final int col, row;
    private final Stats baseStats;

    private boolean isPlayable;

    Job(int col, int row, int hp, int attack, int defense, int special, int speed, boolean isPlayable) {
        this.col = col;
        this.row = row;
        this.baseStats = new Stats(hp, attack, defense, special, speed);
        this.isPlayable = isPlayable;
    }

    public int getX() {
        return this.col * SIZE;
    }

    public int getY() {
        return this.row * SIZE;
    }

    public boolean getIsPlayable() {
        return this.isPlayable;
    }

    public Stats getBaseStats() {
        return baseStats;
    }
}