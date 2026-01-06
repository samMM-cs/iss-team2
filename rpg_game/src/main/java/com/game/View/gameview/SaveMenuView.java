package com.game.view.gameview;

import java.util.Optional;

import com.game.controller.GameController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class SaveMenuView extends VBox {
    private static final int SLOTS = 3;
    private final GameController gameController;
    private boolean used;

    public SaveMenuView(GameController gameController) {
        this.gameController = gameController;
        setSpacing(20);
        setPadding(new Insets(30));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #2b2b2b;");

        // Label
        Label title = new Label("Save Game");
        title.setFont(Font.font(26));
        title.setStyle("-fx-text-fill: white;");

        getChildren().add(title);

        for (int i = 0; i < SLOTS; i++) {
            getChildren().add(createSlotRow(i));
        }
        Button backBtn = new Button("Back to Menu");
        backBtn.setVisible(true);
        backBtn.setPrefWidth(200);
        backBtn.setStyle("""
                            -fx-background-color: #555;
                            -fx-text-fill: white;
                            -fx-font-size: 14;
                """);
        backBtn.setOnAction(e -> {
            this.setVisible(false);
            getChildren().forEach(node -> node.setEffect(null));
        });

        getChildren().add(backBtn);
    }

    private HBox createSlotRow(int slot) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10));
        row.setStyle("""
                    -fx-background-color: #3a3a3a;
                    -fx-background-radius: 8;
                """);

        Label slotLabel = new Label("Slot " + slot);
        slotLabel.setPrefWidth(80);
        slotLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        Label statusLabel = new Label();
        statusLabel.setPrefWidth(80);
        statusLabel.setStyle("-fx-text-fill: gray;");

        Button saveBtn = new Button("Save");
        styleButton(saveBtn, "#4CAF50");

        saveBtn.setOnAction(e -> {
            used=gameController.getSaveManager().isSlotUsed(slot);
            if (!used) {
                try {
                    gameController.saveGame(slot);
                    statusLabel.setText("Saved");
                    statusLabel.setStyle("-fx-text-fill: lightgreen;");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else
                askOverwrite(slot, statusLabel);
        });

        row.getChildren().addAll(slotLabel, statusLabel, saveBtn);
        return row;
    }

    private void styleButton(Button button, String color) {
        button.setPrefWidth(80);
        button.setStyle("""
                    -fx-background-color: %s;
                    -fx-text-fill: white;
                    -fx-font-size: 13;
                    -fx-background-radius: 6;
                """.formatted(color));
    }

    private void askOverwrite(int slot, Label statusLabel) {
        boolean used = gameController.getSaveManager().isSlotUsed(slot);

        if (!used) {
            statusLabel.setText("Empty");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Overwrite Save");
        alert.setHeaderText("Overwrite existing save?");
        alert.setContentText("Slot " + slot + " already contains a save.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                gameController.saveGame(slot);
                statusLabel.setText("Saved");
                statusLabel.setStyle("-fx-text-fill: lightgreen;");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
