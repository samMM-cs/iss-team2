package com.game.model.battle;


import com.game.model.character.Enemy;
import com.game.model.character.Item;

import java.util.ArrayList;

public class StandardRewardStrategy implements RewardStrategy {
    @Override
    public Reward calculateRewards(Enemy enemy) {
        // Logic has to be implemented when database is ready
        return new Reward(0, new ArrayList<Item>());
    }
}
