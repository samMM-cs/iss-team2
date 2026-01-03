package com.game.model.character;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.game.model.Position;
import com.game.model.ability.AbilityType;
import com.game.model.battle.*;

public abstract class CharacterPG {
    private Job job;
    private Stats baseStats;

    private Stats currentStats;
    private Position pos;

    private Image img;
    private ImageView sprite;
    private List<Move> currentMove;
    private List<AbilityType> abilities = new ArrayList<>();

    private boolean inCombat; // Stato del combattimento

    public CharacterPG(Job job, Position pos, Image img) {
        this.job = job;
        this.pos = pos;
        this.img = img;
        this.sprite = createCharacterSprite(this.img, job);
        this.baseStats = job.getBaseStats().copy();
        this.currentStats = this.getBaseStats().copy();
        this.currentMove = new ArrayList<>(job.getEffectiveMoves());
    }

    public ImageView createCharacterSprite(Image img, Job job) {
        this.sprite = new ImageView(img);
        this.sprite.setSmooth(false);
        this.sprite.setPreserveRatio(false);

        Rectangle2D viewPort = new Rectangle2D(job.getX(), job.getY(), Job.SIZE, Job.SIZE);

        this.sprite.setViewport(viewPort);
        return this.sprite;
    }

    public void addAbility(AbilityType ability) {
        this.abilities.add(ability);
    }

    protected void refreshCurrentStats() {
        this.currentStats = this.baseStats.copy();
    }

    public boolean isInCombat() {
        return this.inCombat;
    }

    public Job getJob() {
        return job;
    }

    public Position getPos() {
        return pos;
    }

    public Image getImg() {
        return img;
    }

    public ImageView getSprite() {
        return sprite;
    }

    public final List<AbilityType> getAbilities() {
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

    public List<Move> getCurrentMove() {
        return this.currentMove;
    }

    public void setCurrentStats(Stats newStats) {
        currentStats = newStats;
    }

    public abstract void takeDamage(int value);

    public abstract void heal(int value);

    @Override
    public String toString() {
        return this.getJob().toString();
    }

}