package com.game.model.battle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.game.model.character.Enemy;
import com.game.model.character.Job;
import com.game.model.map.Map1;
import com.game.model.map.Map2;
import com.game.model.map.Map3;
import com.game.model.story.FlagMap;
import com.game.model.GameState;
import com.game.model.Position;
import com.game.model.WorldPosition;

import java.util.ArrayList;
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
    public void setup() {
        GameState.destroy();
        gameState = createGameState();
        enemy = new Enemy(Job.TROLL, mock(Position.class));
        rewardStrategy = new StandardRewardStrategy();
        turnStrategy = new StaticSpeedTurn(gameState.getParty(), enemy.clones(gameState.getNPlayers()));

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
        assertTrue(battle.getEnemies().isEmpty());
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
        battle = new Battle(enemy);
        for (int i = 0; i < battle.getEnemies().size(); i++) {
            String move = battle.enemyAIString(i);
            assertNotNull(move);
            assertTrue(moves.stream().map(Move::getName).toList().contains(move));
        }
    }

    // T6
    @Test
    void test_enemyAIActionStrategy() {
        battle = new Battle(enemy);
        for (int i = 0; i < battle.getEnemies().size(); i++) {
            ActionStrategy action = battle.enemyAIActionStrategy(i);

            assertNotNull(action);
        }
    }

    // T7
    @Test
    void nextTurn_testAllPlayerAlive() {
        GameState gameState = GameState.getInstance();
        battle = new Battle(enemy);
        List<Action> planned = new ArrayList<>();
        for (int i = 0; i < battle.getEnemies().size(); i++) {
            planned.add(new Action(battle.enemyAIString(i), enemy, gameState.getParty().getMainPlayer()));
        }
        battle.setPlannedActionList(planned);

        BattleResult result = battle.nextTurn();

        assertNotNull(result);
        assertEquals(BattleResult.ONGOING, result);
        assertEquals(1, battle.getTurnIndex());
    }

    // T10
    @Test
    void test_AssignRewards() throws Exception {
        battle = new Battle(enemy);

        battle.assignRewards();

        assertEquals(40, gameState.getParty().getMainPlayer().getCurrentStats().getXp());
    }

    private GameState createGameState() {
        GameState gameState = new GameState.GameStateBuilder()
                .setNPlayers(2)
                .setSelectedCharacters(List.of(Job.ARCHER, Job.WARRIOR))
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
