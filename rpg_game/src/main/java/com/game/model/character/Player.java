package com.game.model.character;

import javafx.scene.image.*;

import com.game.model.Position;

public class Player extends CharacterPG {
    private Inventory inventory;
    private Player follower;
    private static final Image img = new Image(Player.class.getResourceAsStream("/characters/rogues.png"));

    public Player(Job job, Position position) {
        super(job, position, img);
    }

    public void equipItem(Item item) {
    }

    public void notifyFollower() {
        if (this.follower != null) {
            this.follower.notifyFollower();
            this.follower.setPosition(getPos());
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

    @Override
    public void takeDamage(int value) {
        int hp = getCurrentStats().getHp();
        getCurrentStats().setHp(Math.max(0, hp - value));
    }

    @Override
    public void heal(int value) {
        int maxHp = getBaseStats().getHp();
        int hp = getCurrentStats().getHp();
        getCurrentStats().setHp(Math.min(maxHp, hp + value));
    }
}