package com.game.model.battle;

import java.util.ArrayList;
import java.util.List;
import com.game.model.character.CharacterPG;
import com.game.model.character.Enemy;
import com.game.model.character.Player;
import com.game.model.character.Job;
import com.game.model.Position;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

public class StaticSpeedTurnTest {
    StaticSpeedTurn sst;
    Position pos;

    @Before
    public void setUp() {
        Position pos= mock(Position.class);
        sst= new StaticSpeedTurn();
    }

    @Test
    public void testGetTurnIterator() {
        List<CharacterPG> list= new ArrayList<>();
        CharacterPG p1= new Player (Job.ARCHER, pos);
        CharacterPG p2= new Player(Job.MAGE, pos);
        CharacterPG e1= new Enemy(Job.GOBLIN, pos);
        CharacterPG e2= new Enemy(Job.GOBLIN2, pos);
        list.add(p1);
        list.add(p2);
        list.add(e1);
        list.add(e2);

        sst.sortAction(list);

        for (int i=0; i<list.size()-1; i++) {
            assertTrue(list.get(i).getCurrentStats().getSpeed() >= list.get(i+1).getCurrentStats().getSpeed());
        }
        
    }

    @Test
    public void testSortAction() {

    }
}
