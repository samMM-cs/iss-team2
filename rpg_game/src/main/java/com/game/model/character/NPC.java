package com.game.model.character;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.model.Position;
import com.game.model.character.dialogue.Interactable;
import com.game.view.DialogueView;
import com.game.model.character.dialogue.Dialogue;
import com.game.model.battle.*;

public abstract class NPC implements Interactable, HasSpriteAndPosition {
    @JsonIgnore
    private ImageView sprite;
    private final Job job;
    private final Position pos;

    private final Dialogue dialogue;

    private final List<Move> shopMoves;

    public NPC(Job job, Position pos, Image img, Dialogue dialogue) {
        this.job = job;
        this.pos = pos;
        this.sprite = createCharacterSprite(img, job);
        this.dialogue = dialogue;
        this.shopMoves = new ArrayList<>();
    }

    @JsonCreator
    public NPC(@JsonProperty("job") Job job, @JsonProperty("dialogue") Dialogue dialogue,
            @JsonProperty("shopMoves") List<Move> shopMoves, @JsonProperty("position") Position position) {
        this.job = job;
        this.dialogue = dialogue;
        this.shopMoves = shopMoves;
        this.pos = position;
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

    @Override
    public final Position getPosition() {
        return this.pos;
    }

    @JsonIgnore
    @Override
    public final ImageView getSprite() {
        return this.sprite;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }

    public void addShopMove(Move move) {
        this.shopMoves.add(move);
    }

    public List<Move> getShopMoves() {
        return Collections.unmodifiableList(this.shopMoves);
    }

    @Override
    public void interact(Player player) {
        DialogueView view = new DialogueView();
        view.showDialogue(this.getDialogue());
    }
}