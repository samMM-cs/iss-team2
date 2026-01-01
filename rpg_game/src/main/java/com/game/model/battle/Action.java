package com.game.model.battle;

import java.util.ArrayList;
import java.util.List;

import com.game.model.character.CharacterPG;

public class Action {
    private String action;
    private CharacterPG user;
    private List<CharacterPG> targets;
    
    public Action(String action, CharacterPG user, List<CharacterPG> targets) {
        this.action = action;
        this.user = user;
        this.targets = targets;
    }

    public Action(String action, CharacterPG user, CharacterPG... targets) {
        this.action = action;
        this.user = user;
        this.targets = new ArrayList<>();
        for (CharacterPG target : targets) {
            this.targets.add(target);
        }
    }

    public void execute() {
        MoveRegistry.getMoveRegistry()
            .getMoveActionStrategy(action)
            .doAction(user, targets);
    }

    public CharacterPG getUser() {
        return this.user;
    }
}
