package com.game.model.character;

import javafx.scene.image.Image;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.game.model.Position;
import com.game.model.character.dialogue.Dialogue;
import com.game.view.DialogueView;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MerchantNPC.class, name = "MerchantNPC"),
        @JsonSubTypes.Type(value = MovesNPC.class, name = "MovesNPC")
})
public class MerchantNPC extends NPC {
    private static final Image img = new Image(MerchantNPC.class.getResourceAsStream("/characters/rogues.png"));

    @JsonCreator
    public MerchantNPC(@JsonProperty("job") Job job, @JsonProperty("position") Position pos) {
        super(job, pos, img, new Dialogue(List.of(
                "Welcome to my shop! I'm " + job,
                "Take a look at my goods.")));
    }

    @Override
    public void interact(Party party) {
        if (getDialogue() != null) {
            DialogueView view = new DialogueView();
            view.showDialogue(getDialogue());
        }
    }
}
