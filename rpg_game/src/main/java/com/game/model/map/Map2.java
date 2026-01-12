package com.game.model.map;

import java.util.List;
import java.util.Random;

import com.game.model.Position;
import com.game.model.character.Enemy;
import com.game.model.character.Job;
import com.game.model.character.NPC;

public class Map2 implements MapData {
    private static final String MAP_FILE_PATH = "/maps/samplemap2.tmj";
    private static final int spriteIndex = 2;

    public Map2() {
    }

    @Override
    public int getSpriteindex() {
        return spriteIndex;
    }

    @Override
    public String getFilePath() {
        return MAP_FILE_PATH;
    }

    @Override
    public Position getPlayerPosition(int N, int i) {
        return new Position(11, N - i);
    }

    @Override
    public List<Enemy> getEnemies() {
        List<Position> pos = List.of(new Position(9, 10), new Position(16, 15), new Position(23, 26));
        List<Job> jobs = List.of(Job.GOBLIN, Job.TROLL);
        Random rand = new Random();
        return pos.stream().map(p -> new Enemy(jobs.get(rand.nextInt(jobs.size())), p, rand.nextInt(2, 5))).toList();
    }

    @Override
    public List<NPC> getNpcs() {
        return List.of();
    }
}
