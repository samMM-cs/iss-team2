package com.game.model.character;

import javafx.scene.image.Image;
import com.game.model.Position;

public class Enemy extends CharacterPG {
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

    @Override
    public void takeDamage(int value) {
        this.hp= Math.max(0, this.hp - value);
    }

    @Override
    public void heal(int value) {
        this.hp= Math.min(this.getJob().getHp(), this.hp+value);
    }

}