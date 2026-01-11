package com.game.model.story;

import java.util.List;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.game.model.GameState;
import com.game.model.SerializablePredicate;

public class StoryNode {
    private String name;
    private List<String> dialogues;
    private List<Choice> choices;
    @JsonTypeInfo(use = Id.CLASS)
    private SerializablePredicate<GameState> trigger;

    @JsonCreator
    public StoryNode(
            @JsonProperty("name") String name,
            @JsonProperty("dialogues") List<String> dialogues,
            @JsonProperty("choices") List<Choice> choices) {
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

    public void addChoice(Choice choice) {
        this.choices.add(choice);
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
