package com.game.model;

public class Player extends Character {
  private int xp;
  private Job job;
  private Inventory inventory;
  private Position position;
  private Player follower;

  public void equipItem(Item item) {
  }

  public void learnAbility(Ability ability) {
  }

  public void notifyFollower() {
  }

  public void subscribeToFollowed(Player player) {
  }

  public void unsubscribeFromFollowed(Player player) {
  }
}
