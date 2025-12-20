package com.game.view.gameview;

import com.game.controller.GameController;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;

public class NewGameView extends GameView {
    public static final int MAX_PLAYER = 4;
    private GameController gameController;

    public NewGameView(Stage stage, GameController gameController) {
        super(stage);
        this.gameController = gameController;
    }

    @Override
    public void show() {
        if (stage == null) {
            System.err.println("Stage null in NewGameView");
            return;
        }

        // ===== PLAYER BUTTONS (GAME STYLE) =====
        ToggleButton player1 = createButton(new ToggleButton(), "1 PLAYER", "/Default.png");
        ToggleButton player2 = createButton(new ToggleButton(), "2 PLAYER", "/Default.png");
        ToggleButton player3 = createButton(new ToggleButton(), "3 PLAYER", "/Default.png");
        ToggleButton player4 = createButton(new ToggleButton(), "4 PLAYER", "/Default.png");

        player1.setUserData(1);
        player2.setUserData(2);
        player3.setUserData(3);
        player4.setUserData(4);

        ToggleGroup group = new ToggleGroup();
        player1.setToggleGroup(group);
        player2.setToggleGroup(group);
        player3.setToggleGroup(group);
        player4.setToggleGroup(group);

        player2.setSelected(true); // default

        ToggleButton[] players = { player1, player2, player3, player4 };

        // Effetto selezione visiva
        for (ToggleButton btn : players) {
            btn.setStyle("-fx-text-fill: white;");

            btn.selectedProperty().addListener((obs, oldVal, selected) -> {
                if (selected) {
                    btn.setScaleX(1.1);
                    btn.setScaleY(1.1);
                    btn.setStyle("-fx-text-fill: black;");
                } else {
                    btn.setScaleX(1);
                    btn.setScaleY(1);
                    btn.setStyle("-fx-text-fill: white;");
                }
            });
        }

        HBox playerBox = new HBox(25, players);
        playerBox.setAlignment(Pos.CENTER);

        // ===== AUTOSAVE =====
        CheckBox autoSave = new CheckBox("AUTO SAVE");
        autoSave.setStyle("-fx-text-fill: white; -fx-font-size: 16;");

        // ===== CONFIRM BUTTON =====
        Button confirm = createButton(new Button(), "START", "/Default.png");

        confirm.setOnAction(e -> {
            int selectedPlayers = getSelectedPlayers(group);
            gameController.onNewGameConfirmed(selectedPlayers, autoSave.isSelected());
        });

        // ===== MAIN MENU LAYOUT =====
        VBox menu = new VBox(35, playerBox, autoSave, confirm);
        menu.setAlignment(Pos.CENTER);

        // ===== BACKGROUND =====
        ImageView bg = new ImageView(new Image(getClass().getResourceAsStream("/Forest.png")));
        Rectangle overlay = new Rectangle(800, 600, Color.rgb(0, 0, 0, 0.5));

        StackPane root = new StackPane(bg, overlay, menu);
        Scene scene = new Scene(root, 800, 600);

        bg.fitWidthProperty().bind(scene.widthProperty());
        bg.fitHeightProperty().bind(scene.heightProperty());
        overlay.widthProperty().bind(scene.widthProperty());
        overlay.heightProperty().bind(scene.heightProperty());

        stage.setScene(scene);
        stage.show();

        FadeTransition ft = new FadeTransition(Duration.seconds(1), menu);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private int getSelectedPlayers(ToggleGroup group) {
        if (group.getSelectedToggle() == null)
            return -1;
        return (int) group.getSelectedToggle().getUserData();
    }

    private <T extends ButtonBase> T createButton(T btn, String txt, String path) {
        ImageView imgBtn = new ImageView(new Image(getClass().getResourceAsStream(path)));
        BackgroundImage bgImgBtn = new BackgroundImage(imgBtn.getImage(), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        btn.setText(txt);
        btn.setBackground(new Background(bgImgBtn));
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        btn.setPrefSize(200, 70);
        btn.setAlignment(Pos.CENTER);
        btn.setPadding(new Insets(0));
        btn.setContentDisplay(ContentDisplay.CENTER);

        // Effetto click
        btn.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> btn.setScaleX(0.95));
        btn.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> btn.setScaleY(0.95));
        btn.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            btn.setScaleX(1);
            btn.setScaleY(1);
        });
        return btn;
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }

    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
