package com.game.model.character.dialogue;

import java.util.List;

public class Dialogue {
    private final List<String> lines;
    private int currentIndex = 0;

    public Dialogue(List<String> lines) {
        this.lines = lines;
    }

    public String getNextLine() {
        if (currentIndex < lines.size())
            return lines.get(currentIndex++);
        else
            return null;
    }

    public void reset() {
        currentIndex = 0;
    }

    public boolean isFinished() {
        return currentIndex >= lines.size();
    }

    public final List<String> getLines() {
        return this.lines;
    }
}
