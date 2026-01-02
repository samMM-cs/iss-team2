package com.game.model.ability;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import com.game.model.Position;
import com.game.model.battle.*;
import com.game.model.character.Job;
import com.game.model.character.NPC;
import com.game.model.character.Player;
import com.game.model.character.dialogue.Dialogue;
import com.game.view.ShopView;

public class AbilityNPC extends NPC {
    private static final Image img = new Image(AbilityNPC.class.getResourceAsStream("/characters/rogues.png"));
    List<AbilityType> abilities = List.of(AbilityType.FIREBALL, AbilityType.HEAL);
    List<Move> abilityList = new ArrayList<>();

    public AbilityNPC(Job job, Position pos) {
        super(job, pos, img, new Dialogue(List.of(
                "Welcome to my shop! I'm " + job,
                "Take a look at my goods.")));
        for (AbilityType ability : abilities) {
            this.addShopAbility(ability);
        }
    }

    @Override
    public void interact(Player player) {
        ShopView shop = new ShopView(player);
        shop.open(this);
    }
}
