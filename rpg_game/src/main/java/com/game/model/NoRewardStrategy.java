package com.game.model;

import java.util.List;
import java.util.ArrayList;

public class NoRewardStrategy implements RewardStrategy {
    @Override
    public Reward calculateRewards(List<Enemy> enemies) {
        return new Reward(0, new ArrayList<Item>());
    }
}