package com.game.view;

import com.game.model.character.dialogue.*;

import javafx.util.Duration;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;

import java.util.List;

public class DialogueView extends StackPane {
    private List<String> lines;
    private int currentIndex = 0;

    private Label dialogueLabel;
    private Label arrowLabel;

    private Timeline textAnimation;
    private boolean isAnimating = false;

    private Runnable onCloseCallback;

    public DialogueView() {
        initUI();
        this.setVisible(false);
    }

    public void initUI() {
        this.setPrefWidth(800);
        this.setAlignment(Pos.BOTTOM_CENTER);
        this.setStyle("-fx-padding: 30;");
        this.setMouseTransparent(false);

        // Dialogue box (Stile Retro)
        VBox dialogueBox = new VBox(10);
        dialogueBox.setPrefHeight(120);
        dialogueBox.setMaxWidth(700);
        dialogueBox.getStyleClass().add("dialogue-box"); // Se usi CSS
        dialogueBox.setStyle("""
                    -fx-background-color: #1c1c1c;
                    -fx-border-color: white;
                    -fx-border-width: 3;
                    -fx-border-radius: 5;
                    -fx-background-radius: 5;
                    -fx-padding: 20;
                """);

        dialogueLabel = new Label();
        dialogueLabel.setWrapText(true);
        dialogueLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20; -fx-font-family: 'Monospaced';");

        arrowLabel = new Label("▼");
        arrowLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16;");
        arrowLabel.setVisible(false);
        arrowLabel.setCursor(Cursor.HAND);
        arrowLabel.setOnMouseClicked(e -> {
            handleAdvance();
            e.consume(); // Per fermare la propagazione di un evento all'interno della gerarchia dei nodi
                         // della UI
        });

        dialogueBox.getChildren().addAll(dialogueLabel, arrowLabel);
        dialogueBox.setAlignment(Pos.TOP_LEFT);

        this.getChildren().add(dialogueBox);

        // Gestione dell'input per avanzare
        this.setOnMouseClicked(e -> handleAdvance());
    }

    private void playText(String text) {
        stopAnimation(); // Ferma eventuali timeline residue

        dialogueLabel.setText("");
        arrowLabel.setVisible(false);
        isAnimating = true;

        textAnimation = new Timeline();

        // Creazione dinamica dei frame basata sulla lunghezza del testo
        for (int i = 0; i <= text.length(); i++) {
            int length = i;
            KeyFrame frame = new KeyFrame(
                    Duration.millis(30 * i),
                    e -> dialogueLabel.setText(text.substring(0, length)));
            textAnimation.getKeyFrames().add(frame);
        }

        textAnimation.setOnFinished(e -> {
            isAnimating = false;
            arrowLabel.setVisible(true);
            FadeTransition fade = new FadeTransition(Duration.millis(500), arrowLabel);
            fade.setFromValue(1.0);
            fade.setToValue(0.2);
            fade.setCycleCount(Animation.INDEFINITE);
            fade.setAutoReverse(true);
            fade.play();
        });

        textAnimation.play();
    }

    public void showDialogue(Dialogue dialogue) {
        if (dialogue == null || dialogue.getLines().isEmpty())
            return;
        this.lines = dialogue.getLines();
        this.currentIndex = 0;
        this.setVisible(true); // Mostra la UI
        this.setMouseTransparent(false);
        playText(lines.get(currentIndex)); // Parte dal primo messaggio
    }

    public void handleAdvance() {
        if (isAnimating) {
            // Salta l'animazione e mostra tutto il testo subito
            completeText();
        } else {
            // Passa alla riga successiva
            currentIndex++;
            if (currentIndex < lines.size()) {
                playText(lines.get(currentIndex));
            } else {
                close();
            }
        }
    }

    public void setOnCloseClick(Runnable callback) {
        this.onCloseCallback = callback;
    }

    private void completeText() {
        stopAnimation();
        dialogueLabel.setText(lines.get(currentIndex));
        arrowLabel.setVisible(true);
        isAnimating = false;
    }

    private void stopAnimation() {
        if (textAnimation != null) {
            textAnimation.stop();
        }
    }

    public void close() {
        this.setVisible(false);
        this.setMouseTransparent(true);

        //Esegui il callback se è già stato impostato
        if (onCloseCallback != null)
            onCloseCallback.run();
    }
}
