package com.game.controller;    

import javafx.event.ActionEvent;

public class MainMenuController {
    public ViewManager viewManager = ViewManager.getInstance();

    public MainMenuController() {
    }

    public void onNewGame(ActionEvent event) {
        GameControllerFactory.createController(GameMode.NEW_GAME).start();
    }

    public void onResumeGame(ActionEvent event) {
        GameControllerFactory.createController(GameMode.CONTINUE_GAME).start();
    }

    public void onExit(ActionEvent event) {
        viewManager.exit();
    }
}