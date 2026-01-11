package com.game.model.battle;

import com.game.model.character.CharacterPG;
import com.game.model.character.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.game.model.GameState;

public class Battle {
    private GameState gameState;
    private Enemy enemy;
    private int turnIndex;
    private TurnStrategy turnStrategy;
    private RewardStrategy rewardStrategy;
    private List<Action> plannedActionList = new ArrayList<>();

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

    // Minimal enemyAI
    public String enemyAIString() {
        return enemy.getCurrentMove().get(new Random().nextInt(enemy.getCurrentMove().size())).getName();
    }

    public ActionStrategy enemyAIActionStrategy() {
        Move enemyMove = enemy.getCurrentMove().get(new Random().nextInt(enemy.getCurrentMove().size()));
        return enemyMove.getType().createMove(enemyMove);

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
                    BattleResult flag = isBattleOver();
                    if (flag != BattleResult.ONGOING) {
                        return flag;
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

    public TurnStrategy getTurnStrategy() {
        return turnStrategy;
    }

    public RewardStrategy getRewardStrategy() {
        return rewardStrategy;
    }

    public void setPlannedActionList(List<Action> plannedActionList) {
        // this.plannedActionList = plannedActionList;
        this.plannedActionList.clear();
        this.plannedActionList.addAll(plannedActionList);
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