package com.game.view;

import com.game.model.character.Player;
import com.game.model.character.Stats;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class HUD extends VBox {

    private Stats statsCharacter;

    // Componenti principali
    private Rectangle hpFill;
    private Rectangle xpFill;
    private Label hpText;
    private Label xpText;
    private Label levelLabel;
    private ImageView heartView;

    private final double BAR_WIDTH = 180;
    private final double BAR_HEIGHT = 14;

    private final Image[] heartImages = {
            new Image("/battle/icons/heart/Sprite_heart.png"),
            new Image("/battle/icons/heart/Sprite_heart_2.png"),
            new Image("/battle/icons/heart/Sprite_heart_3.png"),
            new Image("/battle/icons/heart/Sprite_heart_4.png")
    };

    public HUD(Player player) {

        setSpacing(8);
        setPadding(new Insets(12));
        setAlignment(Pos.CENTER_LEFT);
        setMaxWidth(320);
        setStyle("""
                -fx-background-color: rgba(30, 30, 30, 0.95);
                -fx-background-radius: 14;
                -fx-border-color: #555;
                -fx-border-width: 2;
                """);

        // HEADER: Nome e Livello
        Label nameLabel = new Label(player.getJob().toString().toUpperCase());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.web("#FFD700")); // Oro brillante

        levelLabel = new Label();
        levelLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        levelLabel.setTextFill(Color.WHITE);

        HBox header = new HBox(12, nameLabel, levelLabel);
        header.setAlignment(Pos.CENTER_LEFT);

        // HP Section
        VBox hpSection = createHPSection();

        // XP Section
        VBox xpSection = createXPSection();

        getChildren().addAll(header, hpSection, xpSection);

        update(player);
    }

    private VBox createHPSection() {
        Label title = new Label("HEALTH");
        title.setStyle("-fx-text-fill: #AAA; -fx-font-size: 10; -fx-font-weight: bold;");

        heartView = new ImageView(heartImages[0]);
        heartView.setFitWidth(22);
        heartView.setFitHeight(22);
        heartView.setEffect(new DropShadow(4, Color.BLACK));

        StackPane barContainer = new StackPane();
        barContainer.setAlignment(Pos.CENTER_LEFT);

        Rectangle bg = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.rgb(60, 15, 15));
        bg.setArcWidth(10);
        bg.setArcHeight(10);

        hpFill = new Rectangle(0, BAR_HEIGHT);
        hpFill.setArcWidth(10);
        hpFill.setArcHeight(10);
        hpFill.setFill(new LinearGradient(
                0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#FF6A6A")),
                new Stop(1, Color.web("#FF0000")))
        );
        hpFill.setEffect(new DropShadow(4, Color.DARKRED));

        barContainer.getChildren().addAll(bg, hpFill);

        hpText = new Label();
        hpText.setTextFill(Color.WHITE);
        hpText.setFont(Font.font("Monospaced", 12));

        HBox row = new HBox(8, heartView, barContainer, hpText);
        row.setAlignment(Pos.CENTER_LEFT);

        return new VBox(2, title, row);
    }

    private VBox createXPSection() {
        Label title = new Label("EXPERIENCE");
        title.setStyle("-fx-text-fill: #AAA; -fx-font-size: 10; -fx-font-weight: bold;");

        StackPane barContainer = new StackPane();
        barContainer.setAlignment(Pos.CENTER_LEFT);

        Rectangle bg = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.rgb(15, 40, 15));
        bg.setArcWidth(10);
        bg.setArcHeight(10);

        xpFill = new Rectangle(0, BAR_HEIGHT);
        xpFill.setArcWidth(10);
        xpFill.setArcHeight(10);
        xpFill.setFill(new LinearGradient(
                0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#00FF7F")),
                new Stop(1, Color.web("#32CD32"))
        ));
        xpFill.setEffect(new DropShadow(3, Color.DARKGREEN));

        barContainer.getChildren().addAll(bg, xpFill);

        xpText = new Label();
        xpText.setTextFill(Color.WHITE);
        xpText.setFont(Font.font("Monospaced", 12));

        HBox row = new HBox(8, barContainer, xpText);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(0, 0, 0, 28));

        return new VBox(2, title, row);
    }

    public void update(Player player) {
        if (player.getCurrentStats() == null)
            return;

        statsCharacter = player.getCurrentStats();

        double hpRatio = statsCharacter.getHpPerc();
        double xpRatio = statsCharacter.getXpPerc();

        animateWidth(hpFill, hpRatio * BAR_WIDTH);
        animateWidth(xpFill, xpRatio * BAR_WIDTH);

        hpText.setText(statsCharacter.getHp() + "/" + statsCharacter.getMaxHp());
        xpText.setText(statsCharacter.getXp() + "/" + statsCharacter.getMaxXp());
        levelLabel.setText("LV. " + statsCharacter.getLevel());

        int frame = (int) Math.floor((1 - Math.max(0, Math.min(1, hpRatio))) * (heartImages.length - 1));
        heartView.setImage(heartImages[Math.min(frame, heartImages.length - 1)]);
    }

    private void animateWidth(Rectangle rect, double targetWidth) {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(rect.widthProperty(), targetWidth, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.millis(100), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        rect.setFill(rect == hpFill ? rect.getFill() : rect.getFill());
    }
}
