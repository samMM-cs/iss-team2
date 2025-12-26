package com.game.model.character;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.game.model.Position;
import com.game.model.ability.Ability;

public abstract class CharacterPG {
    private Job job;
    private Stats baseStats;

    private Stats currentStats;
    private Position pos;

    private ImageView sprite;
    private List<Ability> abilities = new ArrayList<>();

    // Per l'HUD
    private VBox hud;
    private ProgressBar hpBar;
    private ProgressBar expBar;
    private Label nameLabel;
    private Label hpLabel;
    private Label levelLabel;

    private boolean inCombat; // Stato del combattimento

    public CharacterPG(Job job, Position pos, Image img) {
        this.job = job;
        this.pos = pos;
        this.sprite = createCharacterSprite(img, job);
        this.baseStats = job.getBaseStats().copy();
        this.currentStats = this.getBaseStats().copy();
        // HUD
        nameLabel = new Label(job.name());
        hpLabel = new Label("HP");
        hpBar = new ProgressBar(1);
        expBar = new ProgressBar(0);
        levelLabel = new Label("Level " + baseStats.getLevel());

        HBox hpBox = new HBox(5, hpLabel, hpBar);
        hud = new VBox(5, nameLabel, hpBox, levelLabel, expBar);
        hud.setVisible(false); // HUD invisibile di default
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

    // Metodo per usare tutte le abilità
    public void useAbility() {
        for (Ability a : abilities) {
            System.out.println("Using ability: " + a.getName());
            a.apply(this);
        }
    }

    public void gainExp(int amount) {
        baseStats.addExp(amount);
        refreshCurrentStats();
    }

    protected void refreshCurrentStats() {
        this.currentStats = this.baseStats.copy();
    }

    public void enterCombat() {
        inCombat = true;
        hud.setVisible(inCombat);
        updateHud();
    }

    public void exitCombat() {
        inCombat = false;
        hud.setVisible(inCombat);
        updateHud();
    }

    public void updateHud() {
        hpBar.setProgress((double) currentStats.getHp() / baseStats.getHp());
        expBar.setProgress(baseStats.getXpPerc());
        levelLabel.setText("Lv " + baseStats.getLevel());
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

    public VBox getHud() {
        return hud;
    }

    public abstract void takeDamage(int value);

    public abstract void heal(int value);

    @Override
    public String toString() {
        return this.getJob().toString();
    }

}