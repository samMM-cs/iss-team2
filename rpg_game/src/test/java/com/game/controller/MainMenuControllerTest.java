package com.game.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.game.model.creator.GameCreator;
import com.game.model.creator.Game;

public class MainMenuControllerTest {
    Game game;
    GameController gameController;
    MainMenuController controller;

    @BeforeEach
    void setup() {
        controller = spy(new MainMenuController());

        //Sostituisco i creato reali
        controller.newGameCreator = mock(GameCreator.class);
        controller.continueGameCreator = mock(GameCreator.class);
        controller.viewManager = mock(ViewManager.class);
        game = mock(Game.class);
        
        //quando createGameController viene chiamato restituisco il mock
        gameController = mock(GameController.class);
        doReturn(gameController).when(controller).createGameController(game);

        //Quando viene chiamato createGame() dal creator restituisco il mock Game
        when(controller.newGameCreator.createGame()).thenReturn(game);
        when(controller.continueGameCreator.createGame()).thenReturn(game);
    }
    //T1
    @Test
    void onNewGame_test() {
        controller.onNewGame(null);

        verify(controller.newGameCreator).createGame();
        verify(gameController).start();
        verifyNoInteractions(controller.continueGameCreator);
    }
    
    //T2
    @Test
    void onResumeGame_test() {
        controller.onResumeGame(null);
        
        verify(controller.continueGameCreator).createGame();
        verify(gameController).resume();
        verifyNoInteractions(controller.newGameCreator);
    }
    //T3
    @Test
    void testOnExit() {
        assertDoesNotThrow(() -> controller.onExit(null));
    }
}