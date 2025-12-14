package com.game.model.character;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.*;

import java.util.ArrayList;
import java.util.List;
import com.game.model.Position;
import com.game.model.GameState;

public class Player extends Character {
  private int xp;
  private Job job;
  private Inventory inventory;
  private Position position;
  private Player follower;
  private ImageView sprite;
  private static final Image img = new Image(Player.class.getResourceAsStream("/characters/rogues.png"));

  public Player(Job job, Position position) {
    this.job = job;
    this.position = position;
    this.sprite = createCharacterSprite(img, job);
  }

  private ImageView createCharacterSprite(Image img, Job job) {
    ImageView newSprite = new ImageView(img);

    Rectangle2D viewPort = new Rectangle2D(job.getX(), job.getY(), Job.SIZE, Job.SIZE);

    newSprite.setViewport(viewPort);
    return newSprite;
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

  public static List<Player> createTestPlayers(GameState gameState) {
    List<Player> playerJob = new ArrayList<>();
    for (int i = 0; i < gameState.getNPlayers(); i++) {
      Player p = new Player((Job) gameState.getSelectedCharacters().get(i), new Position(i * Job.SIZE, 0));
      playerJob.add(p);
    }
    return playerJob;
  }

  public final int getXp() {
    return this.xp;
  }

  public final void addXp(int xp) {
    this.xp += xp;
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
    if (this.sprite != null) {
      this.sprite.setX(position.getX());
      this.sprite.setY(position.getY());
    }
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

  public void setSprite(ImageView sprite) {
    this.sprite = sprite;
  }

  @Override
  public String toString() {
    return job.toString();
  }
}