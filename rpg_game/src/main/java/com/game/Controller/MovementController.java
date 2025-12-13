package com.game.controller;

import java.util.LinkedList;
import java.util.Queue;

import com.game.model.Party;
import com.game.model.Player;
import com.game.model.Position;
import com.game.view.MapView;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class MovementController {
  private Party party;
  private Scene scene;
  private KeyCode prevDirection;
  private Queue<KeyCode> activeKeys = new LinkedList<>();
  private Position posLimit;
  private MapView mapView;

  public MovementController(Party party, Scene scene, float speed, MapView mapView) {
    this.scene = scene;
    this.party = party;
    this.mapView = mapView;
    scene.setOnKeyPressed(e -> {
      activeKeys.offer(e.getCode());
    });

    // Listener to update limits when window gets resized
    scene.widthProperty().addListener((obs, oldVal, newVal) -> updatePositionLimit());
    scene.heightProperty().addListener((obs, oldVal, newVal) -> updatePositionLimit());

    updatePositionLimit();
  }

  private void updatePositionLimit() {
    // Bound positions by map dimensions (in tiles). If mapView is not ready,
    // fall back to default based on scene size.
    if (mapView != null && mapView.getTileSize() > 0) {
      int tileSize = mapView.getTileSize();
      int maxTilesX = (int) (mapView.getMapWidth() / tileSize);
      int maxTilesY = (int) (mapView.getMapHeight() / tileSize);
      // max index is tiles-1, ensure at least 0
      int maxX = Math.max(0, maxTilesX - 1);
      int maxY = Math.max(0, maxTilesY - 1);
      this.posLimit = new Position(maxX, maxY);
    } else {
      // fallback: prevent negative or extremely large limits
      int fallbackX = Math.max(0, (int) this.scene.getWidth() - 2);
      int fallbackY = Math.max(0, (int) this.scene.getHeight() - 4);
      this.posLimit = new Position(fallbackX, fallbackY);
    }
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
        dy -= 1;
      if (key == KeyCode.A || key == KeyCode.LEFT)
        dx -= 1;
      if (key == KeyCode.S || key == KeyCode.DOWN)
        dy += 1;
      if (key == KeyCode.D || key == KeyCode.RIGHT)
        dx += 1;
    }

    if (dx != 0 || dy != 0) {
      Position nextPosition = mainPlayer.getPosition().add(dx, dy);
      if (nextPosition.isInside(posLimit)) {
        party.updateFollowPosition(nextPosition);

        // Aggiorna la posizione della camera nel MapView (converti da tile a pixel)
        double playerPixelX = nextPosition.getX() * mapView.getTileSize();
        double playerPixelY = nextPosition.getY() * mapView.getTileSize();
        mapView.updatePlayerPosition(playerPixelX, playerPixelY);
      }
    }
  }
}
