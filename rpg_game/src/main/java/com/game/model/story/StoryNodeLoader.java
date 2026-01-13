package com.game.model.story;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.game.model.GameState;
import com.game.model.SerializableConsumer;
import com.game.model.SerializablePredicate;

//Basically the factory of a StoryNode Tree
public class StoryNodeLoader {
    public static StoryNode load() {
        return loadFromJSON("/dialogues/dialogue.json");
    }
    
    private static StoryNode loadFromJSON(String path) {
        try {
            String content = Files.readString(Paths.get(path));
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    /*public static StoryNode load() {
        StoryNode start = new StoryNodeBuilder().setName("start")
                .addDialogue("This is the starting point of the story")
                .setTrigger(new SerializablePredicate.ConstantTrue<GameState>())
                .buildStoryNode();

        StoryNode secondLevel = new StoryNodeBuilder().setName("second level")
                .addDialogue("You are in the second level of the story")
                .addChoice(new Choice(null, "Your story has ended", new SerializableConsumer.DoNothing<GameState>()))
                // .setTrigger(gs -> gs.getMapInd() == 1)
                .setTrigger(new SecondLevelTrigger())
                .buildStoryNode();

        start.addChoice(new Choice(secondLevel, "Go to next level"));

        return start;
    }*/

    private final static class SecondLevelTrigger implements SerializablePredicate<GameState> {

        @Override
        public boolean test(GameState gs) {
            return gs.getMapInd() == 1;
        }

    }
}
