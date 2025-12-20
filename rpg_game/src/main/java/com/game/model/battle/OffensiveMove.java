package com.game.model.battle;

import java.util.List;

import com.game.model.character.CharacterPG;

public abstract class OffensiveMove extends MoveAction {
    @Override
    public void doAction (CharacterPG user, List<CharacterPG> target) {
        List<Integer> damage= calculateDamage(user, target);
        /*
         * This implementation avoids the use of a map which isn't recommended
         * because some targets in the List can be equals for method equals
         */
        for (int i=0; i<target.size(); i++) {
            target.get(i).takeDamage(damage.get(i));
        }
    }

    public abstract List<Integer> calculateDamage(CharacterPG user, List<CharacterPG> target);
}
