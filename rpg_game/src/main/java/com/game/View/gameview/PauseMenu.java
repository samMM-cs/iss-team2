package com.game.view.gameview;

import javafx.geometry.Pos;

import com.game.controller.ViewManager;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class PauseMenu extends StackPane {

    public PauseMenu() {
        super();
        // --- SFONDO SCURO SEMI-TRASPARENTE ---
        Rectangle overlay = new Rectangle();
        overlay.setFill(Color.rgb(255, 255, 255, 0.25));
        overlay.widthProperty().bind(this.widthProperty());
        overlay.heightProperty().bind(this.heightProperty());

        // --- CONTENITORE PRINCIPALE ---
        VBox box = new VBox(18);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(25));

        // --- CORNICE PIXEL ART ---
        box.setStyle("""
                    -fx-background-color: transparent;
                    -fx-border-color: black;
                    -fx-border-width: 4px;
                    -fx-border-insets: 0;
                    -fx-border-style: solid;
                """);

        // --- TITOLO ---
        Label title = new Label("PAUSE");
        title.setFont(Font.font("Verdana", 26));
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-effect: dropshadow(gaussian, black, 4, 0.8, 0, 0);");

        // --- PULSANTI ---
        Button resume = createMenuButton("Resume");
        resume.setOnAction(e -> ViewManager.getInstance().togglePause());
        Button quit = createMenuButton("Quit to Title");
        quit.setOnAction(e->ViewManager.getInstance().exit());
        box.getChildren().addAll(title, resume, quit);

        getChildren().addAll(overlay, box);
        StackPane.setAlignment(box, Pos.CENTER);

        setPickOnBounds(true);
        setMouseTransparent(false);
        setVisible(false);
    }

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(180);
        btn.setPrefHeight(38);

        btn.setFont(Font.font("Verdana", 16));
        btn.setTextFill(Color.WHITE);

        btn.setStyle("""
                    -fx-background-color: linear-gradient(to bottom, #444, #222);
                    -fx-border-color: black;
                    -fx-border-width: 3px;
                    -fx-background-radius: 0;
                    -fx-border-radius: 0;
                """);

        // --- HOVER EFFECT RPG ---
        btn.setOnMouseEntered(e -> btn.setStyle("""
                    -fx-background-color: linear-gradient(to bottom, #666, #333);
                    -fx-border-color: white;
                    -fx-border-width: 3px;
                    -fx-background-radius: 0;
                    -fx-border-radius: 0;
                """));

        btn.setOnMouseExited(e -> btn.setStyle("""
                    -fx-background-color: linear-gradient(to bottom, #444, #222);
                    -fx-border-color: black;
                    -fx-border-width: 3px;
                    -fx-background-radius: 0;
                    -fx-border-radius: 0;
                """));

        return btn;
    }

    public void showWithBlur(StackPane gameRoot) {
        setVisible(true);
        toFront();

        gameRoot.getChildren().forEach(node -> {
            if (node != this)
                node.setEffect(new GaussianBlur(10));
        });
    }

    public void hideMenu(StackPane gameRoot) {
        setVisible(false);

        gameRoot.getChildren().forEach(node -> node.setEffect(null));
    }
}
