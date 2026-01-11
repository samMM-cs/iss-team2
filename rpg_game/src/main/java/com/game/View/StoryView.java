package com.game.view;

import com.game.model.character.dialogue.Dialogue;
import com.game.model.story.StoryNode;

public class StoryView {
    private StoryNode storyNode;
    public DialogueView dialogueView;

    public StoryView(StoryNode storyNode) {
        this.storyNode = storyNode;
    }

    public void show() {
        dialogueView = new DialogueView();
        dialogueView.showDialogue(new Dialogue(storyNode.getDialogues()));
    }

    public StoryNode getStoryNode() {
        return storyNode;
    }

    public void setStoryNode(StoryNode storyNode) {
        this.storyNode = storyNode;
    }

    public DialogueView getDialogueView() {
        return dialogueView;
    }
}
