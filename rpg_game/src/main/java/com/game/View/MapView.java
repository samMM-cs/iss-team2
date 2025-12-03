package com.game.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;

public class MapView {

    private Stage stage;
    private Image tileset;
    private Rectangle playerRectangle;

    public MapView(Stage stage) {
        this.stage = stage;
        this.tileset = new Image(getClass().getResourceAsStream("/map/background4a.png"));

        this.playerRectangle = new Rectangle(50, 50, Color.BLUE);
    }

    public void showMap() {
        // Pane principale
        Pane root = new Pane();

        // Immagine sfondo
        ImageView map = new ImageView(tileset);
        map.setPreserveRatio(true);
        map.setFitWidth(stage.getWidth());
        root.getChildren().add(map);
        root.getChildren().add(playerRectangle);

        Scene scene = new Scene(root, tileset.getWidth(), tileset.getHeight());
        stage.setScene(scene);
        stage.show();
    }
}