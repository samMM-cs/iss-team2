package com.game.model;

public class Ability {
  private String name;
  private String desctription;
  private Effect effect;

  public final String getName() {
    return this.name;
  }

  public final String getDesctription() {
    return this.desctription;
  }

  public final Effect getEffect() {
    return this.effect;
  }
}
