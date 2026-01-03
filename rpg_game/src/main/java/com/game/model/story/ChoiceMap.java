package com.game.model.story;

import java.util.EnumMap;

public class ChoiceMap {
    private final EnumMap<Choice, Boolean> flags;

    public ChoiceMap() {
        /*this.flags = Arrays.stream(Choice.values())
            .collect(Collectors.toMap(Function.identity(), choice -> false, (a,b) -> a, () -> new EnumMap<>(Choice.class)));*/
        this.flags= new EnumMap<>(Choice.class);
        for (Choice choice : Choice.values()) {
            flags.put(choice, false);
        }
    }

    public boolean getChoice(Choice choice) {
        return flags.get(choice);
    }

    public void setChoice(Choice choice, boolean flag) {
        this.flags.put(choice, flag);
    }
}
