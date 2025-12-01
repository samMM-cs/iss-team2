package com.game;

import com.game.View.GameLauncher;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Qui avvio il Launcher
        GameLauncher.getInstance().launch(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
