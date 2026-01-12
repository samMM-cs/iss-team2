package com.game.model.battle;

import com.game.model.character.CharacterPG;
import com.game.model.character.Stats;

import java.util.ArrayList;
import java.util.List;

public class MagicAttackMove extends OffensiveMove {
    public MagicAttackMove(Move move) {
        this.move = move;
    }

    @Override
    public List<Integer> calculateDamage(CharacterPG user, List<? extends CharacterPG> target) {
        List<Integer> res = new ArrayList<>(target.size());
        for (int i = 0; i < target.size(); i++) {
            Stats userStats = user.getCurrentStats();
            res.add((int) (move.getValue()
                    * Math.pow(1.1, userStats.getLevel() - 1)
                    * userStats.getDmgMult()
                    * userStats.getAttack()
                    / target.get(i).getCurrentStats().getDefense()));
        }
        return res;
    }

}
