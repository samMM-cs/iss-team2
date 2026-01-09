package com.game.view;

import com.game.model.story.StoryNode;

public class StoryView {
    private StoryNode storyNode;

    public StoryView (StoryNode storyNode) {
        this.storyNode = storyNode;
    }

    public StoryNode getStoryNode() {
        return storyNode;
    }
}
