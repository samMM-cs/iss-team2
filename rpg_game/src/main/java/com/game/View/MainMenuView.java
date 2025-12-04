package com.game.view;

import com.game.controller.MainMenuController;

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
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainMenuView {
    private Stage stage;
    private MainMenuController controller;

    public MainMenuView(Stage stage) {
        this.stage = stage;
        this.controller = new MainMenuController(stage);
    }

    public void show() {

        // Creo ImageView
        ImageView imgBtn = new ImageView(new Image(getClass().getResourceAsStream("/Default.png")));

        ImageView imgSfondo = new ImageView(new Image(getClass().getResourceAsStream("/Forest.png")));

        Button newGameBtn = createButton("New Game", "/Default.png");
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

        // Immagine dietro ed i pulsanti sopra
        StackPane root = new StackPane(imgSfondo, buttons);

        Scene scene = new Scene(root, 800, 600);

        // Adatto lo sfondo rispetto alla dimensione della finestra
        imgSfondo.fitHeightProperty().bind(scene.heightProperty());
        imgSfondo.fitWidthProperty().bind(scene.widthProperty());

        // Eventi
        newGameBtn.setOnAction(controller::onNewGame);

        settingsBtn.setOnAction(controller::onSettings);
        exitBtn.setOnAction(controller::onExit);
        stage.setTitle("RPG Game");
        stage.setWidth(Screen.getPrimary().getBounds().getWidth());
        stage.setHeight(Screen.getPrimary().getBounds().getHeight());
        stage.centerOnScreen();

        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    private Button createButton(String txt, String path) {
        final String BUTTON_FONT_FAMILY = "Verdana";
        final double BUTTON_FONT_SIZE = 20;
        final double BUTTON_PREF_WIDTH = 260;
        final double BUTTON_PREF_HEIGHT = 56;
        Font btnFont = new Font(BUTTON_FONT_FAMILY, BUTTON_FONT_SIZE);
        ImageView imgBtn = new ImageView(new Image(getClass().getResourceAsStream(path)));
        BackgroundImage bgImgBtn = new BackgroundImage(imgBtn.getImage(), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        Button btn = new Button(txt, imgBtn);
        btn.setFont(btnFont);
        btn.setPrefWidth(BUTTON_PREF_WIDTH);
        btn.setPrefHeight(BUTTON_PREF_HEIGHT);
        btn.setBackground(new Background(bgImgBtn));
        return btn;
    }
}