package com.game.view.gameview;

import java.util.Optional;

import com.game.controller.GameController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class SaveMenuView extends VBox {

    private static final int SLOTS = 3;
    private final GameController gameController;

    public SaveMenuView(GameController gameController) {
        this.gameController = gameController;

        setSpacing(25);
        setPadding(new Insets(40));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: linear-gradient(to bottom, #1f1f1f, #2b2b2b);");

        Label title = new Label("SAVE GAME");
        title.setFont(Font.font("Arial Black", 28));
        title.setStyle("-fx-text-fill: #eaeaea;");

        getChildren().add(title);

        for (int i = 0; i < SLOTS; i++) {
            getChildren().add(createSlotCard(i));
        }

        Button backBtn = new Button("Back to Menu");
        backBtn.setPrefWidth(220);
        backBtn.setStyle("""
                -fx-background-color: #444;
                -fx-text-fill: white;
                -fx-font-size: 14;
                -fx-background-radius: 10;
                """);

        backBtn.setOnMouseEntered(e -> backBtn.setStyle("""
                -fx-background-color: #666;
                -fx-text-fill: white;
                -fx-font-size: 14;
                -fx-background-radius: 10;
                """));

        backBtn.setOnMouseExited(e -> backBtn.setStyle("""
                -fx-background-color: #444;
                -fx-text-fill: white;
                -fx-font-size: 14;
                -fx-background-radius: 10;
                """));

        backBtn.setOnAction(e -> setVisible(false));

        getChildren().add(backBtn);
    }

    private VBox createSlotCard(int slot) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPrefWidth(360);
        card.setStyle("""
                -fx-background-color: #333;
                -fx-background-radius: 12;
                """);

        Label slotLabel = new Label("Slot " + slot);
        slotLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16;");

        Label statusLabel = new Label();
        statusLabel.setFont(Font.font(13));

        Button saveBtn = new Button("SAVE");
        saveBtn.setPrefWidth(100);

        updateSlotStatus(slot, statusLabel, saveBtn);

        saveBtn.setOnAction(e -> handleSave(slot, statusLabel, saveBtn));

        card.getChildren().addAll(
                slotLabel,
                new Separator(),
                new HBox(20, statusLabel, saveBtn));

        return card;
    }

    private void updateSlotStatus(int slot, Label status, Button btn) {
        boolean used = gameController.getSaveManager().isSlotUsed(slot);
        //boolean isValid = gameController.getSaveManager().isSlotValid(slot);

        if (!used) {
            status.setText("EMPTY");
            status.setStyle("-fx-text-fill: #8bc34a;");
            styleButton(btn, "#4caf50");
        } else {
            status.setText("USED");
            status.setStyle("-fx-text-fill: #ffb347;");
            styleButton(btn, "#ff9800");
        }
    }

    private void handleSave(int slot, Label status, Button btn) {
        boolean used = gameController.getSaveManager().isSlotUsed(slot);

        if (used && !confirmOverwrite(slot))
            return;

        gameController.saveGame(slot);
        updateSlotStatus(slot, status, btn);
    }

    private boolean confirmOverwrite(int slot) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Overwrite Save");
        alert.setHeaderText("Overwrite existing save?");
        alert.setContentText("Slot " + slot + " already contains a save.");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void styleButton(Button button, String color) {
        button.setStyle("""
                -fx-background-color: %s;
                -fx-text-fill: white;
                -fx-font-size: 13;
                -fx-background-radius: 8;
                """.formatted(color));
    }
}
