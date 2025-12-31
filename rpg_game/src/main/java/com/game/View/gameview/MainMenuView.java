package com.game.view.gameview;

import com.game.controller.MainMenuController;
import com.game.controller.ViewManager;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainMenuView {
    private MainMenuController controller;

    public MainMenuView() {
        this.controller = new MainMenuController();
    }

    public void show() {

        ImageView imgSfondo = new ImageView(new Image(getClass().getResourceAsStream("/Forest.png")));

        Button newGameBtn = createButton("NewGame", "/Default.png");
        Button resumeBtn = createButton("Resume", "/Default.png");
        Button exitBtn = createButton("Exit", "/Default.png");

        VBox buttons = new VBox(15);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(newGameBtn, resumeBtn, exitBtn);

        // Immagine dietro ed i pulsanti sopra
        StackPane root = new StackPane(imgSfondo, buttons);

        Scene scene = new Scene(root, 800, 600);

        // Adatto lo sfondo rispetto alla dimensione della finestra
        imgSfondo.fitHeightProperty().bind(scene.heightProperty());
        imgSfondo.fitWidthProperty().bind(scene.widthProperty());

        // Eventi
        newGameBtn.setOnAction(controller::onNewGame);
        exitBtn.setOnAction(controller::onExit);

        ViewManager.getInstance().setAndShowScene(scene);

    }

    private Button createButton(String txt, String path) {
        ImageView imgBtn = new ImageView(new Image(getClass().getResourceAsStream(path)));
        BackgroundImage bgImgBtn = new BackgroundImage(imgBtn.getImage(), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        Button btn = new Button(txt);
        btn.setBackground(new Background(bgImgBtn));
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        btn.setPrefSize(200, 70);

        // Effetto click
        btn.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> btn.setScaleX(0.95));
        btn.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> btn.setScaleY(0.95));
        btn.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            btn.setScaleX(1);
            btn.setScaleY(1);
        });
        return btn;
    }
}