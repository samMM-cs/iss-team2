package com.game.model.ability;

import com.game.model.character.Player;
import com.game.model.character.Enemy;

public interface Ability {
    void apply(Player player,Enemy target);
}
