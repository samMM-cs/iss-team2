package com.game.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.game.model.GameState;
import com.game.model.character.Job;
import com.game.model.creator.NewGame;

/* 
ID | Metodo              | Scenario                                                         | Input                     | Azione                                                            | Risultato Atteso
T6 | saveGame            | Salvataggio del GameState                                        | slot=1                    | saveManager.saveGame()                                            | viene creato o sovrascritto un file di salvataggio
T7 | loadGame            | Caricamento di un salvataggio                                    | slot=0                    | saveManager.loadGame()                                            | viene creata l'instanza del GameState corrispondente al salvataggio
T8 | loadFromAutosave    | Caricamento dall'autosalvataggio                                 |                           | saveManager.loadGameFromAutoSave()                                | viene creata l'instanza del GameState corrispondente all'autosalvataggio
 */

public class GameControllerTest {
  @Test
  public void T1_GameControllerCreation() {
    assertDoesNotThrow(() -> {
      NewGame newGame = mock(NewGame.class);
      GameController gc = new GameController(newGame);
      Field game = GameController.class.getDeclaredField("game");
      game.setAccessible(true);
      assertInstanceOf(SaveManager.class, gc.getSaveManager());
      assertInstanceOf(NewGame.class, game.get(gc));
    });
  }

  @Test
  public void T2_onNewGameConfirmed() throws Exception {
    NewGame newGame = mock(NewGame.class);
    GameController gc = new GameController(newGame);

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

      System.out.println(GameState.getInstance());
    }
  }

  @Test
  public void T3_addCharacter() {
    NewGame newGame = mock(NewGame.class);
    GameController gc = new GameController(newGame);
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
    NewGame newGame = mock(NewGame.class);
    GameController gc = new GameController(newGame);
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
    NewGame newGame = mock(NewGame.class);
    GameController gc = new GameController(newGame);
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

      verify(gamestate).createEnemy();
      verify(gamestate).createParty();
      verify(gamestate).createNpc();
    }
  }
}
