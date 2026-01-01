package com.game.model.battle;

import com.game.model.character.CharacterPG;
import com.game.model.character.Enemy;

import java.util.List;

import com.game.model.GameState;

public class Battle {
    private GameState gameState;
    private Enemy enemy;
    private int turnIndex;
    private TurnStrategy turnStrategy;
    private RewardStrategy rewardStrategy;
    private List<Action> plannedActionList;

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

    public BattleResult nextTurn() {
        this.turnStrategy.sortAction();
        TurnIterator it = turnStrategy.getTurnIterator();
        while (it.hasCharacters()) {
            CharacterPG character = it.nextCharacter();
            if (character.getCurrentStats().getHp() > 0) {
                Action action = getCurrentAction(character);
                if (action != null) {
                    System.out.println("Azione di: " + character);
                    action.execute();
                    if (isBattleOver() != BattleResult.ONGOING) {
                        return isBattleOver();
                    }
                }
            }
        }
        this.turnIndex++;
        return BattleResult.ONGOING;
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

    public void assignRewards() {
        Reward reward = rewardStrategy.calculateRewards(enemy);
        reward.assignXP(this.gameState.getParty());
        reward.assignItem(this.gameState.getInventory());
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setPlannedActionList(List<Action> plannedActionList) {
        this.plannedActionList = plannedActionList;
    }

    private Action getCurrentAction(CharacterPG character) {
        for (Action action : plannedActionList) {
            if (action.getUser().equals(character)) {
                return action;
            }
        }
        return null;
    }
}