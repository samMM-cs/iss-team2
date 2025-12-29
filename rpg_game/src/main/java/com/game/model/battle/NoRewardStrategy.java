package com.game.model.battle;

import com.game.model.character.Enemy;
import com.game.model.character.Item;

import java.util.ArrayList;

public class NoRewardStrategy implements RewardStrategy {
    @Override
    public Reward calculateRewards(Enemy enemy) {
        return new Reward(0, new ArrayList<Item>());
    }
}