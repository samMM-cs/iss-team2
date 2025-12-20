package com.game.model.battle;

import com.game.model.character.CharacterPG;

import java.util.ArrayList;
import java.util.List;

public class MagicAttackMove extends OffensiveMove {
    @Override
    public List<Integer> calculateDamage(CharacterPG user, List<CharacterPG> target) {
        List<Integer> res= new ArrayList<>(target.size());
        for (int i=0; i<target.size(); i++) {
            //This implementation doesn't use characters stats yet
            res.add(Integer.valueOf(move.getValue()));
        }
        return res;
    }

}
