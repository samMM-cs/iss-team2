package com.game.model.character;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.game.model.battle.Move;
import com.game.model.battle.MoveReader;

public enum Job {

    WARRIOR(0, 0, 100, 4, 3, 0, 6, true),
    ROGUE(4, 1, 100, 4, 3, 0, 6, true),
    MAGE(1, 0, 100, 6, 3, 0, 4, true),
    ARCHER(2, 0, 100, 4, 3, 0, 4, true),
    // Enemy
    GOBLIN(0, 0, 100, 3, 3, 0, 4, false),
    TROLL(0, 1, 100, 4, 3, 0, 2, false),
    // NPC
    TRAINER(3, 4, 0, 0, 0, 0, 0, false),
    ;

    public static final int SIZE = 32;

    private final int col, row;
    private final Stats baseStats;

    private boolean isPlayable;
    private List<Move> effectiveMoves = new ArrayList<>();
    private List<Move> shopMoves = new ArrayList<>();

    Job(int col, int row, int hp, int attack, int defense, int special, int speed, boolean isPlayable) {
        this.col = col;
        this.row = row;
        this.baseStats = new Stats(hp, attack, defense, special, speed);
        this.isPlayable = isPlayable;
    }

    public int getX() {
        return this.col * SIZE;
    }

    public int getY() {
        return this.row * SIZE;
    }

    public boolean getIsPlayable() {
        return this.isPlayable;
    }

    public Stats getBaseStats() {
        return baseStats;
    }

    public static void initAllMoves() {
        List<Move> allMoves = MoveReader.readMove("/battle/moves.json");
        String shopTrainer = Job.TRAINER.name();
        for (Job job : Job.values()) {
            job.effectiveMoves = allMoves.stream()
                    .filter(move -> move.getReq().contains(job.name()) && move.getCost() == 0)
                    .collect(Collectors.toList());
            job.shopMoves = allMoves.stream()
                    .filter(move -> move.getReq().contains(shopTrainer) && move.getCost() > 0).collect(Collectors.toList());
        }
        
    }

    public List<Move> getShopMoves() {
        return shopMoves;
    }

    public List<Move> getEffectiveMoves() {
        return this.effectiveMoves;
    }
}