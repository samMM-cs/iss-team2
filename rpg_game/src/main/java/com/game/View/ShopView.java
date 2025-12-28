package com.game.view;

import com.game.model.ability.Ability;
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

        Button closeBtn = new Button("ESCI");
        closeBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white; -fx-font-weight: bold;");
        closeBtn.setOnAction(e -> closed());

        this.getChildren().addAll(title, goldLabel, abilityBox, closeBtn,buyAbility);
    }

    private void addShopItem(Ability ability) {
        HBox itemBox = new HBox(15); // Aumentato spazio
        itemBox.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(ability.getName() + " - " + ability.getCost() + " €");
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16;");

        Button buyButton = new Button("Compra");
        buyButton.setCursor(Cursor.HAND);
        buyButton.setOnAction(e -> {
            if (player.getCurrentStats().getMoney() >= ability.getCost()) {
                player.getCurrentStats().removeMoney(ability.getCost());
                player.addAbility(ability); // Assicurati che Player abbia questo metodo
                updateUI();
                showFeedback(ability.getName());
            } else
                buyButton.setText("No Money!"); // Feedback visivo immediato
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
        for (Ability ability : npc.getShopAbilities()) {
            addShopItem(ability);
        }
        this.setVisible(true);
    }

    public void closed() {
        this.setVisible(false);
    }
}
