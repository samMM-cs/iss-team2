package com.game.model.character;

import javafx.scene.image.Image;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.model.Position;

public class Enemy extends CharacterPG {
    private static final Image enemy_img = new Image(Enemy.class.getResourceAsStream("/characters/monsters.png"));

    @JsonCreator
    public Enemy(@JsonProperty("job") Job job, @JsonProperty("position") Position pos) {
        super(job, pos, enemy_img);
    }

    public void hide() {
        this.getSprite().setVisible(false);
    }

}