package com.game.view;

import com.game.model.character.CharacterPG;
import com.game.model.character.Stats;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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

    // Riferimenti diretti ai componenti per evitare ClassCastException
    private Rectangle hpFill;
    private Rectangle xpFill;
    private Label hpText;
    private Label xpText;
    private Label levelLabel;
    private ImageView heartView;

    private final double BAR_WIDTH = 180;
    private final double BAR_HEIGHT = 12;

    private final Image[] heartImages = {
            new Image("/battle/icons/heart/Sprite_heart.png"),
            new Image("/battle/icons/heart/Sprite_heart_2.png"),
            new Image("/battle/icons/heart/Sprite_heart_3.png"),
            new Image("/battle/icons/heart/Sprite_heart_4.png")
    };

    public HUD(CharacterPG c) {
        this.statsCharacter = c.getCurrentStats();

        // Setup Container principale
        setSpacing(10);
        setPadding(new Insets(15));
        setMaxWidth(300);
        setStyle("""
                    -fx-background-color: rgba(25, 25, 25, 0.9);
                    -fx-background-radius: 12;
                    -fx-border-color: #444;
                    -fx-border-width: 2;
                """);

        // 1. Header: Nome e Livello
        Label nameLabel = new Label(c.getJob().toString().toUpperCase());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.GOLD);

        levelLabel = new Label();
        levelLabel.setTextFill(Color.WHITE);
        levelLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        HBox header = new HBox(15, nameLabel, levelLabel);
        header.setAlignment(Pos.CENTER_LEFT);

        // 2. Sezione HP
        VBox hpSection = createHPSection();

        // 3. Sezione XP
        VBox xpSection = createXPSection();

        getChildren().addAll(header, hpSection, xpSection);

        // Primo aggiornamento dei valori
        update();
    }

    private VBox createHPSection() {
        Label title = new Label("HEALTH");
        title.setStyle("-fx-text-fill: #999; -fx-font-size: 9;");

        // Il cuore
        heartView = new ImageView(heartImages[0]);
        heartView.setFitWidth(20);
        heartView.setFitHeight(20);

        // La barra disegnata
        StackPane barContainer = new StackPane();
        barContainer.setAlignment(Pos.CENTER_LEFT);

        Rectangle bg = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.rgb(50, 20, 20));
        bg.setArcWidth(8);
        bg.setArcHeight(8);

        hpFill = new Rectangle(0, BAR_HEIGHT);
        hpFill.setArcWidth(8);
        hpFill.setArcHeight(8);
        hpFill.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#ff5f5f")), new Stop(1, Color.web("#b00000"))));

        barContainer.getChildren().addAll(bg, hpFill);

        hpText = new Label();
        hpText.setTextFill(Color.WHITE);
        hpText.setFont(Font.font("Monospaced", 11));

        HBox row = new HBox(8, heartView, barContainer, hpText);
        row.setAlignment(Pos.CENTER_LEFT);

        return new VBox(2, title, row);
    }

    private VBox createXPSection() {
        Label title = new Label("EXPERIENCE");
        title.setStyle("-fx-text-fill: #999; -fx-font-size: 9;");

        StackPane barContainer = new StackPane();
        barContainer.setAlignment(Pos.CENTER_LEFT);

        // Sfondo della barra (Verde scurissimo quasi nero)
        Rectangle bg = new Rectangle(BAR_WIDTH, BAR_HEIGHT, Color.rgb(20, 40, 20));
        bg.setArcWidth(8);
        bg.setArcHeight(8);

        xpFill = new Rectangle(0, BAR_HEIGHT);
        xpFill.setEffect(new javafx.scene.effect.DropShadow(5, Color.web("#43a047")));
        xpFill.setArcWidth(8);
        xpFill.setArcHeight(8);

        // GRADIENTE VERDE:
        xpFill.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#43a047")),
                new Stop(1, Color.web("#1b5e20"))));

        barContainer.getChildren().addAll(bg, xpFill);

        xpText = new Label();
        xpText.setTextFill(Color.WHITE);
        xpText.setFont(Font.font("Monospaced", 11));

        HBox row = new HBox(8, barContainer, xpText);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(0, 0, 0, 28));

        return new VBox(2, title, row);
    }

    public void update() {
        if (statsCharacter == null)
            return;

        double hpRatio = statsCharacter.getHpPerc();
        double xpRatio = statsCharacter.getXpPerc();

        // Animazione della larghezza dei rettangoli
        animateWidth(hpFill, hpRatio * BAR_WIDTH);
        animateWidth(xpFill, xpRatio * BAR_WIDTH);

        // Update testi
        hpText.setText(statsCharacter.getHp() + "/" + statsCharacter.getMaxHp());
        xpText.setText(statsCharacter.getXp() + "/" + statsCharacter.getMaxXp());
        levelLabel.setText("LV. " + statsCharacter.getLevel());

        // Update cuore
        int frame = (int) Math.floor((1 - Math.max(0, Math.min(1, hpRatio))) * (heartImages.length - 1));
        heartView.setImage(heartImages[Math.min(frame, heartImages.length - 1)]);
    }

    private void animateWidth(Rectangle rect, double targetWidth) {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(rect.widthProperty(), targetWidth);
        KeyFrame kf = new KeyFrame(Duration.millis(400), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }
}