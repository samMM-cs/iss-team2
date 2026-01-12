package com.game.view;

import com.game.model.GameState;
import com.game.model.character.dialogue.Dialogue;
import com.game.model.story.StoryNode;

public class StoryView {
    private final DialogueView dialogueView;

    public StoryView(DialogueView dialogueView) {
        this.dialogueView = dialogueView;
    }

    public void show(StoryNode node, GameState gameState) {
        dialogueView.showDialogue(new Dialogue(node.getDialogues()));
    }

    public DialogueView getDialogueView() {
        return dialogueView;
    }
}
