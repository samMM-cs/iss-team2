package com.game.model.battle;

import java.util.ArrayList;
import java.util.List;

import com.game.model.character.CharacterPG;

public class Action {
    private String action;
    private ActionStrategy action2;
    private CharacterPG user;
    private List<CharacterPG> targets;
    
    public Action(ActionStrategy action, CharacterPG user, List<CharacterPG> targets) {
        this.action2 = action;
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
        if (action2 != null) {
            action2.doAction(user, targets);
        } else if (action != null) {
            MoveRegistry.getMoveRegistry().getMoveActionStrategy(action).doAction(user, targets);
        }
    }

    public CharacterPG getUser() {
        return this.user;
    }
}
