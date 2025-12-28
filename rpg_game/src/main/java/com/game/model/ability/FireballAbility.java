package com.game.model.ability;

import com.game.model.character.Player;

public class FireballAbility implements Ability {

    @Override
    public int getId() {
        return 1;
    }
    @Override
    public String getName() {
        return "Fireball";
    }

    @Override
    public String getDescription() {
        return "Infligge danni da fuoco";
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public void apply(Player player) {
        System.out.println("Abilita' aggiunta a " + player);
        player.getCurrentStats().addAbility(this);
    }
}
