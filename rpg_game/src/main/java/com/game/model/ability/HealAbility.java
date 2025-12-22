package com.game.model.ability;

import com.game.model.character.CharacterPG;

public class HealAbility implements Ability {
    @Override
    public String getName() {
        return "Heal";
    }

    @Override
    public void apply(CharacterPG c) {
        System.out.println("Abilita' aggiunta a " + c);
    }
}