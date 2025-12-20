package com.game.model.battle;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

public class MoveReaderTest {
    @Test
    public void testReadMove() {
        String jsonpath= "battle/movestest.json";
        MoveTest moveTest1= new MoveTest("A01", "Basic Attack", "ATTACK", 10);
        MoveTest moveTest2= new MoveTest("H01", "Basic Heal", "HEAL", 5);

        List<Move> moveList= MoveReader.readMove(jsonpath);
        

        assertNotNull(moveList);
        assertEquals(2, moveList.size());
        assertEquals(moveTest1, moveList.get(0));
        assertEquals(moveTest2, moveList.get(1));
    }

    //Subclass to have method equals and constructor without changing Move class
    class MoveTest extends Move {
        public MoveTest (String id, String name, String type, int value) {
            this.id= id;
            this.name= name;
            this.type= type;
            this.value= value;
        }

        //Not symmetric, but still ok for this test
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Move)) {
                return false;
            }
            Move move= (Move) o;
            return this.getId().equals(move.getId())
                && this.getName().equals(move.getName())
                && this.getType().equals(move.getType())
                && this.getValue()==move.getValue();
        }
    }
}
