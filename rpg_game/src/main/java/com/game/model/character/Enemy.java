package com.game.model.character;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.model.Position;

public class Enemy extends CharacterPG {
    private static final Image enemy_img = new Image(Enemy.class.getResourceAsStream("/characters/monsters.png"));

    @JsonCreator
    public Enemy(@JsonProperty("job") Job job, @JsonProperty("position") Position pos) {
        super(job, pos, enemy_img);
    }

    public Enemy(Job job, Position pos, int lvl) {
        this(job, pos);
        this.getCurrentStats().setLevel(lvl);
    }

    public Enemy(Job job, Position pos, Stats stats) {
        super(job, pos, enemy_img, stats);
    }

    public Enemy(Job job, Position pos, Stats stats, int lvl) {
        this(job, pos, stats);
        this.getCurrentStats().setLevel(lvl);
    }

    public void hide() {
        this.getSprite().setVisible(false);
    }

    public List<Enemy> clones(int N) {
        List<Enemy> out = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            out.add(new Enemy(getJob(), getPosition(), getCurrentStats().copy()));
        }
        return out;
    }
}