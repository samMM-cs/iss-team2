package com.game.controller;

import com.game.model.GameState;
import com.game.model.story.Choice;
import com.game.model.story.StoryNode;
import com.game.view.StoryView;

public class StoryController {
    private final StoryView view;
    private final StoryNode storyNode;
    private final GameState gameState;

    public StoryController(StoryView view) {
        this.view = view;
        this.gameState = GameState.getInstance();
        this.storyNode = gameState.getCurrentStoryNode();
    }

    public void enter() {
        StoryNode node = gameState.getCurrentStoryNode();
        if (node != null && node.getTrigger().test(GameState.getInstance())) {
            view.showDialogue(node, gameState);
            view.showChoices(node);
        }
    }

    public void onChoice(String choiceText) {
        Choice chosenOne = storyNode.getChoices().stream()
                .filter(choice -> choice.getText().equals(choiceText))
                .findFirst()
                .orElse(null);
        if (chosenOne != null) {
            chosenOne.getChoiceAftermath().accept(GameState.getInstance());
            gameState.setCurrentStoryNode(chosenOne.getNextNode());
        }
    }
}