package com.game.model.battle;

import com.game.model.character.CharacterPG;
import java.util.List;

public abstract class MoveAction implements ActionStrategy {
    public Move move;
    
    @Override
    public abstract void doAction(CharacterPG user, List<CharacterPG> target);
}
