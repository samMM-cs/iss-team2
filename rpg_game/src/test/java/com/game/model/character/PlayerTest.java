package com.game.model.character;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.game.model.Position;
import com.game.model.battle.Move;
import com.game.model.battle.MoveReader;

import java.util.List;

public class PlayerTest {
    Player setUpPlayer(Position pos) {
        return new Player(Job.ARCHER, pos);
    }

    @Test
    void testLearnMove() {
        Player player = new Player(Job.WARRIOR, new Position(0, 0));
        List<Move> moves = MoveReader.readMove("/battle/moves.json");

        Move addMove = moves.get(0);

        player.learnMove(addMove);

        assertTrue(player.getCurrentMove().contains(addMove));
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

        assertEquals(240, (int) p2.getPosition().x());
        assertEquals(180, (int) p3.getPosition().x());
        assertEquals(120, (int) p4.getPosition().x());
    }
}
