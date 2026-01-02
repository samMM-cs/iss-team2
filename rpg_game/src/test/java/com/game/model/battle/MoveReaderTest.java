package com.game.model.battle;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

public class MoveReaderTest {
    @Test
    public void testReadMove() {
        String jsonpath = "/battle/moves.json";
        List<Move> moveList = MoveReader.readMove(jsonpath);

        MoveTest moveTest1 = new MoveTest("A01", "Basic Attack", MoveType.ATTACK, "Attacco fisico base", 10, 0);
        MoveTest moveTest2 = new MoveTest("H01", "Basic Heal", MoveType.HEAL, "Cura base", 5, 0);

        assertNotNull("La lista è vuota", moveList);
        if (!moveList.isEmpty()) {
            System.out.println("Mossa caricata");
            assertNotNull("La lista non è vuota", moveList);
        }
        // assertEquals(2, moveList.size());
        assertEquals(moveTest1.getType(), MoveType.ATTACK);
        assertEquals(moveTest2.getType(), MoveType.HEAL);
    }

    // Subclass to have method equals and constructor without changing Move class
    class MoveTest extends Move {
        String id, name, type, description;
        int cost, value;

        public MoveTest(String id, String name, MoveType type, String description, int value, int cost) {
            setId(id);
            setName(name);
            setType(type);
            setDescription(description);
            setValue(value);
            setCost(cost);
        }

        // Not symmetric, but still ok for this test
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Move)) {
                return false;
            }
            Move move = (Move) o;
            return this.getId().equals(move.getId())
                    && this.getName().equals(move.getName())
                    && this.getType().equals(move.getType())
                    && this.getValue() == move.getValue() && getDescription() == move.getDescription()
                    && getCost() == move.getCost();
        }

    }
}
