package com.game.view;

import java.util.List;

import com.game.controller.MovementController;
import com.game.model.Job;
import com.game.model.Party;
import com.game.model.Player;
import com.game.model.Position;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;

public class ExplorationView {
    private static final String MAP_FILE_PATH = "/maps/samplemap1.tmj";
    private static final String TILESET_IMAGE_PATH = "/images/punyworld-overworld-tileset.png";
    private MovementController movementController;
    private Stage stage;
    private List<Rectangle> playerRectangles;
    private Party party;
    private Pane root;
    private Scene scene;
    private MapView mapView;

    private static Party debug__createParty() {
        Player mainPlayer = new Player(new Job(), new Position(150, 0));
        Player player2 = new Player(new Job(), new Position(100, 0));
        player2.subscribeToFollowed(mainPlayer);
        Player player3 = new Player(new Job(), new Position(50, 0));
        player3.subscribeToFollowed(player2);
        Player player4 = new Player(new Job(), new Position(0, 0));
        player4.subscribeToFollowed(player3);
        return new Party(mainPlayer, List.of(player2, player3, player4));
    }

    private static List<Rectangle> degub__createRectangles() {
        Rectangle rect0 = new Rectangle(50, 50, Color.BLUE);
        Rectangle rect1 = new Rectangle(50, 50, Color.RED);
        Rectangle rect2 = new Rectangle(50, 50, Color.GREEN);
        Rectangle rect3 = new Rectangle(50, 50, Color.YELLOW);
        return List.of(rect0, rect1, rect2, rect3);
    }

    public ExplorationView(Stage stage) {
        this.stage = stage;
        this.playerRectangles = degub__createRectangles();
        // Crea la MapView
        this.mapView = new MapView(MAP_FILE_PATH, TILESET_IMAGE_PATH);
        this.root = new Pane();
        this.scene = new Scene(root, stage.getWidth(), stage.getHeight());
        this.party = debug__createParty();
        this.movementController = new MovementController(party, scene);
        mapView.prefHeightProperty().bind(root.heightProperty());
        mapView.prefWidthProperty().bind(root.widthProperty());
    }

    public void showMap() {
        root.getChildren().add(mapView);

        AnimationTimer timer = new AnimationTimer() {
            public void handle(long p) {
                movementController.update();

                for (int i = 3; i > 0; i--) {
                    playerRectangles.get(i).setX(party.getMembers().get(i - 1).getPosition().getX());
                    playerRectangles.get(i).setY(party.getMembers().get(i - 1).getPosition().getY());
                }
                playerRectangles.get(0).setX(party.getMainPlayer().getPosition().getX());
                playerRectangles.get(0).setY(party.getMainPlayer().getPosition().getY());
            }
        };

        playerRectangles.forEach(root.getChildren()::add);
        stage.setScene(scene);
        stage.show();
        timer.start();
    }
}