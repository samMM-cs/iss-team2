package com.game.view.mapview;

import java.util.ArrayList;
import java.util.List;

import com.game.controller.GameController;
import com.game.controller.MovementController;
import com.game.model.character.Party;
import com.game.model.character.Enemy;
import com.game.model.GameState;

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
    private List<Enemy> enemies = new ArrayList<>();
    private Pane root;
    private Scene scene;
    private MapView mapView;

    public ExplorationView(Stage stage, GameController gameController, GameState gameState) {
        this.stage = stage;
        if (stage == null)
            return;

        // Crea la MapView
        this.mapView = new MapView(MAP_FILE_PATH, TILESET_IMAGE_PATH);

        this.root = new Pane();
        this.scene = new Scene(root, stage.getWidth(), stage.getHeight());
        this.party = new Party(gameState.createParty());
        this.enemies = gameState.createEnemy();
        
        this.movementController = new MovementController(party, scene, mapView);

        mapView.prefHeightProperty().bind(root.heightProperty());
        mapView.prefWidthProperty().bind(root.widthProperty());
        root.getChildren().add(mapView);
    }

    public void showMap() {
        this.party.getMembers().forEach(player -> root.getChildren().add(player.getSprite()));
        this.enemies.forEach(enemy -> root.getChildren().add(enemy.getSprite()));
        
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                movementController.update();
                mapView.requestLayout();
            }
        };

        timer.start();
    }
}
