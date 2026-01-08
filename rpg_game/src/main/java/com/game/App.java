package com.game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import com.game.view.GameLauncher;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Qui avvio il Launcher
        GameLauncher.getInstance().launch(primaryStage);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
        });
        System.setErr(new PrintStream(
                new FileOutputStream("app.log", true),
                true,
                StandardCharsets.UTF_8));
        launch(args);
    }
}
