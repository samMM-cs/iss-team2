package com.game.view;

import com.game.controller.MovementController;
import com.game.model.Party;
import com.game.model.Player;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.*;

public class ExplorationView {
    private static final String MAP_FILE_PATH = "/maps/samplemap1.tmj";
    private static final String TILESET_IMAGE_PATH = "/images/punyworld-overworld-tileset.png";
    private MovementController movementController;
    private Stage stage;
    private Party party;
    private Pane root;
    private Scene scene;
    private MapView mapView;

    public ExplorationView(Stage stage) {
        this.stage = stage;
        this.mapView = new MapView(MAP_FILE_PATH, TILESET_IMAGE_PATH);

        this.root = new Pane();
        this.scene = new Scene(root, stage.getWidth(), stage.getHeight());
        this.party = new Party(Player.createTestPlayers());
        this.movementController = new MovementController(party, scene, mapView.getTileSize(), mapView);

        mapView.prefHeightProperty().bind(root.heightProperty());
        mapView.prefWidthProperty().bind(root.widthProperty());
    }

    public void showMap() {
        root.getChildren().add(mapView);
        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                movementController.update();
                // Request MapView layout each frame so the smoothed camera updates over time
                mapView.requestLayout();
                // Update sprite positions to match the map's current offset/scale
                updateSpritePositions();
            }
        };

        party.getMembers().reversed().forEach(player -> {
            player.getSprite().setPreserveRatio(false);
            root.getChildren().add(player.getSprite());
        });

        stage.setScene(scene);
        stage.show();
        timer.start();
    }

    private void updateSpritePositions() {
        double offsetX = mapView.getOffsetX();
        double offsetY = mapView.getOffsetY();
        double scale = mapView.getMapScale();
        int tileSize = mapView.getTileSize();

        final double renderedTile = tileSize * scale;
        for (Player player : party.getMembers()) {
            double mapPixelX = player.getPosition().getX() * tileSize;
            double mapPixelY = player.getPosition().getY() * tileSize;
            double screenX = offsetX + mapPixelX * scale;
            double screenY = offsetY + mapPixelY * scale;

            player.getSprite().setFitWidth(renderedTile);
            player.getSprite().setFitHeight(renderedTile);
            player.getSprite().setX(screenX);
            player.getSprite().setY(screenY);
        }
    }
}