package com.game.model;

import java.util.List;

public interface RewardStrategy {
    public abstract Reward calculateRewards(List<Enemy> enemies);
}
