package com.game.model.character;

import org.junit.jupiter.api.Test;

import com.game.model.battle.MoveReader;
import com.game.model.battle.Move;
import com.game.model.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class NPCTest {

    static class TestNPC extends NPC {
        public TestNPC(Job job, Position pos) {
            super(job, pos, null, null);
        }
    }

    @Test
    void testAddShopMoves() {
        NPC npc = new TestNPC(Job.TRAINER, new Position(10, 10));
        List<Move> movesList = MoveReader.readMove("/battle/moves.json");
        assertNotNull(movesList);
        assertFalse(movesList.isEmpty());

        Move move = movesList.get(0);

        npc.addShopMove(move);

        assertEquals(1, npc.getShopMoves().size());
        assertTrue(npc.getShopMoves().contains(move));
    }

}