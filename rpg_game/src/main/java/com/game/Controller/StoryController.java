package com.game.controller;

import com.game.model.GameState;
import com.game.model.story.Choice;
import com.game.model.story.StoryNode;
import com.game.view.StoryView;

public class StoryController {
    private StoryNode storyNode;
    private StoryView view;

    public StoryController (StoryView view) {
        this.storyNode = view.getStoryNode();
        this.view = view;
    }

    public void enter() {
        if (storyNode.getTrigger().test(GameState.getInstance())) {
            //Show StoryView
        }
    }

    public void onChoice(String choiceText) {
        Choice chosenOne = storyNode.getChoices().stream()
            .filter(choice -> choice.getText().equals(choiceText))
            .findFirst()
            .orElse(null);
        updateCurrentStoryNode(chosenOne.getNextNode());
    }

    public void updateCurrentStoryNode(StoryNode newStoryNode) {
        GameState.getInstance().setCurrentStoryNode(newStoryNode);
    }
    
}
