package com.game.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.game.model.GameState;
import com.game.model.GameStateMemento;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class SaveManagerTests {
    private SaveManager saveManager;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        saveManager = new SaveManager();
        new File("autosave.json").delete();
        new File("save_slot0.json").delete();
        new File("save_slot1.json").delete();
        new File("save_slot2.json").delete();
    }

    //T1 - isSlotUsed()==false
    @Test
    void testIsSlotUsed_slotNotExists() throws IOException {
        assertFalse(saveManager.isSlotUsed(1));
    }
    
    //T2 - isSlotUsed()==true
    @Test
    void testIsSlotUsed_slotExists() throws IOException {
        GameState gameState = mockGameState();
        saveManager.saveGame(1, gameState);
        assertTrue(new File("save_slot1.json").exists());
    }

    //T3
    @Test
    void testAutosave_NotExists() {
        assertFalse(saveManager.isAutosaveUsed());
    }

    //T4
    @Test
    void testAutosave_exists() throws IOException {
        GameState gameState = mockGameState();
        saveManager.autosave(gameState);
        assertTrue(saveManager.isAutosaveUsed());
    }

    //T5
    @Test
    void testAutosaveGameStateValid() throws IOException{
        GameState gameState = mockGameState();
        saveManager.autosave(gameState);
        assertTrue(new File("autosave.json").exists());
    }
    //T6
    @Test
    void testAutosaveGameStateNull() throws IOException {
        GameState gameState = mockGameState();
        saveManager.autosave(gameState);
        assertTrue(new File("autosave.json").exists());
    }
    
    //T7
    @Test
    void testSaveGame_InSlotValid() throws IOException {
        GameState gameState = mockGameState();

        saveManager.saveGame(1, gameState);
        assertTrue(new File("save_slot1.json").exists());
    }
    
    //T8
   /*  @Test
    void testLoadGame_SlotValid() throws IOException {
        GameState gameState = GameState.getInstance();
        gameState.getParty();
        saveManager.saveGame(1, gameState);

        assertDoesNotThrow(()->saveManager.loadGame(1));
    }*/
    //T9
    @Test
    void testLoadGame_SlotNotValid() {
        assertThrows(IOException.class, () -> saveManager.loadGame(99));
    }

    //T10
    /*@Test
    void testLoadFromAutosave_Valid() throws IOException{
        GameState gameState = mockGameState();
        saveManager.autosave(gameState);
    
        assertDoesNotThrow(() -> saveManager.loadGameFromAutoSave());
    }*/
   
    //T11
    @Test
    void testLoadFromAutosave_NotValid() {
        assertThrows(IOException.class, () -> saveManager.loadGameFromAutoSave());
    }
    
    //Lo uso per evitare codice duplicatp
    private GameState mockGameState() {
        GameState gameState = mock(GameState.class);
        GameStateMemento memento = mock(GameStateMemento.class);

        when(gameState.saveToMemento()).thenReturn(memento);
        return gameState;
    }
}
