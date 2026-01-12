package com.game.model.character;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.model.battle.Move;

public class Stats {
  private int xp;
  private int level;
  private int maxXp;
  private int hp;
  private int maxHp;
  private int attack;
  private int defense;
  private int special;
  private int speed;
  private List<Move> moves;
  private int money;
  private int atkCount;
  private int healCount;
  private float dmgMult;
  private float healMult;

  @JsonCreator
  public Stats(
      @JsonProperty("xp") int xp,
      @JsonProperty("level") int level,
      @JsonProperty("maxXp") int maxXp,
      @JsonProperty("hp") int hp,
      @JsonProperty("maxHp") int maxHp,
      @JsonProperty("attack") int attack,
      @JsonProperty("defense") int defense,
      @JsonProperty("special") int special,
      @JsonProperty("speed") int speed,
      @JsonProperty("moves") List<Move> moves,
      @JsonProperty("money") int money,
      @JsonProperty("dmgCount") int dmgCount,
      @JsonProperty("healCount") int healCount,
      @JsonProperty("dmgMult") float dmgMult,
      @JsonProperty("healMult") float healMult) {
    // jackson constructor
    this.xp = xp;
    this.level = level;
    this.maxXp = maxXp;
    this.hp = hp;
    this.maxHp = maxHp;
    this.attack = attack;
    this.defense = defense;
    this.special = special;
    this.speed = speed;
    this.moves = moves;
    this.money = money;
  }

  public Stats(int hp, int attack, int defense, int special, int speed) {
    this.hp = hp;
    this.maxHp = 100;
    this.attack = attack;
    this.defense = defense;
    this.special = special;
    this.speed = speed;

    this.level = 1;
    this.xp = 0;
    this.maxXp = 100;
    this.moves = new ArrayList<>();
    this.money = 1;
    this.atkCount = 0;
    this.healCount = 0;
    this.dmgMult = 1;
    this.healMult = 1;
  }

  public Stats(Stats newStats) {
    this.hp = newStats.getHp();
    this.maxHp = newStats.getMaxHp();
    this.attack = newStats.getAttack();
    this.defense = newStats.getDefense();
    this.special = newStats.getSpecial();
    this.speed = newStats.getSpeed();

    this.xp = newStats.getXp();
    this.level = newStats.getLevel();
    this.maxXp = newStats.getMaxXp();

    this.moves = new ArrayList<>(newStats.getMoves());
    this.money = newStats.getMoney();

    this.atkCount = newStats.getAtkCount();
    this.healCount = newStats.getHealCount();
    this.dmgMult = newStats.getDmgMult();
    this.healMult = newStats.getHealMult();
  }

  public void addMove(Move move) {
    if (!moves.contains(move))
      moves.add(move);
  }

  public List<Move> getMoves() {
    return moves;
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
    if (atkCount >= 10 && healCount <= 5) {
      atkCount -= 10;
      dmgMult *= 1.5;
    } else if (healCount >= 10 && atkCount <= 5) {
      healCount -= 10;
      healMult *= 1.5;
    } else if (atkCount >= 5 && healCount >= 5) {
      atkCount -= 5;
      healCount -= 5;
      dmgMult *= 1.25;
      healMult *= 1.25;
    }
  }

  @JsonIgnore
  public double getHpPerc() {
    return (double) this.hp / (double) this.maxHp;
  }

  public int getHp() {
    return this.hp;
  }

  public int getMaxHp() {
    return maxHp;
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

  @JsonIgnore
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

  public void addMoney(int amount) {
    this.money += amount;
  }

  public void removeMoney(int amount) {
    this.money -= amount;
  }

  public int getMoney() {
    return money;
  }

  public Stats copy() {
    return new Stats(this);
  }

  @Override
  public String toString() {
    return "Stats{" +
        "hp=" + hp +
        ", maxHp=" + maxHp +
        ", attack=" + attack +
        ", defense=" + defense +
        ", special=" + special +
        ", speed=" + speed +
        ", level=" + level +
        ", xp=" + xp +
        ", maxXp=" + maxXp +
        ", moves=" + moves.size() +
        ", money=" + money +
        '}';
  }

  public int getAtkCount() {
    return atkCount;
  }

  public void incAtkCount() {
    this.atkCount++;
  }

  public int getHealCount() {
    return healCount;
  }

  public void incHealCount() {
    this.healCount++;
  }

  public float getDmgMult() {
    return dmgMult;
  }

  public float getHealMult() {
    return healMult;
  }
}