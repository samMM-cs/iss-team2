package com.game.model.story;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Choice {
    private final StoryNode nextNode;
    private final String text;

    @JsonCreator
    public Choice(@JsonProperty("nextNode") StoryNode nextNode, @JsonProperty("text") String text) {
        this.nextNode = nextNode;
        this.text = text;
    }

    public StoryNode getNextNode() {
        return this.nextNode;
    }

    public String getText() {
        return this.text;
    }
}
