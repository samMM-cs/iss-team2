package com.game.view;

import com.game.controller.GameController;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class NewGameView extends GameView{
    public static final int MAX_PLAYER= 4;

    public NewGameView(Stage stage) {
        this.stage= stage;
        this.controller= new GameController(this);
    }

    @Override
    public void load() {
        RadioButton player1 = new RadioButton("1 Player");
        RadioButton player2 = new RadioButton("2 Player");
        RadioButton player3 = new RadioButton("3 Player");
        RadioButton player4 = new RadioButton("4 Player");

        RadioButton autoSaveEnabled= new RadioButton("Enable AutoSave");
        RadioButton autoSaveDisabled = new RadioButton("Disable AutoSave");

        Button confirm = new Button("Confirm");
        confirm.setAlignment(Pos.BOTTOM_CENTER);

        ToggleGroup nPlayers = new ToggleGroup();
        player1.setToggleGroup(nPlayers);
        player2.setToggleGroup(nPlayers);
        player3.setToggleGroup(nPlayers);
        player4.setToggleGroup(nPlayers);

        ToggleGroup autoSave = new ToggleGroup();
        autoSaveEnabled.setToggleGroup(autoSave);
        autoSaveDisabled.setToggleGroup(autoSave);

        System.out.println("Sei nella newGameView!");

        VBox nPlayersButtons = new VBox(15, player1, player2, player3, player4);
        nPlayersButtons.setAlignment(Pos.CENTER_LEFT);

        VBox autoSaveButtons= new VBox(15, autoSaveEnabled, autoSaveDisabled);
        autoSaveButtons.setAlignment(Pos.CENTER_RIGHT);

        confirm.setOnAction(e -> controller.setupGameState(
                nPlayers.getSelectedToggle(),
                autoSave.getSelectedToggle())
            );

        ImageView imgSfondo = new ImageView(new Image(getClass().getResourceAsStream("/Forest.png")));

        StackPane root = new StackPane(imgSfondo, nPlayersButtons, autoSaveButtons, confirm);

        Scene scene = new Scene(root, 800, 600);
        imgSfondo.fitHeightProperty().bind(scene.heightProperty());
        imgSfondo.fitWidthProperty().bind(scene.widthProperty());

        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
