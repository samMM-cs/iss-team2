package com.game.model.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.game.model.Position;
import com.game.model.character.Enemy;
import com.game.model.character.Job;
import com.game.model.character.MovesNPC;
import com.game.model.character.NPC;

public class Map1 implements MapData {
    private static final String MAP_FILE_PATH = "/maps/samplemap1.tmj";
    private static final int spriteIndex = 2;

    public Map1() {
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
        return new Position(N - i - 1, 7);
    }

    @Override
    public List<Enemy> getEnemies() {
        List<Enemy> enemies = new ArrayList<>();
        List<Job> enemiesJob = List.of(Job.GOBLIN, Job.GOBLIN, Job.TROLL, Job.TROLL);
        List<Position> pos = List.of(new Position(5, 5), new Position(13, 14),
                new Position(14, 33), new Position(35, 24));
        for (int i = 0; i < enemiesJob.size(); i++) {
            Position newPos = new Position(pos.get(i).x(), pos.get(i).y());
            enemies.add(new Enemy(enemiesJob.get(i), newPos, new Random().nextInt(1, 3)));
        }
        return enemies;
    }

    @Override
    public List<NPC> getNpcs() {
        List<NPC> npc = new ArrayList<>();
        npc.add(new MovesNPC(Job.TRAINER, new Position(18, 25)));
        npc.add(new MovesNPC(Job.TRAINER, new Position(46, 48)));
        return npc;
    }
}
