package com.game.model.character;

public class Item {
  private String name;
  private ItemType type;
  private ItemStats bonus;

  public ItemStats getBonus() {
    return bonus;
  }

  public String getName() {
    return name;
  }

  public ItemType getType() {
    return type;
  }
}
