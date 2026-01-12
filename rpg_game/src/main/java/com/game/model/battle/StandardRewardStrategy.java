package com.game.model.battle;

import com.game.model.character.Enemy;
import com.game.model.character.Item;

import java.util.ArrayList;

public class StandardRewardStrategy implements RewardStrategy {
    @Override
    public Reward calculateRewards(Enemy enemy) {
        return new Reward(20 * enemy.getCurrentStats().getLevel(), new ArrayList<Item>());
    }
}
