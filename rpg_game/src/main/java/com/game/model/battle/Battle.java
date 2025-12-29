package com.game.model.battle;

import com.game.model.character.CharacterPG;
import com.game.model.character.Enemy;

import com.game.model.GameState;

public class Battle {
    private GameState gameState;
    private Enemy enemy;
    private int turnIndex;
    private TurnStrategy turnStrategy;
    private RewardStrategy rewardStrategy;

    public Battle(Enemy enemy) {
        this.gameState = GameState.getInstance();
        this.turnIndex = 0;
        this.enemy = enemy;
        this.turnStrategy = new StaticSpeedTurn(gameState.getParty(), this.enemy);
        this.rewardStrategy = new StandardRewardStrategy();
    }

    public Battle(int turnIndex, TurnStrategy turnStrategy, RewardStrategy rewardStrategy) {
        this.gameState = GameState.getInstance();
        this.turnIndex = turnIndex;
        this.turnStrategy = turnStrategy;
        this.rewardStrategy = rewardStrategy;
    }

    public final int getTurnIndex() {
        return this.turnIndex;
    }

    public void nextTurn() {
        this.turnStrategy.sortAction();
        TurnIterator it = turnStrategy.getTurnIterator();
        while (it.hasCharacters()) {
            CharacterPG character = it.nextCharacter();
            if (character.getCurrentStats().getHp() > 0) {
                // PerformAction
            }
        }
        this.turnIndex++;
    }

    /**
     *
     * @return 0 if Battle is still ongoing, 1 if the party won, 2 if the party got
     *         wiped
     */
    public BattleResult isBattleOver() {
        boolean partyWiped = true;
        for (CharacterPG player : this.gameState.getParty().getMembers()) {
            if (player.getCurrentStats().getHp() > 0) {
                partyWiped = false;
            }
        }
        boolean enemydead = true;
        if (enemy.getCurrentStats().getHp() > 0)
            enemydead = false;
        /*
         * for (CharacterPG c : enemies) {
         * if (c.getCurrentStats().getHp() > 0) {
         * enemydead= false;
         * }
         * }
         */
        if (partyWiped)
            return BattleResult.PARTY_DEFEATED;
        if (enemydead)
            return BattleResult.PARTY_WON;
        return BattleResult.ONGOING;
    }

    public void endBattle() {
        BattleResult flag = this.isBattleOver();
        switch (flag) {
            case BattleResult.ONGOING:
                break;
            case BattleResult.PARTY_WON: {
                System.out.println("ggwp");
                // Notify controller
                this.assignRewards();
                break;
            }
            case BattleResult.PARTY_DEFEATED: {
                System.out.println("Party got wiped out");
                // Notify controller
                break;
            }
            default: {
                System.err.println("Error in logic: isBattleOver()");
                break;
            }
        }
    }

    public void assignRewards() {
        Reward reward = rewardStrategy.calculateRewards(enemy);
        reward.assignXP(this.gameState.getParty());
        reward.assignItem(this.gameState.getInventory());
    }

    public Enemy getEnemy() {
        return enemy;
    }
}