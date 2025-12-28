package com.game.model.ability;

import com.game.model.character.Player;

public interface Ability {
    String getName();

    String getDescription();

    int getCost();

    int getId();

    void apply(Player player);
}
