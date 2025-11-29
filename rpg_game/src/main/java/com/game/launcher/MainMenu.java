package com.game.launcher;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {
    private Stage stage;

    public MainMenu(Stage stage) {
        this.stage = stage;
    }

    public void show() {

        // Creo ImageView
        ImageView imgBtn = new ImageView(new Image(getClass().getResource("Default.png").toExternalForm()));
        imgBtn.setFitWidth(50); // larghezza desiderata
        imgBtn.setFitHeight(50); // altezza desiderata
        imgBtn.setPreserveRatio(true); // mantiene proporzioni
        ImageView imgSfondo = new ImageView(new Image(getClass().getResource("Forest.png").toExternalForm()));

        Button newGameBtn = new Button("New Game");
        Button resumeBtn = new Button("Resume");
        Button settingsBtn = new Button("Settings");
        Button exitBtn = new Button("Exit");

        BackgroundImage bgImgBtn = new BackgroundImage(imgBtn.getImage(), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        newGameBtn.setBackground(new Background(bgImgBtn));
        resumeBtn.setBackground(new Background(bgImgBtn));
        settingsBtn.setBackground(new Background(bgImgBtn));
        exitBtn.setBackground(new Background(bgImgBtn));
        VBox buttons = new VBox(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(newGameBtn, resumeBtn, settingsBtn, exitBtn);

        //Immagine dietro ed i pulsanti sopra
        StackPane root = new StackPane(imgSfondo,buttons);
        Scene scene = new Scene(root, 800, 600);

        //Adatto lo sfondo rispetto alla dimensione della finestra
        imgSfondo.fitHeightProperty().bind(scene.heightProperty());
        imgSfondo.fitWidthProperty().bind(scene.widthProperty());
        
        // Eventi
        newGameBtn.setOnAction(e -> {
            System.out.println("New Game Started");
            // Qui chiamo i factory
        });

        settingsBtn.setOnAction(e -> {
            System.out.println("Impostazioni");
        });
        exitBtn.setOnAction(e -> stage.close());

        stage.setTitle("RPG Game");
        stage.setScene(scene);
        stage.show();
    }
}