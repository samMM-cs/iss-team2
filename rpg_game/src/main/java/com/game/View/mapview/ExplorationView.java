package com.game.view.mapview;

import com.game.controller.exploration.ExplorationController;
import com.game.controller.GameController;
import com.game.controller.ViewManager;
import com.game.controller.exploration.MapBuilder;
import com.game.model.character.Player;
import com.game.model.map.MapData;
import com.game.model.map.TiledMapData;
import com.game.model.GameState;
import com.game.view.HUD;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ExplorationView {
    private static final String TILESET_IMAGE_PATH = "/images/punyworld-overworld-tileset.png";
    private static final String TILESET_DATA_PATH = "/maps/punyworld-overworld-tiles.tsx";
    private ExplorationController movementController;
    private boolean isRunning = false;
    private Pane root;
    private Scene scene;
    private MapView mapView;
    private HUD hud;
    private AnimationTimer timer;
    private GameController gameController;

    public ExplorationView(MapData map, GameController gameController) {
        TiledMapData mapData = MapBuilder.loadRawMapData(map.getFilePath());

        this.mapView = new MapBuilder()
                .setDimensions(mapData.getTileheight(), mapData.getWidth(), mapData.getHeight())
                .setWalkableIds(TILESET_DATA_PATH)
                .setTileSetImageFromPath(TILESET_IMAGE_PATH)
                .addLayers(mapData.getLayers())
                .showSprites(map.getSpriteindex())
                .build();

        this.root = new Pane();
        this.scene = new Scene(root, ViewManager.getInstance().getWidth(),
                ViewManager.getInstance().getHeight());
        this.hud = new HUD(GameState.getInstance().getParty().getMainPlayer());
        hud.setVisible(false);

        this.movementController = new ExplorationController(scene, mapView);
        this.gameController = gameController;

        this.mapView.updatePlayerPosition(
                GameState.getInstance().getParty().getMainPlayer()
                        .getPosition().scale(mapView.getTileSize()));
        mapView.prefHeightProperty().bind(root.heightProperty());
        mapView.prefWidthProperty().bind(root.widthProperty());
        root.getChildren().add(mapView);
        root.getChildren().add(hud);

        timer = new AnimationTimer() {

            @Override
            public void start() {
                if (isRunning)
                    System.out.println("Already running");
                else {
                    System.out.println("Starting timer");
                    isRunning = true;
                }
                super.start();
            }

            @Override
            public void stop() {
                if (isRunning) {
                    System.out.println("Stopping timer");
                    isRunning = false;
                } else {
                    System.out.println("Already stopped");
                }
                super.stop();
            }

            @Override
            public void handle(long now) {
                movementController.update();
                mapView.requestLayout();

                for (Player player : GameState.getInstance().getParty().getMembers()) {
                    if (player.isInCombat()) {
                        hud.setVisible(true);
                        hud.update(player);
                    } else
                        hud.setVisible(false);
                }
            }
        };
    }

    public void showMap() {
        ViewManager.getInstance().setAndShowScene(scene);
        ViewManager.getInstance().initPauseMenu(scene, gameController);
        ViewManager.getInstance().enableGlobalPause(scene);

        /*if (!GameState.getInstance().getStoryShown())
            ViewManager.getInstance().showStory();*/

        

        timer.start();
    }

    public void stop() {
        if (this.timer != null && isRunning) {
            this.timer.stop();
        }
    }

    public void start() {
        if (this.timer != null && !isRunning) {
            this.timer.start();
        }
    }

    public void destroy() {
        mapView = null;
    }
}