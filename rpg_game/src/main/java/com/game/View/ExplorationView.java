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
        // Crea la MapView
        this.mapView = new MapView(MAP_FILE_PATH, TILESET_IMAGE_PATH);

        this.root = new Pane();
        this.scene = new Scene(root, stage.getWidth(), stage.getHeight());
        this.party = new Party(Player.createTestPlayers());
        this.movementController = new MovementController(party, scene);

        mapView.prefHeightProperty().bind(root.heightProperty());
        mapView.prefWidthProperty().bind(root.widthProperty());
    }

    public void showMap() {
        root.getChildren().add(mapView);

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long p) {
                movementController.update();

                /*List<Position> oldPositions=party.getMembers().stream().map(Player::getPosition).toList();
                for (int i = 1; i <party.getMembers().size(); i++) {
                    party.getMembers().get(i).setPosition(oldPositions.get(i-1));
                }*/
               party.updateFollowPosition();
            }
        };

        party.getMembers().forEach(player -> root.getChildren().add(player.getSprite()));
        stage.setScene(scene);
        stage.show();
        timer.start();
    }
}