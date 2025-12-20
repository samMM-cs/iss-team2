package com.game.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PositionTest {
    @Test
    public void constructor_inizializesCoordinates() {
        Position p = new Position(10, 20);
        assertEquals(10, (int) p.x());
        assertEquals(20, (int) p.y());
    }

    @Test
    public void add_returnNewPosition() {
        Position p = new Position(10, 10);
        Position result = p.add(3, -1);

        assertEquals(13, (int) result.x());
        assertEquals(9, (int) result.y());
    }

    @Test
    public void add_doesNotModifyOriginal() {
        Position p = new Position(5, 5);
        p.add(1, 1);

        assertEquals(5, (int) p.x());
        assertEquals(5, (int) p.y());
    }

    @Test
    public void clamp_insideLimits() {
        Position p = new Position(4, 4);
        Position result = p.clamp(10, 10);

        assertEquals(4, (int) result.x());
        assertEquals(4, (int) result.y());
    }

    @Test
    public void clamp_outsideLimits() {
        Position p = new Position(20, -3);
        Position result = p.clamp(10, 10);

        assertEquals(10, (int) result.x());
        assertEquals(0, (int) result.y());
    }

    @Test
    public void isInside_returnsTrue() {
        Position p = new Position(4, 4);
        assertTrue(p.isInside(new Position(10, 10)));
    }

    @Test
    public void isInside_retunrsFalse() {
        Position p = new Position(20, 20);
        assertFalse(p.isInside(new Position(10, 10)));
    }

}