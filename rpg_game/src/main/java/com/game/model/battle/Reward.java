package com.game.model.battle;

import java.util.List;

import com.game.model.character.Inventory;
import com.game.model.character.Item;
import com.game.model.character.Party;
import com.game.model.character.Player;

public class Reward {
    int xp;
    List<Item> drop;

    public Reward(int xp, List<Item> drop) {
        this.xp = xp;
        this.drop = drop;
    }

    public void assignXP(Party party) {
        List<Player> team = party.getMembers();
        for (Player player : team) {
            player.addXp(xp);
        }
    }

    public void assignItem(Inventory inventory) {
        for (Item item : this.drop) {
            inventory.addItem(item);
        }
    }
}
