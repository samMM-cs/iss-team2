package com.game.model.character;

import javafx.scene.image.Image;
import com.game.model.Position;

public class Enemy extends Character {
    private static final Image enemy_img = new Image(Enemy.class.getResourceAsStream("/characters/monsters.png"));
    private int hp;

    public Enemy(Job job, Position pos) {
        super(job, pos, enemy_img);
        this.hp = job.getHp();
    }

    public int getHp() {
        return hp;
    }

    public void removeHp(int x) {
        this.hp -= x;
    }

}