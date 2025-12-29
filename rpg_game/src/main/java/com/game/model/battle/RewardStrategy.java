package com.game.model.battle;


import com.game.model.character.Enemy;

public interface RewardStrategy {
    public abstract Reward calculateRewards(Enemy enemy);
}
