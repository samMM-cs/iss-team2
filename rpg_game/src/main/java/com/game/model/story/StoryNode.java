package com.game.model.story;

import java.util.List;
import java.util.function.Predicate;

import com.game.model.GameState;

public class StoryNode {
    private String name;
    private List<String> dialogues;
    private List<Choice> choices;
    private Predicate<GameState> trigger;

    @Deprecated
    public StoryNode(String name, List<String> dialogues, List<Choice> choices) {
        this.name = name;
        this.dialogues = dialogues;
        this.choices = choices;
    }

    public StoryNode(StoryNodeBuilder sNodeBuilder) {
        this.name = sNodeBuilder.getName();
        this.dialogues = sNodeBuilder.getDialogues();
        this.choices = sNodeBuilder.getChoices();
        this.trigger = sNodeBuilder.getTrigger();
    }

    public String getName() {
        return name;
    }

    public List<String> getDialogues() {
        return dialogues;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public Predicate<GameState> getTrigger() {
        return trigger;
    }
}
