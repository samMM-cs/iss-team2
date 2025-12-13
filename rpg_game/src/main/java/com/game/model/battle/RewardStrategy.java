package com.game.model.battle;

import java.util.List;

import com.game.model.character.Enemy;

public interface RewardStrategy {
    public abstract Reward calculateRewards(List<Enemy> enemies);
}
