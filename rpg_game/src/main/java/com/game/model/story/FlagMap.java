package com.game.model.story;

import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class FlagMap {
    private final EnumMap<Flag, Boolean> flags;

    public FlagMap() {
        /*
         * this.flags = Arrays.stream(Flag.values())
         * .collect(Collectors.toMap(Function.identity(), Flag -> false, (a,b) -> a, ()
         * -> new EnumMap<>(Flag.class)));
         */
        this.flags = new EnumMap<>(Flag.class);
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

    @JsonCreator
    public FlagMap(List<Flag> flags) {
        this();
        for (Flag flag : flags) {
            this.flags.put(flag, true);
        }
    }

    @JsonValue
    // only save set flags in json
    public List<Flag> getFlags() {
        return flags.entrySet().stream()
                .filter(Entry::getValue)
                .map(Entry::getKey)
                .toList();
    }

}
