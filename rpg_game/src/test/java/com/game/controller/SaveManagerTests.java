package com.game.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.game.model.GameState;
import com.game.model.WorldPosition;
import com.game.model.character.Job;
import com.game.model.map.Map1;
import com.game.model.map.Map2;
import com.game.model.map.Map3;
import com.game.model.story.FlagMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SaveManagerTests {
    private SaveManager saveManager;
    private String projectRoot;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) {
        saveManager = new SaveManager();
        String projectRoot = System.getProperty("user.dir");

        new File(projectRoot, "autosave.json").delete();
        new File(projectRoot, "save_slot0.json").delete();
        new File(projectRoot, "save_slot1.json").delete();
        new File(projectRoot, "save_slot2.json").delete();
    }

    // T1 - isSlotUsed()==false
    @Test
    public void testIsSlotUsed_slotNotExists() throws IOException {
        assertFalse(saveManager.isSlotUsed(1));
    }

    // T2 - isSlotUsed()==true
    @Test
    public void testIsSlotUsed_slotExists() throws IOException {
        GameState gameState = trueGameState();
        saveManager.saveGame(1, gameState);
        assertTrue(new File(projectRoot, "save_slot1.json").exists());
    }

    // T3
    @Test
    public void testAutosave_NotExists() {
        assertFalse(saveManager.isAutosaveUsed());
    }

    // T4
    @Test
    public void testAutosave_exists() throws IOException {
        GameState gameState = trueGameState();
        saveManager.autosave(gameState);
        assertTrue(saveManager.isAutosaveUsed());
    }

    // T5
    @Test
    public void testAutosaveGameStateValid() throws IOException {
        GameState gameState = trueGameState();
        saveManager.autosave(gameState);
        assertTrue(new File(projectRoot, "autosave.json").exists());
    }

    // T6
    @Test
    public void testAutosaveGameStateNull() throws IOException {
        GameState gameState = trueGameState();
        saveManager.autosave(gameState);
        assertTrue(new File(projectRoot, "autosave.json").exists());
    }

    // T7
    @Test
    public void testSaveGame_InSlotValid() throws IOException {
        GameState gameState = trueGameState();

        saveManager.saveGame(1, gameState);
        assertTrue(new File(projectRoot, "save_slot1.json").exists());
    }

    // T8
    @Test
    public void testLoadGame_SlotValid() throws IOException {
        GameState gameState = trueGameState();
        gameState.getParty();
        saveManager.saveGame(1, gameState);

        assertDoesNotThrow(() -> saveManager.loadGame(1));
    }

    // T9
    @Test
    public void testLoadGame_SlotNotValid() {
        assertThrows(IOException.class, () -> saveManager.loadGame(99));
    }

    // T10
    @Test
    public void testLoadFromAutosave_Valid() throws IOException {
        GameState gameState = trueGameState();
        saveManager.autosave(gameState);

        assertDoesNotThrow(() -> saveManager.loadGameFromAutoSave());
    }

    // T11
    @Test
    public void testLoadFromAutosave_NotValid() {
        assertThrows(IOException.class, () -> saveManager.loadGameFromAutoSave());
    }

    private GameState trueGameState() {
        GameState gameState = new GameState.GameStateBuilder()
                .setNPlayers(1)
                .setSelectedCharacters(List.of(Job.ARCHER))
                .enableAutoSave(true)
                .setInventory()
                .setFlagMap(new FlagMap())
                .setWorldPosition(new WorldPosition(0, 0))
                .setMaps(List.of(new Map1(), new Map2(), new Map3()))
                .build();
        gameState.createParty();
        return gameState;
    }
}
