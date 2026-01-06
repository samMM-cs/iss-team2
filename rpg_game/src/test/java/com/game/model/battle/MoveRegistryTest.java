package com.game.model.battle;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

public class MoveRegistryTest {
    @Test
    void testGetMoveRegistry() {
        List<String> moveNames = MoveReader.readMove("/battle/moves.json").stream().map(Move::getName).toList();

        for (String moveName : moveNames) {
            ActionStrategy move = MoveRegistry.getMoveRegistry().getMoveActionStrategy(moveName);
            assertNotNull(move);
        }
    }
}
