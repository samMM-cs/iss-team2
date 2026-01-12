package com.game.model.story;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.game.model.GameState;
import com.game.model.SerializableConsumer;

public class Choice {
    private final StoryNode nextNode;
    private final String text;
    @JsonTypeInfo(use = Id.CLASS)
    private SerializableConsumer<GameState> choiceAftermath;

    @JsonCreator
    public Choice(@JsonProperty("nextNode") StoryNode nextNode, @JsonProperty("text") String text) {
        this.nextNode = nextNode;
        this.text = text;
    }

    @JsonCreator
    public Choice(
            @JsonProperty("nextNode") StoryNode nextNode,
            @JsonProperty("text") String text,
            SerializableConsumer<GameState> choiceAftermath) {
        this.nextNode = nextNode;
        this.text = text;
        this.choiceAftermath = choiceAftermath;
    }

    public StoryNode getNextNode() {
        return this.nextNode;
    }

    public String getText() {
        return this.text;
    }

    public SerializableConsumer<GameState> getChoiceAftermath() {
        return this.choiceAftermath;
    }
}
