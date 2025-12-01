package com.game.model;

import java.util.List;

public class Inventory {
  private List<Item> items;

  public void addItem(Item item) {
    items.add(item);
  }

  public void removeItem(Item item) {
    items.remove(item);
  }

  public void getEquipable() {
  }
}
