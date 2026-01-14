package com.game.controller;

import com.game.model.GameState;
import com.game.model.story.Choice;
import com.game.model.story.StoryNode;

public class StoryController {
    private StoryNode storyNode;
    private final GameState gameState;

    public StoryController() {
        this.gameState = GameState.getInstance();
        this.storyNode = gameState.getCurrentStoryNode();
    }

    public void enter() {
        StoryNode node = gameState.getCurrentStoryNode();
        if (node != null && node.getTrigger().test(GameState.getInstance())) {
            ViewManager.getInstance().showStory(this);
        }
    }

    public void onChoice(String choiceText) {
        storyNode = gameState.getCurrentStoryNode();
        Choice chosenOne = storyNode.getChoices().stream()
                .filter(choice -> choice.getText().equals(choiceText))
                .findFirst()
                .orElse(null);
        System.out.println("CHIAMATO onChoice(" + choiceText + "): " + storyNode.getChoices());
        if (chosenOne != null) {
            chosenOne.getChoiceAftermath().accept(GameState.getInstance());
            gameState.setCurrentStoryNode(chosenOne.getNextNode());
        }
    }
}