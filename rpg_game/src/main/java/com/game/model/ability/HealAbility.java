package com.game.model.ability;

import com.game.model.character.Player;
import com.game.model.character.Enemy;

public class HealAbility implements Ability {
    @Override
    public void apply(Player player, Enemy target) {
        player.heal(AbilityType.HEAL.getHeal());
    }
}