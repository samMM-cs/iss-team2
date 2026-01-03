package com.game.model.character;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.game.model.Position;

public class PlayerTest {
    Player setUpPlayer(Position pos) {
        return new Player(Job.ARCHER, pos);
    }

    // Passa dalla view
    /*
     * @Test
     * public void testCreateTestPlayers() {
     * Job j1= Job.ARCHER;
     * Job j2= Job.WARRIOR;
     * Player p1= new Player(j1, new Position(j1.getX(), 0));
     * Player p2= new Player(j2, new Position(j2.getX(), 0));
     * GameState gameState= new GameState(2, false);
     * GameController gameController= new GameController(mock(Stage.class));
     * 
     * Player.createTestPlayers(null)
     * }
     */

    // Not yet implemented
    @Test
    void testEquipItem() {

    }

    // Not yet implemented
    @Test
    void testLearnAbility() {

    }

    @Test
    void testNotifyFollower() {
        Position p1pos = new Position(300, 100);
        Position p2pos = new Position(240, 100);
        Position p3pos = new Position(180, 100);
        Position p4pos = new Position(120, 100);
        Player p1 = setUpPlayer(p1pos);
        Player p2 = setUpPlayer(p2pos);
        Player p3 = setUpPlayer(p3pos);
        Player p4 = setUpPlayer(p4pos);
        p1.subscribeToFollowed(p2);
        p2.subscribeToFollowed(p3);
        p3.subscribeToFollowed(p4);

        p1.notifyFollower();

        assertEquals(240, (int) p2.getPos().x());
        assertEquals(180, (int) p3.getPos().x());
        assertEquals(120, (int) p4.getPos().x());
    }
}
