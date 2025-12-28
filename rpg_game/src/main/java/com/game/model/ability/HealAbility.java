package com.game.model.ability;

import com.game.model.character.Player;

public class HealAbility implements Ability {

    @Override
    public int getId() {
        return 2;
    }
    @Override
    public String getName() {
        return "Heal";
    }

    @Override
    public String getDescription() {
        return "Cura";
    }

    @Override
    public int getCost() {
        return 600;
    }

    @Override
    public void apply(Player player) {
        System.out.println("Abilita' aggiunta a " + player);
        player.getCurrentStats().addAbility(this);
    }
}