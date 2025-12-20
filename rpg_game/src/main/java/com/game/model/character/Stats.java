package com.game.model.character;

public record Stats(int hp, int attack, int defense, int special, int speed) {
  public Stats setHp(int hp) {
    return new Stats(hp, attack, defense, special, speed);
  }
}