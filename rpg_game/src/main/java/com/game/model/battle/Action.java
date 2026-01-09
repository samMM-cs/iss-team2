package com.game.model.battle;

import java.util.ArrayList;
import java.util.List;

import com.game.model.character.CharacterPG;

public class Action {
    private String action;
    private ActionStrategy action2;
    private CharacterPG user;
    private List<? extends CharacterPG> targets;
    
    public Action(ActionStrategy action, CharacterPG user, List<? extends CharacterPG> targets) {
        this.action2 = action;
        this.user = user;
        this.targets = targets;
    }

    public Action(String action, CharacterPG user, CharacterPG... targets) {
        this.action = action;
        this.user = user;
        this.targets = new ArrayList<CharacterPG>(List.of(targets));
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

    public String getAction() {
        return action;
    }

    public ActionStrategy getAction2() {
        return action2;
    }
}
