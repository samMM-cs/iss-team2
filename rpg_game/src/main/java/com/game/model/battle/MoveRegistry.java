package com.game.model.battle;

import java.util.Map;
import java.util.stream.Collectors;

public class MoveRegistry {
    private static MoveRegistry moveRegistry;
    private final Map<String, ActionStrategy> moves;

    private MoveRegistry() {
        this.moves= MoveReader.readMove("battle/moves.json")
            .stream()
            .collect(Collectors.toMap(Move::getName, MoveFactory::createMove));
    }

    public static MoveRegistry getMoveRegistry() {
        if (moveRegistry == null) {
            moveRegistry= new MoveRegistry();
        }
        return moveRegistry;
    }

    public ActionStrategy getMoveActionStrategy(String id) {
        return moves.get(id);
    }
}
