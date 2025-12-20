package com.game.view.mapview;

import java.util.ArrayList;
import java.util.List;

import com.game.controller.GameController;
import com.game.controller.exploration.ExplorationController;
import com.game.controller.exploration.MapBuilder;
import com.game.model.character.Party;
import com.game.model.character.Player;
import com.game.model.map.TiledMapData;
import com.game.model.character.Enemy;
import com.game.model.GameState;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.*;

public class ExplorationView {
    private static final String MAP_FILE_PATH = "/maps/samplemap1.tmj";
    private static final String TILESET_IMAGE_PATH = "/images/punyworld-overworld-tileset.png";
    private static final String TILESET_DATA_PATH = "/maps/punyworld-overworld-tiles.tsx";
    private ExplorationController movementController;
    private Stage stage;
    private Party party;
    private List<Enemy> enemies = new ArrayList<>();
    private Pane root;
    private Scene scene;
    private MapView2 mapView;

    public ExplorationView(Stage stage, GameController gameController, GameState gameState) {
        this.stage = stage;
        if (stage == null)
            return;
        TiledMapData mapData = MapBuilder.loadRawMapData(MAP_FILE_PATH);
        // this.mapView = new MapView(MAP_FILE_PATH, TILESET_IMAGE_PATH);
        MapBuilder builder = new MapBuilder()
                .addLayer(mapData.getLayers().get(0))
                .addLayer(mapData.getLayers().get(1))
                .setDimensions(mapData.getTileheight(), mapData.getWidth(), mapData.getHeight())
                .setWalkableIds(TILESET_DATA_PATH)
                .setTileSetImageFromPath(TILESET_IMAGE_PATH);

        this.root = new Pane();
        this.scene = new Scene(root, stage.getWidth(), stage.getHeight());
        this.party = gameState.getParty();
        this.enemies = gameState.getEnemies();
        for (Player player : this.party.getMembers()) {
            builder = builder.addSprite(player.getSprite(), player.getPos());
        }
        for (Enemy enemy : this.enemies) {
            builder = builder.addSprite(enemy.getSprite(), enemy.getPos());
        }
        this.mapView = builder.showSprites()
                .addLayer(mapData.getLayers().get(2))
                .build();
        this.movementController = new ExplorationController(party, scene, mapView, enemies);

        mapView.prefHeightProperty().bind(root.heightProperty());
        mapView.prefWidthProperty().bind(root.widthProperty());
        root.getChildren().add(mapView);
    }

    public void showMap() {
        // this.party.getMembers().forEach(player ->
        // mapView.addSprite(player.getSprite()));
        // this.enemies.forEach(enemy -> mapView.addSprite(enemy.getSprite()));

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