package com.game.view;

import com.game.model.character.NPC;
import com.game.model.character.Player;
import com.game.model.battle.Move;

import javafx.scene.layout.HBox;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class ShopView extends VBox {
    private final Player player;
    private Label goldLabel;
    private VBox movesBox;
    private Label titleLabel;
    private Label feedbackLabel;

    public ShopView(Player player) {
        this.player = player;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setMaxWidth(500);
        this.setStyle(
                "-fx-background-color: rgba(30, 30, 30, 0.9); -fx-border-color: #d4af37; -fx-border-width: 4; -fx-padding: 20; -fx-background-radius: 20; -fx-border-radius: 20;");

        titleLabel = new Label("");
        titleLabel.setStyle("-fx-text-fill: #d4af37; -fx-font-size: 28; -fx-font-weight: bold;");

        feedbackLabel = new Label("");
        feedbackLabel.setStyle("-fx-text-fill: #00ff00; -fx-font-size: 14; -fx-font-weight: bold;");

        goldLabel = new Label("Money: " + player.getCurrentStats().getMoney());
        goldLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 18;");

        movesBox = new VBox(10);
        movesBox.setAlignment(Pos.CENTER);

        Button closeBtn = new Button("Exit");
        closeBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white; -fx-font-weight: bold;");
        closeBtn.setOnAction(e -> closed());

        this.getChildren().addAll(titleLabel, goldLabel, movesBox, closeBtn, feedbackLabel);
    }

    private void addShopItem(Move move) {
        HBox itemBox = new HBox(20);
        itemBox.setAlignment(Pos.CENTER_LEFT);

        itemBox.setStyle(
                "-fx-background-color: rgba(50,50,50,0.8);" +
                        "-fx-padding: 12;" +
                        "-fx-border-color: #d4af37;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;");

        itemBox.setOnMouseEntered(e -> itemBox.setStyle(
                "-fx-background-color: rgba(70,70,70,0.9);" +
                        "-fx-padding: 12;" +
                        "-fx-border-color: #ffd700;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;"));
        itemBox.setOnMouseExited(e -> itemBox.setStyle(
                "-fx-background-color: rgba(50,50,50,0.8);" +
                        "-fx-padding: 12;" +
                        "-fx-border-color: #d4af37;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;"));

        Label nameLabel = new Label(move.getName() + " - " + move.getCost() + " €");
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold;");

        Button buyButton = new Button("Buy");
        buyButton.setStyle(
                "-fx-background-color: #228B22;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;");
        buyButton.setOnMouseEntered(e -> buyButton.setStyle(
                "-fx-background-color: #2ecc71;" +
                        "-fx-text-fill: black;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;"));
        buyButton.setOnMouseExited(e -> buyButton.setStyle(
                "-fx-background-color: #228B22;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;"));

        buyButton.setCursor(Cursor.HAND);
        buyButton.setOnAction(e -> {
            if (player.getCurrentStats().getMoney() >= move.getCost()) {
                player.getCurrentStats().removeMoney(move.getCost());
                player.learnMove(move);
                updateUI();
                showFeedback(move.getName());
                buyButton.setDisable(true); // disabilita il pulsante dopo l'acquisto
            }
        });

        itemBox.getChildren().addAll(nameLabel, buyButton);
        movesBox.getChildren().add(itemBox);
    }

    private void showFeedback(String moveName) {
        feedbackLabel.setText("Hai acquistato " + moveName);

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> feedbackLabel.setText(""));
        delay.play();
    }

    public void updateUI() {
        goldLabel.setText("Money: " + player.getCurrentStats().getMoney());
    }

    public void open(NPC npc) {
        titleLabel.setText(npc.getJob().name());
        movesBox.getChildren().clear();
        for (Move move : npc.getShopMoves()) {
            addShopItem(move);
        }
        this.setVisible(true);
    }

    public void closed() {
        this.setVisible(false);
    }
}