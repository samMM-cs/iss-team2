package com.game.model.character;

import javafx.scene.image.Image;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.model.Position;
import com.game.model.battle.*;
import com.game.model.character.dialogue.Dialogue;
import com.game.view.ShopView;

public class MovesNPC extends NPC {
    private static final Image img = new Image(MovesNPC.class.getResourceAsStream("/characters/rogues.png"));

    @JsonCreator
    public MovesNPC(@JsonProperty("job") Job job, @JsonProperty("position") Position pos) {
        super(job, pos, img, new Dialogue(List.of(
                "Welcome to my shop! I'm " + job,
                "Take a look at my goods.")));
        if (job == Job.TRAINER) {
            for (Move move : job.getShopMoves()) {
                addShopMove(move);
            }
        }
    }

    @Override
    public void interact(Player player) {
        ShopView shop = new ShopView(player);
        shop.open(this);
    }
}
