package com.game.model.battle;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MoveType {
    ATTACK {
        public ActionStrategy createMove(Move move) {
            return new AttackMove(move);
        }
    },
    HEAL {
        public ActionStrategy createMove(Move move) {
            return new HealMove(move);
        }
    },
    MAGIC_ATTACK {
        public ActionStrategy createMove(Move move) {
            return new MagicAttackMove(move);
        }
    },
    MAGIC_HEAL {
        public ActionStrategy createMove(Move move) {
            return new HealMove(move);
        }
    };

    public abstract ActionStrategy createMove(Move move);

    @JsonCreator
    public static MoveType fromString(String value) {
        return MoveType.valueOf(value.toUpperCase());
    }
}
