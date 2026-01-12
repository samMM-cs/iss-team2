package com.game.model.battle;

import com.game.model.character.Enemy;
import com.game.model.character.Item;

import java.util.ArrayList;
import java.util.List;

public class StandardRewardStrategy implements RewardStrategy {
    @Override
    public Reward calculateRewards(List<Enemy> enemy) {
        if (enemy.isEmpty())
            return new Reward(0, new ArrayList<>());
        return new Reward(20 * enemy.get(0).getCurrentStats().getLevel() * enemy.size(), new ArrayList<Item>());
    }
}
