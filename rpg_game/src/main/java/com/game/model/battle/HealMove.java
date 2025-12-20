package com.game.model.battle;

import com.game.model.character.CharacterPG;

import java.util.ArrayList;
import java.util.List;

public class HealMove extends MoveAction {
    public HealMove(Move move) {
        this.move= move;
    }

    @Override
    public void doAction (CharacterPG user, List<CharacterPG> target) {
        List<Integer> values= calculateHeal(user, target);
        for (int i=0; i<target.size(); i++) {
            target.get(i).heal(values.get(i));
        }
    }

    public List<Integer> calculateHeal(CharacterPG user, List<CharacterPG> target) {
        List<Integer> res= new ArrayList<>(target.size());
        for (int i=0; i<target.size(); i++) {
            res.add(move.getValue());
        }
        return res;
    }
}
