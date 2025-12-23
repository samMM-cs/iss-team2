package com.game.model.character;

import javafx.scene.image.Image;
import com.game.model.Position;

public class Enemy extends CharacterPG {
    private static final Image enemy_img = new Image(Enemy.class.getResourceAsStream("/characters/monsters.png"));

    public Enemy(Job job, Position pos) {
        super(job, pos, enemy_img);
    }

    @Override
    public void takeDamage(int value) {
        this.getCurrentStats().setHp(
            Math.max(0, this.getCurrentStats().getHp() - value));
    }

    @Override
    public void heal(int value) {
        this.getCurrentStats().setHp(
            Math.min(this.getBaseStats().getHp(), this.getCurrentStats().getHp() + value));
    }

}