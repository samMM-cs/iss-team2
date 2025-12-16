package com.game.model.character;

import javafx.scene.image.*;
import com.game.model.Position;

public class Player extends Character {
  private int xp;
  private Inventory inventory;
  private Player follower;
  private static final Image img = new Image(Player.class.getResourceAsStream("/characters/rogues.png"));

  public Player(Job job, Position position) {
    super(job, position, img);
  }

  public void equipItem(Item item) {
  }

  public void learnAbility(Ability ability) {
  }

  public void notifyFollower() {
    if (this.follower != null) {
      this.follower.notifyFollower();
      this.follower.setPosition(getPos());
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

  public final void addXp(int xp) {
    this.xp += xp;
  }

  public final Inventory getInventory() {
    return this.inventory;
  }

  public final void setPosition(Position position) {
    Position pos = position;
    if (getSprite() != null) {
      getSprite().setX(pos.getX());
      getSprite().setY(pos.getY());
    }
  }

  public final Player getFollower() {
    return this.follower;
  }

  public void setFollower(Player follower) {
    this.follower = follower;
  }
}