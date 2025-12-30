package com.game.view;

import com.game.model.character.CharacterPG;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    private ImageView heart_view;

    private final Image[] heart_img = { new Image("/battle/icons/heart/Sprite_heart.png"),
            new Image("/battle/icons/heart/Sprite_heart_2.png"),
            new Image("/battle/icons/heart/Sprite_heart_3.png"),
            new Image("/battle/icons/heart/Sprite_heart_4.png") };

    public HUD(CharacterPG c) {
        this.statsCharacter = c.getCurrentStats();
        setSpacing(10);
        setPadding(new Insets(16));
        setAlignment(Pos.TOP_LEFT);

        setStyle("""
                    -fx-background-color: rgba(15, 15, 15, 0.8);
                    -fx-background-radius: 12;
                    -fx-border-radius: 12;
                    -fx-border-color: #888;
                    -fx-border-width: 2;
                """);

        playerName = new Label(String.valueOf(c.getJob()));
        playerName.setFont(Font.font(18));
        playerName.setStyle("""
                -fx-text-fill: gold;
                -fx-font-weight: bold;
                    """);
        // HP
        hpBar = new ProgressBar();
        hpBar.setPrefWidth(180);
        hpBar.setStyle("""
                    -fx-accent: linear-gradient(#ff4b4b, #b00000);
                    -fx-control-inner-background: #400000;
                    -fx-background-radius: 8;
                """);
        hpLabel = new Label("HP");
        hpLabel.setStyle("-fx-text-fill: #eeeeee; -fx-font-size: 11;");
        heart_view = new ImageView(heart_img[0]);
        heart_view.setFitHeight(20);
        heart_view.setFitWidth(20);
        HBox hpBox = new HBox(6);
        hpBox.setAlignment(Pos.CENTER_LEFT);
        hpBox.getChildren().addAll(heart_view, hpBar, hpLabel);

        // XP
        HBox xpBox = new HBox(6);
        xpBar = new ProgressBar();
        xpBar.setPrefWidth(180);
        xpBar.setStyle("""
                    -fx-accent: linear-gradient(#4b8bff, #0033aa);
                    -fx-control-inner-background: #001a40;
                    -fx-background-radius: 8;
                """);
        xpLabel = new Label("XP");
        xpLabel.setStyle("-fx-text-fill: #eeeeee; -fx-font-size: 11;");
        xpBox.getChildren().addAll(xpBar, xpLabel);
        getChildren().addAll(playerName, hpBox, xpBox);
    }

    private int getHeartFrame(double hpPerc) {
        int frame = (int) Math.floor((1 - hpPerc) * (heart_img.length - 1));
        return Math.min(frame, heart_img.length - 1);
    }

    public void update() {
        heart_view.setImage(heart_img[getHeartFrame(statsCharacter.getHpPerc())]);
        hpBar.setProgress(statsCharacter.getHpPerc());
        hpLabel.setText("HP: " + statsCharacter.getHp());

        xpBar.setProgress(statsCharacter.getXpPerc());
        xpLabel.setText("XP: " + statsCharacter.getXp() + " / " + statsCharacter.getMaxXp());

        level.setText("Level: " + statsCharacter.getLevel());
    }
}
