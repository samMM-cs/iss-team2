package com.game.view.battleview;

import javafx.scene.control.ListView;
import javafx.geometry.Orientation;

public class BattleView {
    ListView<String> listView = new ListView<>();

    public void show() {
        listView.getItems().addAll("Move", "Item", "Flee");

        listView.setOrientation(Orientation.HORIZONTAL);
        listView.setPrefHeight(60);
    }
}
