package com.game.model.battle;

import com.game.model.character.CharacterPG;
import java.util.List;

public interface ActionStrategy {
    public void doAction(CharacterPG user, List<CharacterPG> target);
}
