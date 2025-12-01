package com.game.View;

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
        
        MainMenuView menu = new MainMenuView(primaryStage);
        menu.show();
    }
}
