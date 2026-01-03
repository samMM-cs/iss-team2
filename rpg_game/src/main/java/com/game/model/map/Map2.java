package com.game.model.map;

import java.util.List;

import com.game.model.Position;
import com.game.model.character.Enemy;
import com.game.model.character.NPC;

public class Map2 implements Map {
    private static final String MAP_FILE_PATH = "/maps/samplemap2.tmj";
    private static final int spriteIndex = 3;

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
        return new Position(11, N - i - 1);
    }

    @Override
    public List<Enemy> getEnemies() {
        return List.of();
    }

    @Override
    public List<NPC> getNpcs() {
        return List.of();
    }
}
