package com.game.view;

import com.game.controller.StoryController;
import com.game.model.GameState;
import com.game.model.character.dialogue.Dialogue;
import com.game.model.story.Choice;
import com.game.model.story.StoryNode;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.List;

public class StoryView extends VBox {
    private final DialogueView dialogueView;
    private VBox choicesBox;
    private StoryController storyController;

    public StoryView(DialogueView dialogueView, StoryController controller) {
        this.dialogueView = dialogueView;
        this.storyController = controller;

        choicesBox = new VBox();
        choicesBox.setSpacing(10);
        choicesBox.setVisible(false);
        choicesBox.setManaged(false);

        this.setSpacing(20);
        this.setAlignment(Pos.BOTTOM_CENTER);
        this.getChildren().addAll(dialogueView, choicesBox);

    }

    // Mostra i dialoghi nel dialogueView
    public void showDialogue(StoryNode node, GameState gameState) {
        dialogueView.showDialogue(new Dialogue(node.getDialogues()));
    }

    // Mostra le scelte
    public void showChoice(StoryNode node) {
        choicesBox.getChildren().clear();
        List<Choice> nodeChoice = node.getChoices();
        for (int i = 0; i < nodeChoice.size(); i++) {
            Choice choice = nodeChoice.get(i);
            Button btn = new Button(choice.getText());
            btn.setStyle(
                    "-fx-background-color: rgba(34, 34, 34, 0.9);" +
                            "-fx-text-fill: #00FF41;" + // Verde Matrix/Retro
                            "-fx-font-family: 'Monospaced';" +
                            "-fx-font-size: 16px;" +
                            "-fx-border-color: #00FF41;" +
                            "-fx-border-width: 1;" +
                            "-fx-border-radius: 5;" +
                            "-fx-background-radius: 5;" +
                            "-fx-padding: 10 20;" +
                            "-fx-cursor: hand;");
            btn.setOnMouseEntered(e -> btn.setStyle(
                    "-fx-background-color: #00FF41;" +
                            "-fx-text-fill: black;" +
                            "-fx-font-family: 'Monospaced';" +
                            "-fx-font-size: 16px;" +
                            "-fx-border-color: #00FF41;" +
                            "-fx-border-radius: 5;" +
                            "-fx-padding: 10 20;"));

            btn.setOnMouseExited(e -> btn.setStyle(
                    "-fx-background-color: rgba(34, 34, 34, 0.9);" +
                            "-fx-text-fill: #00FF41;" +
                            "-fx-font-family: 'Monospaced';" +
                            "-fx-font-size: 16px;" +
                            "-fx-border-color: #00FF41;" +
                            "-fx-border-radius: 5;" +
                            "-fx-padding: 10 20;"));
            btn.setMaxWidth(500);
            btn.setAlignment(Pos.CENTER);
            btn.setOpacity(0);
            btn.setOnAction(e -> {
                storyController.onChoice(choice.getText());
                hideChoices();
            });
            choicesBox.getChildren().add(btn);
            FadeTransition ft = new FadeTransition(Duration.millis(500), btn);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setDelay(Duration.millis(200 * i)); // Appaiono uno dopo l'altro
            ft.play();
        }
        choicesBox.setVisible(true);
        choicesBox.setManaged(true);
    }

    public void show(StoryNode node, GameState gameState) {
        hideChoices();
        gameState.showCurrentStory();

        dialogueView.setOnCloseClick(() -> {
            if (node.getChoices() != null && !node.getChoices().isEmpty())
                showChoice(node);
        });
        showDialogue(node, gameState);
    }

    public void hideChoices() {
        choicesBox.setManaged(false);
        choicesBox.setVisible(false);
        choicesBox.setMouseTransparent(false);
    }
}
