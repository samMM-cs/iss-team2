package com.game.model.story;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.model.GameState;
import com.game.model.SerializableConsumer;
import com.game.model.SerializablePredicate;

public class StoryNodeLoader {
    private static final String PATH = "/dialogues/dialogue.json";
    private static final TypeReference<SerializablePredicate<GameState>> triggerType = new TypeReference<>() {
    };
    private static final TypeReference<SerializableConsumer<GameState>> afterType = new TypeReference<>() {
    };

    public static StoryNode load() {
        return loadFromJSON(PATH);
    }

    private static StoryNode loadFromJSON(String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            var is = StoryNode.class.getResource(PATH);
            JsonNode root = mapper.readTree(is).get("story");
            String startNodeName = root.get("startNode").asText();
            JsonNode nodes = root.get("nodes");
            Map<String, StoryNode> nodeMap = new HashMap<>();
            for (JsonNode jsonNode : nodes) {
                String name = jsonNode.get("name").asText();
                List<String> dialogues = mapper.readerForListOf(String.class).readValue(jsonNode.get("dialogues"));
                SerializablePredicate<GameState> trigger = mapper.convertValue(jsonNode.get("trigger"), triggerType);
                nodeMap.put(name, new StoryNode(name, dialogues, new ArrayList<>(), trigger));
            }
            for (JsonNode jsonNode : nodes) {
                String name = jsonNode.get("name").asText();
                StoryNode node = nodeMap.get(name);
                JsonNode choices = jsonNode.get("choices");
                for (JsonNode choiceJson : choices) {
                    String text = choiceJson.get("text").asText();
                    StoryNode next = nodeMap.get(choiceJson.get("nextNode").asText());
                    SerializableConsumer<GameState> aftermath = mapper.convertValue(choiceJson.get("choiceAftermath"),
                            afterType);
                    node.addChoice(new Choice(next, text, aftermath));
                }
            }
            return nodeMap.get(startNodeName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
