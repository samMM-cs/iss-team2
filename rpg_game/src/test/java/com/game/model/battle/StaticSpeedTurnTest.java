package com.game.model.battle;

import java.util.ArrayList;
import java.util.List;
import com.game.model.character.CharacterPG;
import com.game.model.character.Enemy;
import com.game.model.character.Player;
import com.game.model.character.Job;
import com.game.model.Position;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StaticSpeedTurnTest {
    StaticSpeedTurn sst;
    Position pos;
    List<CharacterPG> list;

    @BeforeEach
    void setUp() {
        pos= mock(Position.class);
        list= new ArrayList<>();
        CharacterPG p1= new Player (Job.ARCHER, pos);
        CharacterPG p2= new Player(Job.MAGE, pos);
        CharacterPG e1= new Enemy(Job.GOBLIN, pos);
        CharacterPG e2= new Enemy(Job.GOBLIN2, pos);
        list.add(p1);
        list.add(p2);
        list.add(e1);
        list.add(e2);
        sst= new StaticSpeedTurn(list);
    }

    @Test
    void testGetTurnIterator() {
        sst.sortAction();

        List<CharacterPG> res= sst.getCharacters();
        assertNotNull(res);
        for (int i=0; i<res.size()-1; i++) {
            assertTrue(res.get(i).getCurrentStats().getSpeed() >= res.get(i+1).getCurrentStats().getSpeed());
        }
    }

    @Test
    void testSortAction() {

    }
}
