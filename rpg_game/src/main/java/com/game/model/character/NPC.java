package com.game.model.character;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.game.model.Position;
import com.game.model.character.dialogue.Interactable;
import com.game.view.DialogueView;
import com.game.model.character.dialogue.Dialogue;
import com.game.model.ability.*;

public abstract class NPC implements Interactable {
    private ImageView sprite;
    private final Job job;
    private final Position pos;

    private final Dialogue dialogue;

    private final List<Ability> shopAbilities;

    public NPC(Job job, Position pos, Image img, Dialogue dialogue) {
        this.job = job;
        this.pos = pos;
        this.sprite = createCharacterSprite(img, job);
        this.dialogue = dialogue;
        this.shopAbilities = new ArrayList<>();
    }

    private ImageView createCharacterSprite(Image img, Job job) {
        this.sprite = new ImageView(img);

        Rectangle2D viewPort = new Rectangle2D(job.getX(), job.getY(), Job.SIZE, Job.SIZE);
        this.sprite.setViewport(viewPort);
        return this.sprite;
    }

    public final Job getJob() {
        return this.job;
    }

    public final Position getPos() {
        return this.pos;
    }

    public final ImageView getSprite() {
        return this.sprite;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }

    public void addShopAbility(Ability ability) {
        this.shopAbilities.add(ability);
    }

    public List<Ability> getShopAbilities() {
        return shopAbilities;
    }

    @Override
    public void interact(Player player) {
        DialogueView view = new DialogueView();
        view.showDialogue(this.getDialogue());
    }
}