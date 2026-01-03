package com.game.model.map;

import java.util.List;

import com.game.model.Position;
import com.game.model.character.Enemy;
import com.game.model.character.NPC;

public interface Map {
  public String getFilePath();

  public int getSpriteindex();

  public Position getPlayerPosition(int N, int i);

  public List<Enemy> getEnemies();

  public List<NPC> getNpcs();
}