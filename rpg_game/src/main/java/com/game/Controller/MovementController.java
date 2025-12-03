package com.game.controller;

import java.util.HashSet;
import java.util.Set;

import com.game.model.Party;
import com.game.model.Player;
import com.game.model.Position;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class MovementController {
  private Party party;
  private float speed = 50;
  private Scene scene;

  private Set<KeyCode> activeKeys = new HashSet<>();

  public MovementController(Party party, Scene scene) {
    this.scene = scene;
    this.party = party;
    setUpKeyReleased();
  }

  public void setUpKeyReleased() {
    scene.setOnKeyPressed(e -> {
      activeKeys.add(e.getCode());
    });
    scene.setOnKeyReleased(e -> {
      activeKeys.remove(e.getCode());
    });
  }

  public void update() {
    movePlayer();
  }

  void movePlayer() {
    // speed *= 1.01;
    Player mainPlayer = party.getMainPlayer();
    int dx = 0;
    int dy = 0;
    if (activeKeys.contains(KeyCode.W) || activeKeys.contains(KeyCode.UP)) {
      dy -= speed;
      activeKeys.remove(KeyCode.W);
      activeKeys.remove(KeyCode.UP);
    }
    if (activeKeys.contains(KeyCode.A) || activeKeys.contains(KeyCode.LEFT)) {
      dx -= speed;
      activeKeys.remove(KeyCode.A);
      activeKeys.remove(KeyCode.LEFT);
    }
    if (activeKeys.contains(KeyCode.S) || activeKeys.contains(KeyCode.DOWN)) {
      dy += speed;
      activeKeys.remove(KeyCode.S);
      activeKeys.remove(KeyCode.DOWN);
    }
    if (activeKeys.contains(KeyCode.D) || activeKeys.contains(KeyCode.RIGHT)) {
      dx += speed;
      activeKeys.remove(KeyCode.D);
      activeKeys.remove(KeyCode.RIGHT);
    }

    if (dx != 0 || dy != 0) {
      mainPlayer.getPosition().add(dx, dy)
          .clamp(this.scene.getWidth(), this.scene.getHeight());
      mainPlayer.notifyFollower();
    }
  }
}
