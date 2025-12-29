package com.game.view.mapview;

import java.util.ArrayList;
import java.util.List;

import com.game.controller.ViewManager;
import com.game.controller.exploration.ExplorationController;
import com.game.controller.exploration.MapBuilder;
import com.game.model.character.Party;
import com.game.model.character.Player;
import com.game.model.character.NPC;
import com.game.model.map.TiledMapData;
import com.game.view.DialogueView;
import com.game.view.ShopView;
import com.game.model.character.Enemy;
import com.game.model.GameState;
import com.game.view.HUD;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ExplorationView {
    private static final String MAP_FILE_PATH = "/maps/samplemap1.tmj";
    private static final String TILESET_IMAGE_PATH = "/images/punyworld-overworld-tileset.png";
    private static final String TILESET_DATA_PATH = "/maps/punyworld-overworld-tiles.tsx";
    private ExplorationController movementController;
    private Party party;
    private List<Enemy> enemies = new ArrayList<>();
    private List<NPC> npc = new ArrayList<>();
    private Pane root;
    private Scene scene;
    private MapView mapView;
    private DialogueView dialogueView;
    private ShopView shopView;
    private HUD hud;

    public ExplorationView() {
        TiledMapData mapData = MapBuilder.loadRawMapData(MAP_FILE_PATH);
        MapBuilder builder = new MapBuilder()
                .addLayer(mapData.getLayers().get(0))
                .addLayer(mapData.getLayers().get(1))
                .addLayer(mapData.getLayers().get(2))
                .setDimensions(mapData.getTileheight(), mapData.getWidth(), mapData.getHeight())
                .setWalkableIds(TILESET_DATA_PATH)
                .setTileSetImageFromPath(TILESET_IMAGE_PATH);

        this.root = new Pane();
        this.scene = new Scene(root, ViewManager.getInstance().getWidth(),
                ViewManager.getInstance().getHeight());
        GameState gameState = GameState.getInstance();
        this.party = gameState.getParty();
        this.enemies = gameState.getEnemies();
        this.npc = gameState.getNpc();
        this.hud = new HUD(party.getMainPlayer());
        hud.setVisible(false);
        this.dialogueView = new DialogueView();
        dialogueView.setVisible(false);
        this.shopView = new ShopView(party.getMainPlayer());
        shopView.setVisible(false);
        for (Player player : this.party.getMembers()) {
            builder = builder.addSprite(player.getSprite(), player.getPos());
        }
        for (Enemy enemy : this.enemies) {
            builder = builder.addSprite(enemy.getSprite(), enemy.getPos());
        }
        for (NPC n : npc) {
            builder = builder.addSprite(n.getSprite(), n.getPos());
        }
        this.mapView = builder.showSprites()
                .addLayer(mapData.getLayers().get(3))
                .build();
        this.movementController = new ExplorationController(party, scene, mapView, enemies, npc, dialogueView,
                shopView);

        this.mapView.updatePlayerPosition(this.party.getMainPlayer().getPos().scale(mapView.getTileSize()));
        mapView.prefHeightProperty().bind(root.heightProperty());
        mapView.prefWidthProperty().bind(root.widthProperty());
        root.getChildren().add(mapView);
        root.getChildren().add(hud);
        root.getChildren().add(dialogueView);
        root.getChildren().add(shopView);
    }

    public void showMap() {

        ViewManager.getInstance().setAndShowScene(scene);

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                this.stop();
                movementController.update();
                this.start();
                mapView.requestLayout();

                for (Player player : party.getMembers()) {
                    if (player.isInCombat()) {
                        hud.setVisible(true);
                        hud.update();
                    } else
                        hud.setVisible(false);

                }

            }
        };

        timer.start();
    }
}