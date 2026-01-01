package com.game.view;

import com.game.model.ability.AbilityType;
import com.game.model.character.NPC;
import com.game.model.character.Player;

import javafx.scene.layout.HBox;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class ShopView extends VBox {
    private Player player;
    private Label goldLabel;
    private VBox abilityBox;
    private Label title;
    private Label buyAbility;

    public ShopView(Player player) {
        this.player = player;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setMaxWidth(500); // Evita che lo shop si allarghi troppo
        this.setStyle(
                "-fx-background-color: rgba(30, 30, 30, 0.9); -fx-border-color: #d4af37; -fx-border-width: 4; -fx-padding: 20; -fx-background-radius: 20; -fx-border-radius: 20;");

        this.title = new Label("");
        title.setStyle("-fx-text-fill: #d4af37; -fx-font-size: 28; -fx-font-weight: bold;");
        this.buyAbility = new Label("");
        buyAbility.setStyle("-fx-text-fill: #00ff00; -fx-font-size: 14; -fx-font-weight: bold;");

        goldLabel = new Label("DISPONIBILITÀ: " + player.getCurrentStats().getMoney() + " €");
        goldLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 18;");

        abilityBox = new VBox(10); // Spazio tra le abilità
        abilityBox.setAlignment(Pos.CENTER);

        Button closeBtn = new Button("Exit");
        closeBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white; -fx-font-weight: bold;");
        closeBtn.setOnAction(e -> closed());

        this.getChildren().addAll(title, goldLabel, abilityBox, closeBtn, buyAbility);
    }

    private void addShopItem(AbilityType ability) {
        HBox itemBox = new HBox(20); // Aumentato spazio
        itemBox.setAlignment(Pos.CENTER_LEFT);

        itemBox.setStyle(
                "-fx-background-color: rgba(50,50,50,0.8);" +
                        "-fx-padding: 12;" +
                        "-fx-border-color: #d4af37;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;");

        // Effetto hover
        itemBox.setOnMouseEntered(e -> itemBox.setStyle(
                "-fx-background-color: rgba(70,70,70,0.9);" +
                        "-fx-padding: 12;" +
                        "-fx-border-color: #ffd700;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;"));
        itemBox.setOnMouseExited(e -> itemBox.setStyle(
                "-fx-background-color: rgba(50,50,50,0.8);" +
                        "-fx-padding: 12;" +
                        "-fx-border-color: #d4af37;" +
                        "-fx-border-width: 2;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;"));

        Label nameLabel = new Label(ability.getName() + " - " + ability.getCost() + " €");
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18;-fx-font-wight: bold");

        Button buyButton = new Button("Buy");
        buyButton.setStyle(
                "-fx-background-color: #228B22;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;");
        buyButton.setOnMouseEntered(e -> buyButton.setStyle(
                "-fx-background-color: #2ecc71;" +
                        "-fx-text-fill: black;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;"));
        buyButton.setOnMouseExited(e -> buyButton.setStyle(
                "-fx-background-color: #228B22;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;"));

        buyButton.setCursor(Cursor.HAND);
        buyButton.setOnAction(e -> {
            if (player.getCurrentStats().getMoney() >= ability.getCost()) {
                player.getCurrentStats().removeMoney(ability.getCost());
                player.addAbility(ability); // Assicurati che Player abbia questo metodo
                updateUI();
                showFeedback(ability.getName());
            } else {
                buyButton.setDisable(true);
                itemBox.setOpacity(1);
            }
        });
        itemBox.getChildren().addAll(nameLabel, buyButton);
        abilityBox.getChildren().add(itemBox);
    }

    private void showFeedback(String nameAbility) {
        buyAbility.setText("Hai acquistato " + nameAbility);

        buyAbility.setStyle("-fx-text-fill: " + "\"#00ff00\"" + "; -fx-font-size: 14;");

        // Timer per cancellare il testo dopo 2 secondi
        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(2));
        delay.setOnFinished(e -> buyAbility.setText(""));
        delay.play();
    }

    public void updateUI() {
        goldLabel.setText("Money: " + player.getCurrentStats().getMoney());
    }

    public void open(NPC npc) {
        this.title.setText(String.valueOf(npc.getJob()));
        abilityBox.getChildren().clear();
        for (AbilityType ability : npc.getShopAbilities()) {
            addShopItem(ability);
        }
        this.setVisible(true);
    }

    public void closed() {
        this.setVisible(false);
    }
}
