package com.game.model.character;

import javafx.scene.image.Image;

import java.util.List;

import com.game.model.Position;
import com.game.model.ability.FireballAbility;
import com.game.model.ability.HealAbility;
import com.game.model.ability.Ability;
import com.game.model.character.dialogue.Dialogue;
import com.game.view.ShopView;

public class AbilityNPC extends NPC {
    private static final Image img = new Image(AbilityNPC.class.getResourceAsStream("/characters/rogues.png"));
    List<Ability> abilities = List.of(new FireballAbility(), new HealAbility());

    public AbilityNPC(Job job, Position pos) {
        super(job, pos, img, new Dialogue(List.of(
                "Welcome to my shop! I'm " + job,
                "Take a look at my goods.")));
        for (Ability ability : abilities) {
            this.addShopAbility(ability);
        }
    }

    @Override
    public void interact(Player player) {
        ShopView shop = new ShopView(player);
        shop.open(this);
    }
}
