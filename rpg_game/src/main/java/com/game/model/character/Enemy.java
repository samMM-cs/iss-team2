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
        this.setCurrentStats(
                this.getCurrentStats().setHp(
                        Math.max(0, this.getCurrentStats().hp() - value)));
    }

    @Override
    public void heal(int value) {
        this.setCurrentStats(
                this.getCurrentStats().setHp(
                        Math.min(this.getBaseStats().hp(), this.getCurrentStats().hp() + value)));
    }

}