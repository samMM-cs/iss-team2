package com.game.model;

public class Player extends Character {
  private int xp;
  private Job job;
  private Inventory inventory;
  private Position position;
  private Player follower;

  public Player(Job job, Position position) {
    this.job = job;
    this.position = position;
  }

  public void equipItem(Item item) {
  }

  public void learnAbility(Ability ability) {
  }

  public void notifyFollower() {
    if (this.follower != null) {
      this.follower.setPosition(this.position);
      this.follower.notifyFollower();
    }
  }

  public void subscribeToFollowed(Player player) {
    player.setFollower(this);
  }

  public void unsubscribeFromFollowed(Player player) {
    player.setFollower(null);
  }

  public final int getXp() {
    return this.xp;
  }

  public final Job getJob() {
    return this.job;
  }

  public final Inventory getInventory() {
    return this.inventory;
  }

  public final Position getPosition() {
    return this.position;
  }

  public final void setPosition(Position position) {
    this.position = position;
  }

  public final Player getFollower() {
    return this.follower;
  }

  public void setFollower(Player follower) {
    this.follower = follower;
  }
}