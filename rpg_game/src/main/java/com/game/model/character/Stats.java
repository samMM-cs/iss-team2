package com.game.model.character;

public class Stats {
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
  }

  public Stats(Stats newStats) {
    this.hp= newStats.getHp();
    this.attack= newStats.getAttack();
    this.defense= newStats.getDefense();
    this.special= newStats.getSpecial();
    this.speed= newStats.getSpeed();
  }

  public int getHp() {
    return this.hp;
  }

  public void setHp(int hp) {
    this.hp= hp;
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

  public Stats copy() {
    return new Stats(this);
  }
}