package com.game.view;

import com.game.model.character.NPC;
import com.game.model.character.Player;
import com.game.model.character.Party;
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
    private final Party party;
    private VBox movesBox;
    private Label titleLabel;
    private Label feedbackLabel;

    public ShopView(Party party) {
        this.party = party;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setMaxWidth(500);
        this.setStyle(
                "-fx-background-color: rgba(30, 30, 30, 0.9); -fx-border-color: #d4af37; -fx-border-width: 4; -fx-padding: 20; -fx-background-radius: 20; -fx-border-radius: 20;");

        titleLabel = new Label("");
        titleLabel.setStyle("-fx-text-fill: #d4af37; -fx-font-size: 28; -fx-font-weight: bold;");

        feedbackLabel = new Label("");
        feedbackLabel.setStyle("-fx-text-fill: #00ff00; -fx-font-size: 14; -fx-font-weight: bold;");

        movesBox = new VBox(10);
        movesBox.setAlignment(Pos.CENTER);

        Button closeBtn = new Button("Exit");
        closeBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white; -fx-font-weight: bold;");
        closeBtn.setOnAction(e -> closed());

        this.getChildren().addAll(titleLabel, movesBox, closeBtn, feedbackLabel);
    }

    private void addShopItem(Move move) {

        // Box principale
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

        // Nome mossa
        Label nameLabel = new Label(move.getName());
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold;");

        // Bottone Learn
        Button learnButton = new Button("Learn");
        learnButton.setStyle(
                "-fx-background-color: #228B22;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;");
        learnButton.setCursor(Cursor.HAND);

        learnButton.setOnMouseEntered(e -> learnButton.setStyle(
                "-fx-background-color: #2ecc71;" +
                        "-fx-text-fill: black;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;"));
        learnButton.setOnMouseExited(e -> learnButton.setStyle(
                "-fx-background-color: #228B22;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;"));
        learnButton.setAlignment(Pos.CENTER_RIGHT);
        final boolean canLearn = party.getMembers().stream()
                .anyMatch(player -> move.getReq().contains(player.getJob().name()) &&
                        !player.getCurrentMove().contains(move));

        learnButton.setDisable(!canLearn);

        learnButton.setOnAction(e -> {
            for (Player player : party.getMembers()) {
                if (canLearn) {
                    player.learnMove(move);
                    showFeedback(player.getJob().name(), move.getName());
                }
            }
            learnButton.setDisable(true);
        });

        itemBox.getChildren().addAll(nameLabel, learnButton);
        movesBox.getChildren().add(itemBox);
    }

    private void showFeedback(String name, String moveName) {
        feedbackLabel.setText(name + " " + " ha imparato" + " " + moveName);

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> feedbackLabel.setText(""));
        delay.play();
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