package com.game.model.battle;

public class MoveFactory {
    public static ActionStrategy createMove(Move move) {
            return move.getType().createMove(move);
    }
}
