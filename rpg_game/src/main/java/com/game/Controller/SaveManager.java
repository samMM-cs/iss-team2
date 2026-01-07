package com.game.controller;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.game.model.GameState;
import com.game.model.GameStateMemento;

public class SaveManager {
    private static final String SLOT_PREFIX = "save_slot";
    private static final String AUTOSAVE_FILE = "autosave";
    private static final String EXT = ".json";

    private final ObjectMapper objectMapper;

    public SaveManager() {
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    // Verifico se lo slot è già usato
    public boolean isSlotUsed(int slot) {
        return new File(SLOT_PREFIX + slot + EXT).exists();
    }

    public boolean isAutosaveUsed() {
        return new File(AUTOSAVE_FILE + EXT).exists();
    }

    public void autosave(GameState gameState) throws IOException {
        if (gameState == null)
            gameState = GameState.getInstance();
        File autosaveFile = new File(AUTOSAVE_FILE + EXT);
        GameStateMemento memento = gameState.saveToMemento();
        objectMapper.writeValue(autosaveFile, memento);
    }

    public void saveGame(int slot, GameState gameState) throws IOException {
        // Implementazione del salvataggio
        File file = new File(SLOT_PREFIX + slot + EXT);

        GameStateMemento memento = gameState.saveToMemento();
        objectMapper.writeValue(file, memento);
    }

    public void loadGame(int slot) throws IOException, ClassNotFoundException {
        File file = new File(SLOT_PREFIX + slot + EXT);
        if (!file.exists()) {
            throw new IOException("Save slot does not exist.");
        }

        GameStateMemento memento = objectMapper.readValue(file, GameStateMemento.class);
        new GameState.GameStateBuilder().restoreFromMemento(memento);
    }

    public void loadGameFromAutoSave() throws IOException {
        File file = new File(AUTOSAVE_FILE + EXT);
        if (!file.exists()) {
            throw new IOException("Save slot does not exist.");
        }

        GameStateMemento memento = objectMapper.readValue(file, GameStateMemento.class);
        new GameState.GameStateBuilder().restoreFromMemento(memento);
    }
}