package com.game.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import com.game.controller.GameController;
import com.game.model.character.Job;
import com.game.view.gameview.*;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.*;

public class CharacterSelectionView extends GameView {
    private GameController gameController;

    private VBox selectedCard = null;
    private Button startGameBtn;

    public CharacterSelectionView(Stage stage, GameController gameController) {
        super(stage);
        this.gameController = gameController;

    }

    @Override
    public void show() {
        if (stage == null) {
            System.err.println("Stage nullo in CharacterSelectionView");
            return;
        }

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

        startGameBtn = new Button("Start Game");
        startGameBtn.setDisable(true);
        startGameBtn.setStyle("""
                    -fx-font-size: 16;
                    -fx-padding: 10 25;
                """);
        startGameBtn.setOnAction(e -> gameController.startExploration());

        ImageView background = new ImageView(new Image(getClass().getResourceAsStream("/Forest.png")));
        VBox ui = new VBox(40, title, container, startGameBtn);
        ui.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(background, ui);
        Scene scene = new Scene(root, 800, 600);
        background.fitHeightProperty().bind(scene.heightProperty());
        background.fitWidthProperty().bind(scene.widthProperty());

        stage.setScene(scene);
        stage.show();
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

        /*Tooltip tool = new Tooltip("Classe: " + job.name() + "\n" +
                "HP: " + job.getHp() + "\n" +
                "ATK: " + job.getAttack() + "\n" +
                "SPD: " + job.getSpeed());
        Tooltip.install(card, tool);*/

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

    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
