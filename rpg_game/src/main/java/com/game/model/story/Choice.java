package com.game.model.story;

public class Choice {
    private final StoryNode nextNode;
    private final String text;

    
    public Choice(StoryNode nextNode, String text) {
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
