package com.game.model.battle;

import java.util.Map;
import java.util.stream.Collectors;

public class MoveRegistry {
    private static MoveRegistry moveRegistry;
    private final Map<String, Move> moves; //Registra le mosse disponibili

    private MoveRegistry() {
        this.moves= MoveReader.readMove("battle/moves.json")
            .stream()
            .collect(Collectors.toMap(Move::getName, m->m));
    }

    public static MoveRegistry getMoveRegistry() {
        if (moveRegistry == null) {
            moveRegistry= new MoveRegistry();
        }
        return moveRegistry;
    }

    public ActionStrategy getMoveActionStrategy(String moveName) {
        Move move = moves.get(moveName);
        if (move == null)
            return null;
        return move.getType().createMove(move); //Crea la ConcreteStrategy
    }
}
