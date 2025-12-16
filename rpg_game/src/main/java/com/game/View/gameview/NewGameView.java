package com.game.view.gameview;

import com.game.controller.GameController;

import javafx.animation.FadeTransition;
import javafx.geometry.HPos;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.control.RadioButton;
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
        System.out.println("Sei nella newGameView!");

        RadioButton player1 = new RadioButton("1 Player");
        RadioButton player2 = new RadioButton("2 Player");
        RadioButton player3 = new RadioButton("3 Player");
        RadioButton player4 = new RadioButton("4 Player");
        player2.setSelected(true);

        ToggleGroup nPlayers = new ToggleGroup();
        player1.setToggleGroup(nPlayers);
        player2.setToggleGroup(nPlayers);
        player3.setToggleGroup(nPlayers);
        player4.setToggleGroup(nPlayers);

        RadioButton[] players = { player1, player2, player3, player4 };
        for (RadioButton rb : players) {
            rb.setStyle("-fx-text-fill: white; -fx-font-size: 16;");
            rb.setOnMouseEntered(e -> rb.setStyle("-fx-text-fill: gold; -fx-font-size: 16;"));
            rb.setOnMouseExited(e -> rb.setStyle("-fx-text-fill: white; -fx-font-size: 16;"));
        }

        Button confirm = new Button("Confirm");
        confirm.setAlignment(Pos.BOTTOM_CENTER);

        VBox nPlayersButtons = new VBox(15, players);
        nPlayersButtons.setAlignment(Pos.CENTER_LEFT);

        CheckBox autoSaveEnabler = new CheckBox("AutoSave");
        autoSaveEnabler.setStyle("-fx-text-fill: white; -fx-font-size: 16;");
        autoSaveEnabler.setOnMouseEntered(e -> autoSaveEnabler.setStyle("-fx-text-fill: gold; -fx-font-size: 16;"));
        autoSaveEnabler.setOnMouseExited(e -> autoSaveEnabler.setStyle("-fx-text-fill: white; -fx-font-size: 16;"));

        confirm.setOnAction(event -> {
            int player = getSelectedPlayers(nPlayers);
            gameController.onNewGameConfirmed(player, autoSaveEnabler.isSelected());
        });

        GridPane availableChoicesGrid = new GridPane();

        availableChoicesGrid.setHgap(50); // spazio tra colonne
        availableChoicesGrid.setVgap(20); // spazio tra righe
        availableChoicesGrid.setAlignment(Pos.CENTER);

        // Colonna sinistra (nPlayers)
        availableChoicesGrid.add(nPlayersButtons, 0, 0);

        // Colonna destra (autoSave)
        availableChoicesGrid.add(autoSaveEnabler, 1, 0);

        // Bottone sotto (su 2 colonne)
        availableChoicesGrid.add(confirm, 0, 1, 2, 1);
        GridPane.setHalignment(confirm, HPos.CENTER);

        ImageView imgSfondo = null;
        try {
            imgSfondo = new ImageView(new Image(getClass().getResourceAsStream("/Forest.png")));
        } catch (Exception e) {
            System.err.println("Errore nel caricamento del background, immagine non trovata");
        }

        Rectangle overlay = new Rectangle(800, 600, Color.rgb(0, 0, 0, 0.4));

        StackPane root = (imgSfondo != null) ? new StackPane(imgSfondo, overlay, availableChoicesGrid)
                : new StackPane(overlay, availableChoicesGrid);

        Scene scene = new Scene(root, 800, 600);

        // Adatto lo sfondo rispetto alla dimensione della finestra
        imgSfondo.fitHeightProperty().bind(scene.heightProperty());
        imgSfondo.fitWidthProperty().bind(scene.widthProperty());

        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();

        FadeTransition ft = new FadeTransition(Duration.seconds(1),availableChoicesGrid);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private int getSelectedPlayers(ToggleGroup group) {
        if (group.getSelectedToggle() == null)
            return -1;
        String txt = ((RadioButton) group.getSelectedToggle()).getText();
        return Integer.parseInt(txt.split(" ")[0]);
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
