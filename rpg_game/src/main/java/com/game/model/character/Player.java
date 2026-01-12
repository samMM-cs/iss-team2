package com.game.model.character;

import javafx.scene.image.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.model.Position;
import com.game.model.battle.*;

public class Player extends CharacterPG {
    private Inventory inventory;
    private Player follower;
    private static final Image img = new Image(Player.class.getResourceAsStream("/characters/rogues.png"));

    public Player(Job job, Position position) {
        super(job, position, img);
    }

    @JsonCreator
    public Player(@JsonProperty("job") Job job,
            @JsonProperty("position") Position position,
            @JsonProperty("currentMove") List<Move> moves) {
        this(job, position);
    }

    public void equipItem(Item item) {
    }

    public void notifyFollower() {
        if (this.follower != null) {
            this.follower.notifyFollower();
            this.follower.setPosition(getPosition());
        }
    }

    public void learnMove(Move move) {
        if (!this.getCurrentMove().contains(move)) {
            this.getCurrentMove().add(move);
        }
    }

    public void subscribeToFollowed(Player player) {
        player.setFollower(this);
    }

    public void unsubscribeFromFollowed(Player player) {
        player.setFollower(null);
    }

    public final void addXp(int xp) {
        this.getCurrentStats().addExp(xp);
    }

    public final Inventory getInventory() {
        return this.inventory;
    }

    public final Player getFollower() {
        return this.follower;
    }

    public void setFollower(Player follower) {
        this.follower = follower;
    }

    public void setPosition(Position pos) {
        this.setPos(pos);
    }
}