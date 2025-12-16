package com.game.model.character;

public enum Job {

    WARRIOR(0, 0,40,4,6,true),
    ROGUE(4,1,40,4,6,true),
    MAGE(1, 0,60,6,4,true),
    ARCHER(2, 0, 40, 40, 4,true),
    //Enemy
    GOBLIN(0, 0, 30, 3, 4,false),
    SPIDER(6,8,20,4,2,false);

    public static final int SIZE = 32;

    private final int col, row, hp, attack, speed;
    private boolean isPlayable;

    Job(int col, int row,int hp,int attack,int speed,boolean isPlayable) {
        this.col = col;
        this.row = row;
        this.hp = hp;
        this.attack = attack;
        this.speed = speed;
        this.isPlayable = isPlayable;
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

    public boolean getIsPlayable() {
        return this.isPlayable;
    }
}