package com.game.controller;

import java.util.LinkedList;
import java.util.Queue;

import com.game.model.Party;
import com.game.model.Player;
import com.game.model.Position;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class MovementController {
  private Party party;
  private float speed = 50;
  private Scene scene;
  private KeyCode prevDirection;
  private Queue<KeyCode> activeKeys = new LinkedList<>();
  private final Position posLimit;

  public MovementController(Party party, Scene scene) {
    this.scene = scene;
    this.party = party;
    scene.setOnKeyPressed(e -> {
      activeKeys.offer(e.getCode());
    });
    this.posLimit = new Position(
        (int) (this.scene.getWidth() - 100) / 50 * 50,
        (int) (this.scene.getHeight() - 100) / 50 * 50);
    System.out.println(this.scene.getWidth() + " " + this.scene.getHeight());
    System.out.println(posLimit);
  }

  public void update() {
    movePlayer();
  }

  private static boolean isOpposite(KeyCode key, KeyCode prev) {
    return switch (key) {
      case KeyCode.W, KeyCode.UP -> prev == KeyCode.S || prev == KeyCode.DOWN;
      case KeyCode.A, KeyCode.LEFT -> prev == KeyCode.D || prev == KeyCode.RIGHT;
      case KeyCode.S, KeyCode.DOWN -> prev == KeyCode.W || prev == KeyCode.UP;
      case KeyCode.D, KeyCode.RIGHT -> prev == KeyCode.A || prev == KeyCode.LEFT;
      default -> false;
    };
  }

  void movePlayer() {
    Player mainPlayer = party.getMainPlayer();
    int dx = 0;
    int dy = 0;
    KeyCode key = activeKeys.poll();

    if (key != null && !isOpposite(key, prevDirection)) {
      prevDirection = key;
      if (key == KeyCode.W || key == KeyCode.UP)
        dy -= speed;
      if (key == KeyCode.A || key == KeyCode.LEFT)
        dx -= speed;
      if (key == KeyCode.S || key == KeyCode.DOWN)
        dy += speed;
      if (key == KeyCode.D || key == KeyCode.RIGHT)
        dx += speed;
    }

    if (dx != 0 || dy != 0) {
      Position nextPosition = mainPlayer.getPosition().add(dx, dy);
      if (nextPosition.isInside(posLimit)) {
        mainPlayer.notifyFollower();
        mainPlayer.setPosition(nextPosition);
      }
    }
  }
}
