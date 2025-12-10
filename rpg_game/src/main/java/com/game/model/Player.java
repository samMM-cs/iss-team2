package com.game.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

public class Player extends Character {
  private int xp;
  private Job job;
  private Inventory inventory;
  private Position position;
  private Player follower;
  private ImageView sprite;
  private final int SPRITE_SIZE = 32;
  private static final Image SPRITESHEET = new Image(Player.class.getResourceAsStream("/characters/rogues.png"));

  public Player(Job job, Position position) {
    this.job = job;
    this.position = position;
  }

  public Player(Job job, Position position, int col, int row) {
    this.job = job;
    this.position = position;
    this.sprite = new ImageView(SPRITESHEET);
    this.sprite
        .setViewport(new javafx.geometry.Rectangle2D(col * SPRITE_SIZE, row * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE));
    this.sprite.setX(position.getX());
    this.sprite.setY(position.getY());
  }

  public void equipItem(Item item) {
  }

  public void learnAbility(Ability ability) {
  }

  public void notifyFollower() {
    if (this.follower != null) {
      this.follower.notifyFollower();
      this.follower.setPosition(this.position);
    }
  }

  public void subscribeToFollowed(Player player) {
    player.setFollower(this);
  }

  public void unsubscribeFromFollowed(Player player) {
    player.setFollower(null);
  }

  public static List<Player> createTestPlayers(int posOff) {
    Player mainPlayer = new Player(Job.WARRIOR, new Position(3 * posOff, 0), 0, 2);
    Player player2 = new Player(Job.MAGE, new Position(2 * posOff, 0), 1, 2);
    player2.subscribeToFollowed(mainPlayer);
    Player player3 = new Player(Job.ROGUE, new Position(posOff, 0), 2, 2);
    player3.subscribeToFollowed(player2);
    Player player4 = new Player(Job.CLERIC, new Position(0, 0), 3, 2);
    player4.subscribeToFollowed(player3);
    return List.of(mainPlayer, player2, player3, player4);
  }

  public final int getXp() {
    return this.xp;
  }

  public final void addXp(int xp) {
    this.xp+= xp;
    return;
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
    this.sprite.setX(position.getX());
    this.sprite.setY(position.getY());
  }

  public final Player getFollower() {
    return this.follower;
  }

  public void setFollower(Player follower) {
    this.follower = follower;
  }

  public ImageView getSprite() {
    return sprite;
  }
}