package com.game.model.story;

import java.util.ArrayList;
import java.util.List;

import com.game.model.GameState;
import com.game.model.SerializablePredicate;

public class StoryNodeBuilder {
    private String name;
    private List<String> dialogues = new ArrayList<>();
    private List<Choice> choices = new ArrayList<>();
    private SerializablePredicate<GameState> trigger = new SerializablePredicate.ConstantTrue<GameState>();

    public StoryNodeBuilder() {
    }

    public StoryNodeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StoryNodeBuilder addDialogue(String dialogue) {
        this.dialogues.add(dialogue);
        return this;
    }

    public StoryNodeBuilder addChoice(Choice choice) {
        this.choices.add(choice);
        return this;
    }

    public StoryNodeBuilder setDialogues(List<String> dialogues) {
        this.dialogues = dialogues;
        return this;
    }

    public StoryNodeBuilder setChoice(List<Choice> choices) {
        this.choices = choices;
        return this;
    }

    public StoryNodeBuilder setTrigger(SerializablePredicate<GameState> trigger) {
        this.trigger = trigger;
        return this;
    }

    public StoryNode buildStoryNode() {
        return new StoryNode(this);
    }

    public String getName() {
        return this.name;
    }

    public List<String> getDialogues() {
        return this.dialogues;
    }

    public List<Choice> getChoices() {
        return this.choices;
    }

    public SerializablePredicate<GameState> getTrigger() {
        return this.trigger;
    }
}
