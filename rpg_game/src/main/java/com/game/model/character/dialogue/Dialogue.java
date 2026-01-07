package com.game.model.character.dialogue;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Dialogue {
    private final List<String> lines;
    private int currentIndex = 0;

    @JsonCreator
    public Dialogue(@JsonProperty("lines") List<String> lines) {
        this.lines = lines;
    }

    @JsonIgnore
    public String getNextLine() {
        if (currentIndex < lines.size())
            return lines.get(currentIndex++);
        else
            return null;
    }

    public void reset() {
        currentIndex = 0;
    }

    @JsonIgnore
    public boolean isFinished() {
        return currentIndex >= lines.size();
    }

    public final List<String> getLines() {
        return this.lines;
    }
}
