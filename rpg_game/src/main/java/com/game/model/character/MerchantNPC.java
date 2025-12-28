package com.game.model.character;

import javafx.scene.image.Image;

import java.util.List;

import com.game.model.Position;
import com.game.model.character.dialogue.Dialogue;
import com.game.view.DialogueView;

public class MerchantNPC extends NPC {
    private static final Image img = new Image(MerchantNPC.class.getResourceAsStream("/characters/rogues.png"));

    public MerchantNPC(Job job, Position pos) {
        super(job, pos, img, new Dialogue(List.of(
                "Welcome to my shop! I'm "+job,
                "Take a look at my goods.")));
    }

    @Override
    public void interact(Player player) {
        System.out.println("Parla il merchant");

        if (getDialogue() != null) {
            DialogueView view = new DialogueView();
            view.showDialogue(getDialogue());
        }
    }
}
