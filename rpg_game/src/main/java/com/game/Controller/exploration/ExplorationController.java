package com.game.controller.exploration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.game.model.Position;
import com.game.model.character.Party;
import com.game.model.character.Player;
import com.game.model.character.Enemy;
import com.game.model.character.NPC;
import com.game.view.mapview.MapView2;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class ExplorationController {
    private Party party;
    private Scene scene;
    private List<Enemy> enemies = new ArrayList<>();
    private List<NPC> npc = new ArrayList<>();

    private Queue<KeyCode> activeKeys = new LinkedList<>();
    private Position posLimit;
    private MapView2 mapView;

    public ExplorationController(Party party, Scene scene, MapView2 mapView, List<Enemy> enemies,List<NPC> npc) {
        this.scene = scene;
        this.party = party;
        this.mapView = mapView;
        this.enemies = enemies;
        this.npc = npc;
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
        updateSpritePositions();
    }

    private void updateSpritePositions() {
        int tileSize = mapView.getTileSize();

        for (Player player : party.getMembers()) {
            if (player.getSprite() != null) {
                player.getSprite().setFitWidth(tileSize);
                player.getSprite().setFitHeight(tileSize);
                player.getSprite().setX(mapView.getOffset().x() + player.getPos().x() * tileSize);
                player.getSprite().setY(mapView.getOffset().y() + player.getPos().y() * tileSize);
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy.getSprite() != null) {
                enemy.getSprite().setFitWidth(tileSize);
                enemy.getSprite().setFitHeight(tileSize);
                enemy.getSprite().setX(mapView.getOffset().x() + enemy.getPos().x() * tileSize);
                enemy.getSprite().setY(mapView.getOffset().y() + enemy.getPos().y() * tileSize);
            }
        }
        for (NPC n : npc) {
            if (n.getSprite() != null) {
                n.getSprite().setFitWidth(tileSize);
                n.getSprite().setFitHeight(tileSize);
                n.getSprite().setX(mapView.getOffset().x() + n.getPos().x() * tileSize);
                n.getSprite().setY(mapView.getOffset().y() + n.getPos().y() * tileSize);
            }
        }
    }

    void movePlayer() {
        Player mainPlayer = party.getMainPlayer();
        int dx = 0;
        int dy = 0;
        KeyCode key = activeKeys.poll();

        if (key != null) {
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
            Position nextPosition = mainPlayer.getPos().add(dx, dy);
            if (canGoThere(nextPosition)) {
                party.updateFollowPosition(nextPosition);
                mapView.updatePlayerPosition(nextPosition.scale(mapView.getTileSize()));
            }
        }
    }

    public boolean canGoThere(Position nextPosition) {
        return nextPosition.isInside(posLimit)
                && this.mapView.getWalkableTiles()[(int) nextPosition.x()][(int) nextPosition.y()];
    }
}
