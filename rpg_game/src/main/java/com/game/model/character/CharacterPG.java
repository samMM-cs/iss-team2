package com.game.model.character;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.game.model.Position;
import com.game.model.ability.Ability;

public abstract class CharacterPG {
    private Job job;
    private Stats baseStats;

    private Stats currentStats;
    private Position pos;

    private ImageView sprite;
    private List<Ability> abilities = new ArrayList<>();

    public CharacterPG(Job job, Position pos, Image img) {
        this.job = job;
        this.pos = pos;
        this.sprite = createCharacterSprite(img, job);
        this.baseStats= job.getBaseStats().copy();
        this.currentStats= this.getBaseStats().copy();
    }

    public ImageView createCharacterSprite(Image img, Job job) {
        this.sprite = new ImageView(img);

        Rectangle2D viewPort = new Rectangle2D(job.getX(), job.getY(), Job.SIZE, Job.SIZE);

        this.sprite.setViewport(viewPort);
        return this.sprite;
    }

    public void addAbility(Ability ability) {
        this.abilities.add(ability);
    }

    //Metodo per usare tutte le abilità
    public void useAbility() {
        for (Ability a : abilities) {
            System.out.println("Using ability: " + a.getName());
            a.apply(this);
        }
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

    public Stats getBaseStats() {
        return baseStats;
    }

    public Stats getCurrentStats() {
        return currentStats;
    }

    public void setCurrentStats(Stats newStats) {
        currentStats = newStats;
    }

    public abstract void takeDamage(int value);

    public abstract void heal(int value);

}