package com.game.view;

import com.game.controller.MovementController;
import com.game.model.Job;
import com.game.model.Party;
import com.game.model.Player;
import com.game.model.Position;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;

public class ExplorationView {
    private MovementController movementController;
    private Stage stage;
    private Image tileset;
    private Rectangle playerRectangle;
    private Party party;
    private Pane root;
    private Scene scene;

    public ExplorationView(Stage stage) {
        this.stage = stage;
        this.tileset = new Image(getClass().getResourceAsStream("/map/background4a.png"));
        this.playerRectangle = new Rectangle(50, 50, Color.BLUE);
        this.root = new Pane();
        this.scene = new Scene(root, tileset.getWidth(), tileset.getHeight());
        this.party = new Party(new Player(new Job(), new Position(0, 0)), null);
        this.movementController = new MovementController(party, scene);
    }

    public void showMap() {
        AnimationTimer timer = new AnimationTimer() {
            public void handle(long p) {
                movementController.update();
                playerRectangle.setX(party.getMainPlayer().getPosition().getX());
                playerRectangle.setY(party.getMainPlayer().getPosition().getY());
            }
        };
        // Immagine sfondo
        ImageView map = new ImageView(tileset);
        map.setPreserveRatio(false);
        map.setFitWidth(stage.getWidth());
        root.getChildren().add(map);
        root.getChildren().add(playerRectangle);
        stage.setScene(scene);
        stage.show();
        timer.start();
    }
}