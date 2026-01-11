package com.game.model.battle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.game.model.character.Enemy;
import com.game.model.character.CharacterPG;
import com.game.model.character.Job;
import com.game.model.map.Map1;
import com.game.model.map.Map2;
import com.game.model.map.Map3;
import com.game.model.story.FlagMap;
import com.game.model.GameState;
import com.game.model.WorldPosition;
import com.game.model.character.Stats;

import java.lang.reflect.Field;
import java.util.List;

public class BattleTest {
    Enemy enemy;
    TurnStrategy turnStrategy;
    RewardStrategy rewardStrategy;
    Battle battle;
    GameState gameState;
    List<Move> moves;
    int turnIndex;

    @BeforeEach
    void setup() {
        enemy = mock(Enemy.class);
        rewardStrategy = mock(StandardRewardStrategy.class);
        gameState = createGameState();
        moves = MoveReader.readMove("/battle/moves.json");
        turnStrategy = mock(StaticSpeedTurn.class);
        turnIndex = 0;
    }

    // T1
    @Test
    void battleTestWithValidEnemy() {
        battle = new Battle(enemy);
        assertNotNull(battle);
    }

    // T2
    @Test
    void battleTestWithNoValidEnemy() {
        battle = new Battle(null);
        assertNull(battle.getEnemy());
    }

    // T3
    @Test
    void battleTestValidInput() {
        battle = new Battle(2, turnStrategy, rewardStrategy);
        assertNotNull(battle.getTurnStrategy());
        assertNotNull(battle.getRewardStrategy());
    }

    // T4
    @Test
    void battleTestNotValidInput() {
        battle = new Battle(0, null, null);
        assertNull(battle.getTurnStrategy());
        assertNull(battle.getRewardStrategy());
    }

    // T5
    @Test
    void test_enemyAIString() {
        Move m1 = new Move();
        m1.setName("Attacco");
        Move m2 = new Move();
        m2.setName("Difesa");
        when(enemy.getCurrentMove()).thenReturn(moves);

        battle = new Battle(enemy);
        String move = battle.enemyAIString();
        assertNotNull(move);
        assertTrue(moves.stream().map(Move::getName).toList().contains(move));
    }

    // T6
    @Test
    void test_enemyAIActionStrategy() {
        Move m1 = new Move();
        m1.setName("Attacco");
        Move m2 = new Move();
        m2.setName("Difesa");
        when(enemy.getCurrentMove()).thenReturn(moves);

        battle = new Battle(enemy);
        ActionStrategy action = battle.enemyAIActionStrategy();
        assertNotNull(action);
    }

    //T7
    @Test
    void nextTurn_testAllPlayerAlive() {
        GameState gameState = GameState.getInstance();

        //Enemy
        Stats stats = mock(Stats.class);
        when(stats.getSpeed()).thenReturn(10);
        when(stats.getHp()).thenReturn(100);
        enemy.setCurrentStats(stats);

        //Player
        for (CharacterPG c : gameState.getParty().getMembers()) {
            Stats characterStats = mock(Stats.class);
            when(characterStats.getSpeed()).thenReturn(6);
            when(characterStats.getHp()).thenReturn(100);
            c.setCurrentStats(characterStats);
        }
        battle = new Battle(enemy);
        BattleResult result = battle.nextTurn();

        assertNotNull(result);
        assertEquals(BattleResult.ONGOING, result);
        assertEquals(1, turnIndex);
    }
    

    //T10
    @Test
    void test_AssignRewards() throws Exception{
        GameState gameState = GameState.getInstance();

        Reward reward = mock(Reward.class);
        RewardStrategy rewardStrategy = mock(RewardStrategy.class);

        Enemy enemy2 = mock(Enemy.class);
        when(rewardStrategy.calculateRewards(enemy2)).thenReturn(reward);

        battle = new Battle(enemy2);

        Field field = Battle.class.getDeclaredField("rewardStrategy");
        field.setAccessible(true);
        field.set(battle,rewardStrategy);
        battle.assignRewards();

        verify(rewardStrategy).calculateRewards(enemy2);
        verify(reward).assignXP(gameState.getParty());

    }
    private GameState createGameState() {
        GameState gameState = new GameState.GameStateBuilder()
                .setNPlayers(2)
                .setSelectedCharacters(List.of(Job.ARCHER,Job.WARRIOR))
                .enableAutoSave(true)
                .setInventory()
                .setFlagMap(new FlagMap())
                .setWorldPosition(new WorldPosition(0, 0))
                .setMaps(List.of(new Map1(), new Map2(), new Map3()))
                .build();
        gameState.createParty();
        gameState.createEnemy();
        return gameState;
    }
}
