package com.game.model.ability;

import com.game.model.character.CharacterPG;

public interface Ability {
    String getName();

    void apply(CharacterPG character);
}
