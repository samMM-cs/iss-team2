package com.game.model.character;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.game.model.Position;

public class NPC implements Interactable {

    private static final Image img = new Image(NPC.class.getResourceAsStream("/characters/rogues.png"));
    private ImageView sprite;
    private final Job job;
    private final Position pos;

    public NPC(Job job, Position pos) {
        this.job = job;
        this.pos = pos;
        this.sprite = createCharacterSprite(img, job);
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

    @Override
    public void interact(Player player) {

    }
    
    /*
     * private String name;
     * private List<Item> shopItems;
     * 
     * public NPC(String name, List<Item> shopItems) {
     * this.name = name;
     * this.shopItems = shopItems;
     * }
     * 
     * public final String getName() {
     * return this.name;
     * }
     * 
     * public final List<Item> getShopItems() {
     * return this.shopItems;
     * }
     */

}
