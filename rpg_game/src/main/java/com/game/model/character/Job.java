package com.game.model.character;

public enum Job {

    WARRIOR(0, 0,40,4,6),
    ROGUE(4,1,40,4,6),
    MAGE(1, 0,60,6,4),
    ARCHER(2, 0,40,40,4);

    public static final int SIZE = 32;

    private final int col, row, hp, attack, speed;

    Job(int col, int row,int hp,int attack,int speed) {
        this.col = col;
        this.row = row;
        this.hp = hp;
        this.attack = attack;
        this.speed = speed;
    }

    public int getX() {
        return this.col * SIZE;
    }

    public int getY() {
        return this.row * SIZE;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getHp() {
        return this.hp;
    }

    public int getSpeed() {
        return this.speed;
    }
}
