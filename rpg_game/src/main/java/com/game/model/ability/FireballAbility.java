package com.game.model.ability;

import com.game.model.character.Enemy;
import com.game.model.character.Player;

public class FireballAbility implements Ability {
    @Override
    public void apply(Player player,Enemy target) {
        System.out.println("Abilita' aggiunta a " + player);
        target.takeDamage(AbilityType.FIREBALL.getDamage());
    }
}
