package com.game.model.ability;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public enum AbilityType {
    FIREBALL(4, 0, 20, 0, 100, 1, "Fireball", "Lancia sfere di fuoco"),
    HEAL(1, 3, 0, 30, 200, 2, "Heal", "Cura il player");

    private final int SIZE = 32;
    private final int col, row, damage, heal, cost, rarity;
    private final Image img = new Image(
            AbilityType.class.getResourceAsStream("/battle/icons/ability/ability_sheet.png"));
    private final String name, description;

    AbilityType(int col, int row, int damage, int heal, int cost, int rarity, String name, String descrption) {
        this.col = col;
        this.row = row;
        this.damage = damage;
        this.heal = heal;
        this.cost = cost;
        this.rarity = rarity;
        this.name = name;
        this.description = descrption;
    }

    public ImageView getIcon() {
        ImageView view = new ImageView(img);
        view.setViewport(new Rectangle2D(getX(), getY(), SIZE, SIZE));
        return view;
    }

    public int getX() {
        return this.col * SIZE;
    }

    public int getY() {
        return this.row * SIZE;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public int getDamage() {
        return damage;
    }

    public int getHeal() {
        return heal;
    }

    public int getRarity() {
        return rarity;
    }

    public Image getImg() {
        return img;
    }
}