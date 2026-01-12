package com.game.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.game.model.GameState;
import com.game.model.character.Job;

public class GameControllerTest {
    @BeforeEach
    public void setup() {
        GameState.destroy();
    }

    @Test
    public void T1_GameControllerCreation() {
        GameController newGameController = GameControllerFactory.createController(GameMode.NEW_GAME);
        GameController continueGameController = GameControllerFactory.createController(GameMode.CONTINUE_GAME);

        assertInstanceOf(NewGameController.class, newGameController);
        assertInstanceOf(ContinueGameController.class, continueGameController);
    }

    @Test
    public void T2_onNewGameConfirmed() throws Exception {
        NewGameController gc = (NewGameController) GameControllerFactory.createController(GameMode.NEW_GAME);

        try (MockedStatic<ViewManager> mockedViewManager = mockStatic(ViewManager.class)) {
            ViewManager mockViewManagerInstance = mock(ViewManager.class);
            mockedViewManager.when(ViewManager::getInstance).thenReturn(mockViewManagerInstance);
            gc.onNewGameConfirmed(2, false);
            GameState gameState = GameState.getInstance();

            Field nPlayers = GameState.class.getDeclaredField("nPlayers");
            nPlayers.setAccessible(true);
            assertEquals(2, nPlayers.get(gameState));

            Field autosave = GameState.class.getDeclaredField("autoSaveEnabled");
            autosave.setAccessible(true);
            assertEquals(false, autosave.get(gameState));

            Field maps = GameState.class.getDeclaredField("maps");
            maps.setAccessible(true);
            assertEquals(3, ((List<?>) maps.get(gameState)).size());
        }
    }

    @Test
    public void T3_addCharacter() {
        NewGameController gc = (NewGameController) GameControllerFactory.createController(GameMode.NEW_GAME);
        try (MockedStatic<ViewManager> vm = mockStatic(ViewManager.class);) {
            ViewManager vminst = mock(ViewManager.class);
            vm.when(ViewManager::getInstance).thenReturn(vminst);
            gc.onNewGameConfirmed(2, false);
            gc.onCharacterSelected(Job.ARCHER);
            assertTrue(!GameState.getInstance().allCharactersSelected());
            assertTrue(GameState.getInstance().getSelectedCharacters().contains(Job.ARCHER));
        }
    }

    @Test
    public void T4_DoNotAddCharacterIfFull() {
        NewGameController gc = (NewGameController) GameControllerFactory.createController(GameMode.NEW_GAME);
        try (MockedStatic<ViewManager> vm = mockStatic(ViewManager.class);) {
            ViewManager vminst = mock(ViewManager.class);
            vm.when(ViewManager::getInstance).thenReturn(vminst);
            gc.onNewGameConfirmed(2, false);
            gc.onCharacterSelected(Job.ARCHER);
            gc.onCharacterSelected(Job.WARRIOR);
            gc.onCharacterSelected(Job.MAGE);
            assertTrue(GameState.getInstance().allCharactersSelected());
            assertTrue(!GameState.getInstance().getSelectedCharacters().contains(Job.MAGE));
        }
    }

    @Test
    public void T5_startExploration() {
        NewGameController gc = (NewGameController) GameControllerFactory.createController(GameMode.NEW_GAME);
        try (MockedStatic<ViewManager> vm = mockStatic(ViewManager.class);
                MockedStatic<GameState> gs = mockStatic(GameState.class)) {
            ViewManager vminst = mock(ViewManager.class);
            vm.when(ViewManager::getInstance).thenReturn(vminst);

            GameState gamestate = mock(GameState.class);
            gs.when(GameState::getInstance).thenReturn(gamestate);

            gc.onNewGameConfirmed(2, false);
            gc.onCharacterSelected(Job.ARCHER);
            gc.onCharacterSelected(Job.WARRIOR);
            gc.startExploration();

            verify(gamestate, atLeastOnce()).createEnemy();
            verify(gamestate).createParty();
            verify(gamestate, atLeastOnce()).createNpc();
        }
    }
}
