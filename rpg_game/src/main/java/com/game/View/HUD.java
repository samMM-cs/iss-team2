package com.game.view;

import com.game.model.character.CharacterPG;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import com.game.model.character.Stats;

public class HUD extends VBox {
    private ProgressBar hpBar;
    private ProgressBar xpBar;
    private Label level;
    private Label hpLabel;
    private Label xpLabel;
    private Label playerName;
    private Stats statsCharacter;

    private final Image[] heart_img = { new Image("/battle/icons/heart/Sprite_heart.png"),
            new Image("/battle/icons/heart/Sprite_heart_2.png"),
            new Image("/battle/icons/heart/Sprite_heart_3.png"),
            new Image("/battle/icons/heart/Sprite_heart_4.png") };

    public HUD(CharacterPG c) {
        this.statsCharacter = c.getCurrentStats();
        setSpacing(8);
        setPadding(new Insets(12));
        setAlignment(Pos.TOP_LEFT);

        setStyle("""
                    -fx-background-color: rgba(15, 15, 15, 0.8);
                    -fx-background-radius: 12;
                    -fx-border-radius: 12;
                    -fx-border-color: #777;
                    -fx-border-width: 2;
                """);

        playerName = new Label(String.valueOf(c.getJob()));
        playerName.setFont(Font.font(16));
        playerName.setStyle("""
                -fx-text-fill: gold;
                -fx-font-weight: bold;
                    """);
        hpBar = new ProgressBar();
        hpBar.setPrefWidth(180);
        hpBar.setStyle("""
                    -fx-accent: linear-gradient(#ff4b4b, #b00000);
                    -fx-control-inner-background: #400000;
                    -fx-background-radius: 8;
                """);

        xpBar = new ProgressBar();
        xpBar.setPrefWidth(180);
        xpBar.setStyle("""
                    -fx-accent: linear-gradient(#4b8bff, #0033aa);
                    -fx-control-inner-background: #001a40;
                    -fx-background-radius: 8;
                """);

        hpLabel = new Label("HP");
        xpLabel = new Label("XP");
        hpLabel.setStyle("-fx-text-fill: #eeeeee; -fx-font-size: 11;");
        xpLabel.setStyle("-fx-text-fill: #eeeeee; -fx-font-size: 11;");

        getChildren().addAll(playerName, hpBar, hpLabel, xpBar, xpLabel);
    }

    public void update() {
        hpBar.setProgress(1);
        hpLabel.setText("HP: " + statsCharacter.getHp());

        xpBar.setProgress(statsCharacter.getXpPerc());
        xpLabel.setText("XP: " + statsCharacter.getXp() + " / " + statsCharacter.getMaxXp());

        level.setText("Level: " + statsCharacter.getLevel());
    }
}
