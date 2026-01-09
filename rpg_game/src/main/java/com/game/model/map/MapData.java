package com.game.model.map;

import java.util.List;
import java.util.stream.Stream;

import com.game.model.Position;
import com.game.model.character.Enemy;
import com.game.model.character.NPC;

public interface MapData {
  public String getFilePath();

  public int getSpriteindex();

  public Position getPlayerPosition(int N, int i);

  public List<Enemy> getEnemies();

  public List<NPC> getNpcs();

  default public List<Position> getPlayerPositions(int N) {
    return Stream.iterate(1, i -> i + 1).limit(N).map(i -> getPlayerPosition(N, i)).toList();
  }
}