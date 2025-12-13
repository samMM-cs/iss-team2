package com.game.view.gameview;

import com.game.controller.GameController;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * import javafx.scene.layout.Background;
 * import javafx.scene.layout.BackgroundImage;
 * import javafx.scene.layout.BackgroundPosition;
 * import javafx.scene.layout.BackgroundRepeat;
 * import javafx.scene.layout.BackgroundSize;
 * import javafx.stage.Screen;
 */
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.CheckBox;

public class NewGameView extends GameView {
    public static final int MAX_PLAYER = 4;

    public NewGameView(Stage stage) {
        this.stage = stage;
        this.controller = new GameController(this);
    }

    @Override
    public void load() {
        RadioButton player1 = new RadioButton("1 Player");
        RadioButton player2 = new RadioButton("2 Player");
        RadioButton player3 = new RadioButton("3 Player");
        RadioButton player4 = new RadioButton("4 Player");

        ToggleGroup nPlayers = new ToggleGroup();
        player1.setToggleGroup(nPlayers);
        player2.setToggleGroup(nPlayers);
        player3.setToggleGroup(nPlayers);
        player4.setToggleGroup(nPlayers);

        CheckBox autoSaveEnabler = new CheckBox("AutoSave");

        Button confirm = new Button("Confirm");
        confirm.setAlignment(Pos.BOTTOM_CENTER);

        System.out.println("Sei nella newGameView!");

        VBox nPlayersButtons = new VBox(15, player1, player2, player3, player4);
        nPlayersButtons.setAlignment(Pos.CENTER_LEFT);

        autoSaveEnabler.setAlignment(Pos.CENTER_RIGHT);

        confirm.setOnAction(e -> controller.setupGameState(
                nPlayers.getSelectedToggle(),
                autoSaveEnabler.isSelected()));

        GridPane availableChoicesGrid = new GridPane();

        availableChoicesGrid.setHgap(50); // spazio tra colonne
        availableChoicesGrid.setVgap(15); // spazio tra righe
        availableChoicesGrid.setAlignment(Pos.CENTER);

        // Colonna sinistra (nPlayers)
        availableChoicesGrid.add(nPlayersButtons, 0, 0);

        // Colonna destra (autoSave)
        availableChoicesGrid.add(autoSaveEnabler, 2, 0);

        // Bottone sotto (su 2 colonne)
        availableChoicesGrid.add(confirm, 1, 1, 2, 1);

        ImageView imgSfondo = new ImageView(new Image(getClass().getResourceAsStream("/Forest.png")));

        /*
         * Background bg= new Background(
         * new BackgroundImage(
         * new Image(getClass().getResourceAsStream("/Forest.png")),
         * BackgroundRepeat.NO_REPEAT,
         * BackgroundRepeat.NO_REPEAT,
         * BackgroundPosition.CENTER,
         * new BackgroundSize(
         * 100, 100, true, true, true, false
         * )
         * )
         * );
         * availableChoicesGrid.setBackground(bg);
         */

        StackPane root = new StackPane(imgSfondo, availableChoicesGrid);

        Scene scene = new Scene(root, 800, 600);

        // Adatto lo sfondo rispetto alla dimensione della finestra
        imgSfondo.fitHeightProperty().bind(scene.heightProperty());
        imgSfondo.fitWidthProperty().bind(scene.widthProperty());

        /*
         * nPlayersButtons.
         * setStyle("-fx-border-color: red; -fx-background-color: rgba(255,0,0,0.2);");
         * autoSaveButtons.
         * setStyle("-fx-border-color: blue; -fx-background-color: rgba(0,0,255,0.2);");
         * confirm.setStyle("-fx-border-color: green;");
         * root.setStyle("-fx-border-color: yellow;");
         */

        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
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
