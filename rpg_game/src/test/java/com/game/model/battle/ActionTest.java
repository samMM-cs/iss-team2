package com.game.model.battle;

import org.junit.jupiter.api.Test;

import com.game.model.character.CharacterPG;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.List;

public class ActionTest {
    @Test
    // Test per veirificare il comportamento, in questo caso il costruttore Action
    // prende ActionStrategy a parametro
    void testExecuteWithActionStrategy() {
        ActionStrategy strategy = mock(ActionStrategy.class);
        CharacterPG user = mock(CharacterPG.class);
        CharacterPG enemy = mock(CharacterPG.class);

        Action action = new Action(strategy, user, List.of(enemy));

        action.execute();
        verify(strategy).doAction(user, List.of(enemy));
    }

    @Test
    void testActionExecuteIsNotNull() {
        ActionStrategy strategy = mock(ActionStrategy.class);
        CharacterPG user = mock(CharacterPG.class);
        CharacterPG enemy = mock(CharacterPG.class);

        Action action = new Action(strategy, user, List.of(enemy));
        
        Action actionString = new Action("attack", user, enemy);

        assertNotNull(actionString.getAction());
        assertNotNull(action.getAction2());
    }

}
