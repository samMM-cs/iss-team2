package com.game.view;

import com.game.controller.GameController;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewGameView extends GameView {
    public static final int MAX_PLAYER = 4;

    public NewGameView(Stage stage) {
        this.stage = stage;
        this.controller = new GameController(stage);
    }

    @Override
    public void load() {
        System.out.println("Sei nella newGameView!");
        VBox buttons = new VBox(15);
        buttons.setAlignment(Pos.CENTER);

        ImageView imgSfondo = new ImageView(new Image(getClass().getResourceAsStream("/Forest.png")));

        StackPane root = new StackPane(imgSfondo, buttons);

        Button player1 = new Button("1 Player");
        buttons.getChildren().addAll(player1);
        player1.setOnAction(controller::on1player);

        Scene scene = new Scene(root, 800, 600);
        imgSfondo.fitHeightProperty().bind(scene.heightProperty());
        imgSfondo.fitWidthProperty().bind(scene.widthProperty());

        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }
}
