package com.game.View;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import com.game.Controller.CharacterSelectionController;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.*;

public class CharacterSelectionView {
    private Stage stage;
    private CharacterSelectionController controller;

    public CharacterSelectionView(Stage stage) {
        this.stage = stage;
        this.controller = new CharacterSelectionController(stage);
    }

    public void selectCharacters() {
        VBox buttons = new VBox(15);
        buttons.setAlignment(Pos.CENTER);

        ImageView imgSfondo = new ImageView(new Image(getClass().getResourceAsStream("/Forest.png")));

        StackPane root = new StackPane(imgSfondo, buttons);

        // Personaggi
        // ImageView archer = new ImageView(new
        // Image(getClass().getResourceAsStream("/characters/archer.png")));
        Button archerBtn = new Button("Archer");
        buttons.getChildren().addAll(archerBtn);
        archerBtn.setOnAction(controller::onArcher);

        Button backBtn = new Button("Back");
        buttons.getChildren().addAll(backBtn);
        backBtn.setOnAction(controller::onBack);
        Scene scene = new Scene(root, 800, 600);
        imgSfondo.fitHeightProperty().bind(scene.heightProperty());
        imgSfondo.fitWidthProperty().bind(scene.widthProperty());

        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }
}
