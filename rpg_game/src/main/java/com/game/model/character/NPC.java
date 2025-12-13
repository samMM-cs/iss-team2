package com.game.model.character;

import java.util.List;

public class NPC {
    private String name;
    private List<Item> shopItems;

    public NPC(String name, List<Item> shopItems) {
        this.name = name;
        this.shopItems = shopItems;
    }

    public final String getName() {
        return this.name;
    }

    public final List<Item> getShopItems() {
        return this.shopItems;
    }
}
