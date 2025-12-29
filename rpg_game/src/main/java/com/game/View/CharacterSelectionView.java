package com.game.view;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import com.game.controller.GameController;
import com.game.controller.ViewManager;
import com.game.model.character.Job;
import com.game.view.gameview.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;

public class CharacterSelectionView extends GameView {
    private GameController gameController;

    private VBox selectedCard = null;
    private Button startGameBtn;

    public CharacterSelectionView(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void show() {
        Label title = new Label("Scegli il personaggio");
        title.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 26;
                -fx-font-weight: bold;
                    """);

        HBox container = new HBox(30);
        container.setAlignment(Pos.CENTER);

        // Creo le card
        for (Job job : Job.values()) {
            if (job.getIsPlayable()) {
                VBox card = createCard(job);
                container.getChildren().add(card);
            }
        }

        startGameBtn = createButton(new Button(), "Start", "/Default.png");
        startGameBtn.setDisable(true);
        startGameBtn.setOnAction(e -> gameController.startExploration());

        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/Forest.png")));
        VBox ui = new VBox(40, title, container, startGameBtn);
        ui.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(background, ui);
        Scene scene = new Scene(root, 800, 600);
        background.fitHeightProperty().bind(scene.heightProperty());
        background.fitWidthProperty().bind(scene.widthProperty());

        ViewManager.getInstance().setAndShowScene(scene);
    }

    private VBox createCard(Job job) {
        Image img_sprite = new Image(getClass().getResourceAsStream("/characters/rogues.png"));

        ImageView sprite = new ImageView(img_sprite);
        sprite.setViewport(new Rectangle2D(job.getX(), job.getY(), Job.SIZE, Job.SIZE));

        sprite.setFitHeight(96);
        sprite.setFitWidth(96);
        sprite.setSmooth(false);

        Label name = new Label(job.name());
        name.setStyle("-fx-text-fill: white; -fx-font-size: 14;");

        Button select = new Button("Select");
        select.setOnAction(e -> gameController.onCharacterSelected(job));

        VBox card = new VBox(10, name, sprite);
        card.setAlignment(Pos.CENTER);
        card.setMinSize(140, 180);
        card.setStyle("""
                    -fx-background-color: rgba(0,0,0,0.4);
                    -fx-background-radius: 10;
                """);

        Tooltip tool = new Tooltip("Classe: " + job.name() + "\n" +
                "HP: " + job.getBaseStats().getHp() + "\n" +
                "ATK: " + job.getBaseStats().getAttack() + "\n" +
                "DEF: " + job.getBaseStats().getDefense() + "\n" +
                "SPE: " + job.getBaseStats().getSpecial() + "\n" +
                "SPD: " + job.getBaseStats().getSpeed());
        Tooltip.install(card, tool);

        // Hover effect
        card.setOnMouseEntered(e -> {
            if (card != selectedCard) {
                card.setStyle("""
                            -fx-background-color: rgba(0,0,0,0.6);
                            -fx-background-radius: 10;
                            -fx-scale-x: 1.05;
                            -fx-scale-y: 1.05;
                        """);
            }
        });

        card.setOnMouseExited(e -> {
            if (card != selectedCard) {
                card.setStyle("""
                            -fx-background-color: rgba(0,0,0,0.4);
                            -fx-background-radius: 10;
                        """);
            }
        });

        card.setOnMouseClicked(e -> selectCharacter(job, card));
        return card;
    }

    private void selectCharacter(Job job, VBox card) {
        if (selectedCard != null) {
            selectedCard.setStyle("""
                        -fx-background-color: rgba(0,0,0,0.4);
                        -fx-background-radius: 10;
                    """);
        }
        selectedCard = card;
        card.setStyle("""
                -fx-border-color: gold;
                -fx-border-width: 3;
                -fx-border-radius: 10;
                -fx-background-radius: 10;
                -fx-background-color: rgba(255,215,0,0.15);
                -fx-scale-x: 1.08;
                -fx-scale-y: 1.08;
                    """);
        startGameBtn.setDisable(false);
        gameController.onCharacterSelected(job);
    }

    private <T extends ButtonBase> T createButton(T btn, String txt, String path) {
        ImageView imgBtn = new ImageView(new Image(getClass().getResourceAsStream(path)));
        BackgroundImage bgImgBtn = new BackgroundImage(imgBtn.getImage(), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        btn.setText(txt);
        btn.setBackground(new Background(bgImgBtn));
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        btn.setPrefSize(200, 70);
        btn.setAlignment(Pos.CENTER);
        btn.setPadding(new Insets(0));
        btn.setContentDisplay(ContentDisplay.CENTER);

        // Effetto click
        btn.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> btn.setScaleX(0.95));
        btn.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> btn.setScaleY(0.95));
        btn.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            btn.setScaleX(1);
            btn.setScaleY(1);
        });
        return btn;
    }

    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
