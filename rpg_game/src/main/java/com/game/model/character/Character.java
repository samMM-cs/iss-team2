package com.game.model.character;

import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.game.model.Position;

public abstract class Character {
    private Job job;
    private Position pos;

    private ImageView sprite;
    List<Ability> abilities;

    public Character(Job job, Position pos, Image img) {
        this.job = job;
        this.pos = pos;
        this.sprite = createCharacterSprite(img, job);
    }

    public ImageView createCharacterSprite(Image img, Job job) {
        this.sprite = new ImageView(img);

        Rectangle2D viewPort = new Rectangle2D(job.getX(), job.getY(), Job.SIZE, Job.SIZE);

        this.sprite.setViewport(viewPort);
        return this.sprite;
    }

    public Job getJob() {
        return job;
    }

    public Position getPos() {
        return pos;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public final List<Ability> getAbilities() {
        return this.abilities;
    }

    protected void setPos(Position pos) {
        this.pos = pos;
    }

}