package com.game.model.character;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.game.model.Position;
import com.game.model.battle.*;

public abstract class CharacterPG implements HasSpriteAndPosition {
    private Job job;
    @JsonIgnore
    private Stats baseStats;

    private Stats currentStats;
    private Position pos;

    @JsonIgnore
    private transient Image img;
    @JsonIgnore
    private transient ImageView sprite;
    private List<Move> currentMove;

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

    @JsonCreator
    public CharacterPG(Job job, Position pos, Image img, Stats currenStats) {
        this(job, pos, img);
        this.currentStats = currenStats;
    }

    public ImageView createCharacterSprite(Image img, Job job) {
        this.sprite = new ImageView(img);
        this.sprite.setSmooth(false);
        this.sprite.setPreserveRatio(false);

        Rectangle2D viewPort = new Rectangle2D(job.getX(), job.getY(), Job.SIZE, Job.SIZE);

        this.sprite.setViewport(viewPort);
        return this.sprite;
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

    @Override
    public Position getPosition() {
        return pos;
    }

    @JsonIgnore
    public Image getImg() {
        return img;
    }

    @Override
    @JsonIgnore
    public ImageView getSprite() {
        return sprite;
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

    public void takeDamage(int value) {
        this.getCurrentStats().setHp(
                Math.max(0, this.getCurrentStats().getHp() - value));
    }

    public void heal(int value) {
        int maxHp = getBaseStats().getHp();
        int hp = getCurrentStats().getHp();
        getCurrentStats().setHp(Math.min(maxHp, hp + value));
    }

    @Override
    public String toString() {
        return this.getJob().toString();
    }
}