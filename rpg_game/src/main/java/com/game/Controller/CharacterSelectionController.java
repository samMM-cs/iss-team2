package com.game.controller;

import com.game.view.MainMenuView;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class CharacterSelectionController {
    private Stage stage;

    public CharacterSelectionController(Stage stage) {
        this.stage = stage;
    }

    public void onArcher(ActionEvent e) {
        System.out.println("Archer");
        System.out.println(e);
    }

    public void onBack(ActionEvent e) {
        System.out.println("Back");
        System.out.println(e);
        new MainMenuView(stage).show();
    }
}
