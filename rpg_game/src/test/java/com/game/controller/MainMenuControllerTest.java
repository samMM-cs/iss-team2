package com.game.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class MainMenuControllerTest {
    // GameController newGameController;
    // GameController continueGameController;
    MainMenuController mmcontroller;
    ViewManager viewManagerMock;
    MockedStatic<ViewManager> viewManagerStaticMock;

    @BeforeEach
    void setUp() {
        viewManagerMock = mock(ViewManager.class);
        mmcontroller = new MainMenuController();
        mmcontroller.viewManager = viewManagerMock;
        viewManagerStaticMock = mockStatic(ViewManager.class);
        viewManagerStaticMock.when(ViewManager::getInstance).thenReturn(viewManagerMock);
    }

    @AfterEach
    void tearDown() {
        viewManagerStaticMock.close();
    }

    // T1
    /*@Test
    void onNewGame_test() {
        try (MockedStatic<GameControllerFactory> factoryMock = mockStatic(GameControllerFactory.class)) {
            GameController gameControllerMock = mock(NewGameController.class);
            factoryMock.when(() -> GameControllerFactory.createController(GameMode.NEW_GAME))
                .thenReturn(gameControllerMock);
            mmcontroller.onNewGame(null);
            verify(GameControllerFactory.createController(GameMode.NEW_GAME));
        }

        //verifyNoInteractions(controller.continueGameCreator);
    }*/

    @Test
    void onNewGame_Test() {
        assertDoesNotThrow(() -> mmcontroller.onNewGame(null));
    }

    // T2
    /*@Test
    void onResumeGame_test() {
        try (MockedStatic<GameControllerFactory> factoryMock = Mockito.mockStatic(GameControllerFactory.class)) {
            GameController gameControllerMock = mock(ContinueGameController.class);
            factoryMock.when(() -> GameControllerFactory.createController(GameMode.CONTINUE_GAME))
                .thenReturn(gameControllerMock);
            mmcontroller.onResumeGame(null);
            verify(factoryMock.createController(GameMode.CONTINUE_GAME));
        }
    }*/

    @Test
    void onResumeGame_Test() {
        assertDoesNotThrow(() -> mmcontroller.onResumeGame(null));
    }

    // T3
    @Test
    void testOnExit() {
        assertDoesNotThrow(() -> mmcontroller.onExit(null));
    }
}