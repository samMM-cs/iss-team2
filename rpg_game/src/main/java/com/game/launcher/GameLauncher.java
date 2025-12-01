package com.game.launcher;

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
        
        MainMenuUI menu = new MainMenuUI(primaryStage);
        menu.show();
    }
}
