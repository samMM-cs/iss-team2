package com.game.model;

import java.util.List;
import java.util.ArrayList;

public class StandardRewardStrategy implements RewardStrategy {
    @Override
    public Reward calculateRewards(List<Enemy> enemies) {
        //Logic has to be implemented when database is ready
        return new Reward(0, new ArrayList<Item>());
    }
}
