package com.game.view.gameview;

import com.game.controller.GameController;
import com.game.controller.ViewManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class ContinueGameView extends GameView {
    private static final int SLOTS = 3;
    private GameController gameController;
    private VBox root;

    public ContinueGameView(GameController gameController) {
        super();
        this.gameController = gameController;
        setStyle("-fx-background-color: #2b2b2b;");
        root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2b2b2b;");
    }

    @Override
    public void show() {
        getChildren().clear(); // pulizia

        // 🌲 BACKGROUND A SCHERMO INTERO
        ImageView bg = new ImageView(new Image(getClass().getResourceAsStream("/Forest.png")));
        bg.setFitWidth(ViewManager.getInstance().getWidth());
        bg.setFitHeight(ViewManager.getInstance().getHeight());
        bg.setPreserveRatio(false);

        // overlay scuro per leggibilità
        Rectangle overlay = new Rectangle(
                ViewManager.getInstance().getWidth(),
                ViewManager.getInstance().getHeight(),
                Color.rgb(0, 0, 0, 0.55));

        // 🎴 PANNELLO CENTRALE
        VBox panel = new VBox(25);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(40));
        panel.setStyle("""
                    -fx-background-color: rgba(30,30,30,0.85);
                    -fx-background-radius: 20;
                    -fx-border-color: gold;
                    -fx-border-width: 3;
                    -fx-border-radius: 20;
                """);

        // TITOLO
        Label title = new Label("Continue Game");
        title.setFont(Font.font("Georgia", 32));
        title.setStyle("-fx-text-fill: gold; -fx-effect: dropshadow(gaussian, black, 4, 0.5, 0, 0);");

        panel.getChildren().add(title);

        // 🎮 SLOT
        for (int i = 0; i < SLOTS; i++) {
            panel.getChildren().add(createSlotRow(i));
        }
        panel.getChildren().add(createAutosaveSlotRow());

        // 🔙 BACK BUTTON
        Button backBtn = new Button("Back to Menu");
        backBtn.setPrefWidth(250);
        backBtn.setStyle("""
                    -fx-background-color: linear-gradient(#444, #222);
                    -fx-text-fill: white;
                    -fx-font-size: 18;
                    -fx-background-radius: 10;
                    -fx-border-color: gold;
                    -fx-border-width: 2;
                    -fx-border-radius: 10;
                """);

        backBtn.setOnMouseEntered(e -> backBtn.setStyle("""
                    -fx-background-color: linear-gradient(#666, #333);
                    -fx-text-fill: white;
                    -fx-font-size: 18;
                    -fx-background-radius: 10;
                    -fx-border-color: gold;
                    -fx-border-width: 2;
                    -fx-border-radius: 10;
                """));

        backBtn.setOnMouseExited(e -> backBtn.setStyle("""
                    -fx-background-color: linear-gradient(#444, #222);
                    -fx-text-fill: white;
                    -fx-font-size: 18;
                    -fx-background-radius: 10;
                    -fx-border-color: gold;
                    -fx-border-width: 2;
                    -fx-border-radius: 10;
                """));

        backBtn.setOnAction(e -> {
            this.setVisible(false);
            this.setManaged(false);
            ViewManager.getInstance().showMainMenu();
        });

        panel.getChildren().add(backBtn);

        // AGGIUNTA AL ROOT DELLA VIEW
        getChildren().addAll(bg, overlay, panel);

        // CENTRARE IL PANNELLO
        panel.layoutXProperty().bind(widthProperty().subtract(panel.widthProperty()).divide(2));
        panel.layoutYProperty().bind(heightProperty().subtract(panel.heightProperty()).divide(2));

        this.setVisible(true);
        this.setManaged(true);
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

        boolean used = gameController.getSaveManager().isSlotUsed(slot);

        Label status = new Label(used ? "Saved" : "Empty");
        status.setPrefWidth(80);
        status.setStyle(used
                ? "-fx-text-fill: lightgreen;"
                : "-fx-text-fill: gray;");
        Button loadBtn = new Button("Load");
        loadBtn.setDisable(!used); // disabilita se vuoto
        loadBtn.setPrefWidth(80);
        loadBtn.setStyle("""
                        -fx-background-color: #4CAF50;
                        -fx-text-fill: white;
                        -fx-font-size: 13;
                        -fx-background-radius: 6;
                """);

        loadBtn.setOnAction(e -> {
            try {
                gameController.loadGame(slot);
                showMessage("Game loaded from slot " + slot);
            } catch (Exception ex) {
                showMessage("Error loading slot " + slot);
                ex.printStackTrace();
            }
        });

        row.getChildren().addAll(slotLabel, status, loadBtn);
        return row;
    }

    private HBox createAutosaveSlotRow() {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10));
        row.setStyle("""
                        -fx-background-color: #3a3a3a;
                        -fx-background-radius: 8;
                """);

        Label slotLabel = new Label("Autosave");
        slotLabel.setPrefWidth(80);
        slotLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        boolean used = gameController.getSaveManager().isAutosaveUsed();

        Label status = new Label(used ? "Saved" : "Empty");
        status.setPrefWidth(80);
        status.setStyle(used
                ? "-fx-text-fill: lightgreen;"
                : "-fx-text-fill: gray;");
        Button loadBtn = new Button("Load");
        loadBtn.setDisable(!used); // disabilita se vuoto
        loadBtn.setPrefWidth(80);
        loadBtn.setStyle("""
                        -fx-background-color: #4CAF50;
                        -fx-text-fill: white;
                        -fx-font-size: 13;
                        -fx-background-radius: 6;
                """);

        loadBtn.setOnAction(e -> {
            try {
                gameController.loadFromAutosave();
                showMessage("Game loaded from autosave");
            } catch (Exception ex) {
                showMessage("Error loading autosave");
                ex.printStackTrace();
            }
        });

        row.getChildren().addAll(slotLabel, status, loadBtn);
        return row;
    }

    @Override
    public void showMessage(String msg) {

    }
}
