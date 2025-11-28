package com.game.launcher;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {
    private Stage stage;

    public MainMenu(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Button newGameBtn = new Button("New Game");
        Button exitBtn = new Button("Exit");

        newGameBtn.setOnAction(e -> {
            System.out.println("New Game Started");
            // Qui chiamo i factory
        });

        exitBtn.setOnAction(e -> stage.close());

        VBox layout = new VBox(10, newGameBtn, exitBtn);
        Scene scene = new Scene(layout, 800, 600);

        stage.setTitle("RPG Game");
        stage.setScene(scene);
        stage.show();
    }
}
