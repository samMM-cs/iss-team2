package com.game.view.battleview;

import javafx.scene.control.ListView;

import com.game.model.battle.Battle;
import com.game.model.character.Enemy;

import javafx.geometry.Orientation;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class BattleView {
    private Enemy enemy;
    private Battle battle;

    private Pane root;
    private Scene scene;
    private Stage stage;
    ListView<String> listView = new ListView<>();

    public BattleView(Stage stage, Battle battle) {
        //this.root = root;
        this.root= new Pane();
        this.stage= stage;
        this.scene = new Scene(root, stage.getWidth(), stage.getHeight());
        
        this.battle= battle;
    }

    public void showBattle() {
        
        listView.getItems().addAll("Move", "Item", "Flee");

        listView.setOrientation(Orientation.HORIZONTAL);
        listView.setPrefHeight(60);

        root.getChildren().add(listView);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
