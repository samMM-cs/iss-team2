package com.game.model.battle;

import com.game.model.character.CharacterPG;
import com.game.model.character.Enemy;
import com.game.model.character.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.game.model.GameState;

public class Battle {
    private GameState gameState;
    private List<Enemy> enemies = new ArrayList<>();
    private int turnIndex;
    private TurnStrategy turnStrategy;
    private RewardStrategy rewardStrategy;
    private List<Action> plannedActionList = new ArrayList<>();

    public Battle(Enemy enemy) {
        this.gameState = GameState.getInstance();
        this.turnIndex = 0;
        if (enemy != null) {
            if (!enemy.getJob().equals(Job.BOSS))
                this.enemies = enemy.clones(GameState.getInstance().getNPlayers());
            else
                this.enemies = List.of(enemy);
        }
        this.turnStrategy = new StaticSpeedTurn(gameState.getParty(), this.enemies);
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
    public String enemyAIString(int i) {
        return enemies.get(i).getCurrentMove()
                .get(new Random().nextInt(enemies.get(i).getCurrentMove().size()))
                .getName();
    }

    public ActionStrategy enemyAIActionStrategy(int i) {
        Move enemyMove = enemies.get(i).getCurrentMove()
                .get(new Random().nextInt(enemies.get(i).getCurrentMove().size()));
        return enemyMove.getType().createMove(enemyMove);
    }

    public BattleResult nextTurn() {
        this.turnStrategy.sortAction();
        TurnIterator it = turnStrategy.getTurnIterator();
        while (it.hasCharacters()) {
            CharacterPG character = it.nextCharacter();
            if (character.getCurrentStats().getHp() < 0) {
                it.remove();
            } else {
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
        boolean partyWiped = this.gameState.getParty().getMembers().stream()
                .allMatch(p -> p.getCurrentStats().getHp() <= 0);
        boolean enemiesDead = this.enemies.stream().allMatch(e -> e.getCurrentStats().getHp() <= 0);
        if (partyWiped)
            return BattleResult.PARTY_DEFEATED;
        if (enemiesDead)
            return BattleResult.PARTY_WON;
        return BattleResult.ONGOING;
    }

    public void assignRewards() {
        Reward reward = rewardStrategy.calculateRewards(enemies);
        reward.assignXP(this.gameState.getParty());
        reward.assignItem(this.gameState.getInventory());
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public TurnStrategy getTurnStrategy() {
        return turnStrategy;
    }

    public RewardStrategy getRewardStrategy() {
        return rewardStrategy;
    }

    public void setPlannedActionList(List<Action> plannedActionList) {
        this.plannedActionList.clear();
        this.plannedActionList.addAll(plannedActionList);
    }

    private Action getCurrentAction(CharacterPG character) {
        return plannedActionList.stream()
                .filter(action -> action.getUser().equals(character))
                .findFirst()
                .orElse(null);
    }
}