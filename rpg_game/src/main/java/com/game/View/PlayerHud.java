package com.game.view;

import com.game.model.character.CharacterPG;
import com.game.model.character.Stats;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class PlayerHud extends VBox {
    private ProgressBar hpBar;
    private ProgressBar expBar;
    private Label levelLabel;
    private Label hpLabel;

    private CharacterPG character;

    public PlayerHud(CharacterPG character) {
        this.character = character;

        // --- HP e label "HP" a sinistra ---
        hpLabel = new Label("HP");
        hpLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        hpBar = new ProgressBar();
        hpBar.setPrefWidth(180);
        hpBar.setStyle("""
                    -fx-accent: #e74c3c;
                    -fx-control-inner-background: #333333;
                    -fx-background-radius: 8;
                    -fx-border-radius: 8;
                """);

        HBox hpBox = new HBox(5, hpLabel, hpBar);
        hpBox.setAlignment(Pos.CENTER_LEFT);

        // --- Level label ---
        levelLabel = new Label();
        levelLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        // --- EXP bar sotto ---
        expBar = new ProgressBar();
        expBar.setPrefWidth(200);
        expBar.setStyle("""
                    -fx-accent: #3498db;
                    -fx-control-inner-background: #333333;
                    -fx-background-radius: 8;
                    -fx-border-radius: 8;
                """);

        // --- Layout verticale ---
        setSpacing(6);
        setAlignment(Pos.CENTER_LEFT);
        setStyle("""
                    -fx-background-color: rgba(0,0,0,0.5);
                    -fx-background-radius: 10;
                    -fx-padding: 10;
                """);

        getChildren().addAll(hpBox, levelLabel, expBar);

        update();
    }

    public void update() {
        Stats base = character.getBaseStats();
        Stats current = character.getCurrentStats();

        levelLabel.setText("Level " + base.getLevel());

        hpBar.setProgress((double) current.getHp() / base.getHp());
        expBar.setProgress(base.getXpPerc());
    }

}
