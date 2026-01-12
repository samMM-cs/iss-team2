package com.game.view;

import com.game.controller.StoryController;
import com.game.model.GameState;
import com.game.model.character.dialogue.Dialogue;
import com.game.model.story.Choice;
import com.game.model.story.StoryNode;

import javafx.scene.control.ListView;

public class StoryView {
    private final DialogueView dialogueView;
    private ListView<String> choices;
    private StoryController storyController;

    public StoryView(DialogueView dialogueView) {
        this.dialogueView = dialogueView;
        this.storyController = new StoryController(this);

        choices = new ListView<>();
        choices.setOrientation(javafx.geometry.Orientation.HORIZONTAL);
        choices.setStyle("-fx-font-size: 14px;");
        choices.setManaged(false);
        choices.setVisible(false);
        choices.setOnMouseClicked(e -> storyController.onChoice(choices.getSelectionModel().getSelectedItem()));
    }

    public void showDialogue(StoryNode node, GameState gameState) {
        dialogueView.showDialogue(new Dialogue(node.getDialogues()));
    }

    public void showChoices(StoryNode node) {
        initChoices(node);
        choices.setVisible(true);
        choices.setManaged(true);
    }

    public void hideChoices() {
        choices.setManaged(false);
        choices.setVisible(false);
    }

    private void initChoices(StoryNode node) {
        choices.getItems().clear();
        choices.getItems().addAll(
            node.getChoices().stream()
                .map(Choice::getText)
                .toList()
        );
        resizeListViewToFitItems(choices);
    }

    private void resizeListViewToFitItems(ListView<?> list) {
        list.setFixedCellSize(36);
        double height = list.getItems().size() * list.getFixedCellSize() + 4;
        list.setPrefHeight(height);
        list.setMaxHeight(height);
    }

    public DialogueView getDialogueView() {
        return dialogueView;
    }
}
