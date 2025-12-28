package com.game.view;

import com.game.controller.ViewManager;

import javafx.stage.Stage;

public class GameLauncher {
    private static GameLauncher instance;

    private GameLauncher() {
    }

    public static GameLauncher getInstance() {
        if (instance == null)
            instance = new GameLauncher();
        return instance;
    }

    public void launch(Stage primaryStage) {
        System.out.println("RGP Game launching...");
        ViewManager.ViewManagerBuilder.build(primaryStage);

        ViewManager.getInstance().showMainMenu();
    }
}
