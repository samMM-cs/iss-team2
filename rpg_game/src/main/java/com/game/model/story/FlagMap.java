package com.game.model.story;

import java.util.EnumMap;

public class FlagMap {
    private final EnumMap<Flag, Boolean> flags;

    public FlagMap() {
        /*this.flags = Arrays.stream(Flag.values())
            .collect(Collectors.toMap(Function.identity(), Flag -> false, (a,b) -> a, () -> new EnumMap<>(Flag.class)));*/
        this.flags= new EnumMap<>(Flag.class);
        for (Flag Flag : Flag.values()) {
            flags.put(Flag, false);
        }
    }

    public boolean getFlag(Flag Flag) {
        return flags.get(Flag);
    }

    public void setFlag(Flag Flag, boolean flag) {
        this.flags.put(Flag, flag);
    }
}
