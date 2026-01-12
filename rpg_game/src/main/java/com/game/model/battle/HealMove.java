package com.game.model.battle;

import com.game.model.character.CharacterPG;
import com.game.model.character.Stats;

import java.util.ArrayList;
import java.util.List;

public class HealMove extends MoveAction {
    public HealMove(Move move) {
        this.move = move;
    }

    @Override
    public void doAction(CharacterPG user, List<? extends CharacterPG> target) {
        user.getCurrentStats().incHealCount();
        List<Integer> values = calculateHeal(user, target);
        for (int i = 0; i < target.size(); i++) {
            target.get(i).heal(values.get(i));
        }
    }

    public List<Integer> calculateHeal(CharacterPG user, List<? extends CharacterPG> target) {
        List<Integer> res = new ArrayList<>(target.size());
        for (int i = 0; i < target.size(); i++) {
            Stats userStats = user.getCurrentStats();
            res.add((int) (move.getValue()
                    * Math.pow(1.1, userStats.getLevel() - 1)
                    * userStats.getHealCount()));
        }
        return res;
    }
}
