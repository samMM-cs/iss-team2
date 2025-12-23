package com.game.model.battle;

import com.game.model.character.CharacterPG;
import com.game.model.GameState;

public class Battle{
    private GameState gameState;
    private int turnIndex;
    private TurnStrategy turnStrategy;
    private RewardStrategy rewardStrategy;

    public Battle(GameState gameState,
        int turnIndex,
        TurnStrategy turnStrategy,
        RewardStrategy rewardStrategy) {
        this.gameState= gameState;
        this.turnIndex = turnIndex;
    }

    public final int getTurnIndex() {
        return this.turnIndex;
    }

    public boolean isBattleOver() {
        for (CharacterPG player : this.gameState.getParty().getMembers()) {
            if (player.getCurrentStats().getHp() == 0) {
                return true;
            }
        }
        for (CharacterPG c : this.gameState.getEnemies()) {
            if (c.getCurrentStats().getHp() == 0) {
                return true;
            }
        }
        return false;
    }

    public void endBattle() {

    }

    public void assignRewards() {
        rewardStrategy.calculateRewards(this.gameState.getEnemies());
    }
}