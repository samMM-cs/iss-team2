package com.game.model;

import java.util.List;

public class Character {
  private Stats stats;
  private String name;
  List<Ability> abilities;

  public final Stats getStats() {
    return this.stats;
  }
  
  public final String getName() {
    return this.name;
  }
  
  public final List<Ability> getAbilities() {
      return this.abilities;
  }
}
