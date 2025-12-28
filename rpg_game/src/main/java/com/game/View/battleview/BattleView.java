package com.game.view.battleview;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

import com.game.model.GameState;
import com.game.model.battle.Battle;
import com.game.model.character.CharacterPG;
import com.game.model.character.Enemy;
import com.game.model.character.Party;
import com.game.view.mapview.ExplorationView;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class BattleView {
    private Enemy enemy;
    private Battle battle;

    private Pane root;
    private Scene scene;
    private Stage stage;
    ListView<String> listView = new ListView<>();
    private Party party;

    public BattleView(Stage stage, Battle battle) {
        // this.root = root;
        this.root = new Pane();
        this.stage = stage;
        this.scene = new Scene(root, stage.getWidth(), stage.getHeight());
        this.party = GameState.getInstance().getParty();
        this.battle = battle;
    }

    private VBox createHud(CharacterPG character) {
        ProgressBar hpBar = new ProgressBar(
                (double) character.getCurrentStats().getHp() / character.getBaseStats().getHp());
        ProgressBar expBar = new ProgressBar(character.getCurrentStats().getXpPerc());
        Label nameLabel = new Label(character.getJob().name());
        Label levelLabel = new Label("Level " + character.getBaseStats().getLevel());

        HBox hpBox = new HBox(5, new Label("HP"), hpBar);

        return new VBox(5, nameLabel, hpBox, levelLabel, expBar);
    }

    public void showBattle() {
        root.getChildren().clear();

        BorderPane bp = new BorderPane();
        bp.prefWidthProperty().bind(scene.widthProperty());
        bp.prefHeightProperty().bind(scene.heightProperty());

        // Top: players' HUDs
        HBox playersBox = new HBox(10);
        playersBox.setPadding(new Insets(10));
        playersBox.getChildren().addAll(party.getMembers().stream().map(this::createHud).toList());

        // Center: filler area (battle arena)
        // TODO: actually show battle

        // Bottom: actions (Move, Item, Flee)
        listView.getItems().clear();
        listView.getItems().addAll("Move", "Item", "Flee");
        listView.setOrientation(Orientation.HORIZONTAL);
        listView.setPrefHeight(60);
        listView.setMaxHeight(60);
        listView.setOnMouseClicked(e -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem.equals("Flee")) {
                new ExplorationView(stage).showMap();
            }
        });
        HBox bottomBox = new HBox(listView);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));

        bp.setTop(playersBox);
        bp.setBottom(bottomBox);

        root.getChildren().add(bp);
        this.stage.setScene(scene);
        this.stage.show();
    }
}
