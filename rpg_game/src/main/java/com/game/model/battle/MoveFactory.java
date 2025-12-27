package com.game.model.battle;

public class MoveFactory {
    public static ActionStrategy createMove(Move move) {

        return switch (move.getType()) {
            case "ATTACK" -> new AttackMove(
                    move
            );
            case "HEAL" -> new HealMove(
                    move
            );
            case "MAGIC_ATTACK" -> new MagicAttackMove(
                    move
            );
            default -> throw new IllegalArgumentException(
                    "Unknown move type: " + move.getType());
        };
    }
}
