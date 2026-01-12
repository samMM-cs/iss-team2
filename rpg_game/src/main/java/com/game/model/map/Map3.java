package com.game.model.map;

import java.util.List;
import java.util.Random;

import com.game.model.Position;
import com.game.model.character.Enemy;
import com.game.model.character.Job;
import com.game.model.character.NPC;

public class Map3 implements MapData {
  private static final String MAP_FILE_PATH = "/maps/samplemap3.tmj";
  private static final int spriteIndex = 2;

  @Override
  public String getFilePath() {
    return MAP_FILE_PATH;
  }

  @Override
  public int getSpriteindex() {
    return spriteIndex;
  }

  @Override
  public Position getPlayerPosition(int N, int i) {
    System.out.println(N - i);
    return switch (N - i) {
      case 0 -> new Position(18, 0); // last player
      case 1 -> new Position(17, 0);
      case 2 -> new Position(17, 1);
      case 3 -> new Position(17, 2);
      default -> null; // unreachable
    };
  }

  @Override
  public List<Enemy> getEnemies() {
    List<Position> pos = List.of(new Position(14, 4), new Position(17, 9),
        new Position(20, 25), new Position(22, 25), new Position(24, 25));
    List<Job> jobs = List.of(Job.GOBLIN, Job.TROLL);
    Random rand = new Random();
    return pos.stream().map(p -> new Enemy(jobs.get(rand.nextInt(jobs.size())), p, rand.nextInt(3, 7))).toList();
  }

  @Override
  public List<NPC> getNpcs() {
    return List.of();
  }

}
