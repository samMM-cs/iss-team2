package com.game.model.story;

//Basically the factory of a StoryNode Tree
public class StoryNodeLoader {
    public static StoryNode load() {
        StoryNode start = new StoryNodeBuilder().setName("start")
            .addDialogue("This is the starting point of the story")
            .setTrigger(gs -> true)
            .buildStoryNode();
        
        StoryNode secondLevel = new StoryNodeBuilder().setName("second level")
            .addDialogue("You are in the second level of the story")
            .addChoice(new Choice(null, "Your story has ended"))
            .setTrigger(gs -> gs.getMapInd() == 1)
            .buildStoryNode();

        /* ---------------------------------------------------------------------------- */

        start.addChoice(new Choice(secondLevel, "Go to next level"));

        return start;
    }
}
