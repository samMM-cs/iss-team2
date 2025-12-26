package com.game.model.character;

public class Stats {
  int xp;
  int level;
  int maxXp;
  int hp;
  int attack;
  int defense;
  int special;
  int speed;

  public Stats(int hp, int attack, int defense, int special, int speed) {
    this.hp = hp;
    this.attack = attack;
    this.defense = defense;
    this.special = special;
    this.speed = speed;

    this.level = 1;
    this.xp = 0;
    this.maxXp = 100;
  }

  public Stats(Stats newStats) {
    this.hp = newStats.getHp();
    this.attack = newStats.getAttack();
    this.defense = newStats.getDefense();
    this.special = newStats.getSpecial();
    this.speed = newStats.getSpeed();

    this.xp = newStats.xp;
    this.level = newStats.level;
    this.maxXp = newStats.maxXp;
  }

  public void addExp(int amount) {
    this.xp += amount;

    while (xp >= maxXp)
      levelUp();
  }

  public void levelUp() {
    xp -= maxXp;
    level++;
    maxXp = (int) (maxXp * 1.5);
  }

  public int getHp() {
    return this.hp;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public int getAttack() {
    return this.attack;
  }

  public void setAttack(int attack) {
    this.attack = attack;
  }

  public int getDefense() {
    return this.defense;
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }

  public int getSpecial() {
    return this.special;
  }

  public void setSpecial(int special) {
    this.special = special;
  }

  public int getSpeed() {
    return this.speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getXp() {
    return xp;
  }

  public void setXp(int xp) {
    this.xp = xp;
  }

  public double getXpPerc() {
    return (double) xp / maxXp;
  }

  public int getMaxXp() {
      return maxXp;
  }
  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public Stats copy() {
    return new Stats(this);
  }
}