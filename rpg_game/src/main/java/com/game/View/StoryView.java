package com.game.view;

import com.game.model.character.dialogue.Dialogue;
import com.game.model.story.StoryNode;

public class StoryView {
    private StoryNode storyNode;

    public StoryView (StoryNode storyNode) {
        this.storyNode = storyNode;
    }

    public void show() {
        DialogueView dialogueView = new DialogueView();
        dialogueView.showDialogue(new Dialogue(storyNode.getDialogues()));
    }

    public StoryNode getStoryNode() {
        return storyNode;
    }
}
