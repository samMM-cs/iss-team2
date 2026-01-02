package com.game.view.battleview;

import com.game.controller.BattleController;
import com.game.controller.ViewManager;
import com.game.model.GameState;
import com.game.model.battle.Battle;
import com.game.model.character.Enemy;
import com.game.model.character.Party;
import com.game.model.character.Player;
import com.game.model.character.Job;
import com.game.view.HUD;
import com.game.model.battle.*;

import javafx.animation.AnimationTimer;
import javafx.scene.control.ListView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

import java.util.List;

public class BattleView extends Pane {

    private Battle battle;
    private final Party party;

    private BattleController controller;

    private final Canvas canvas;
    private final GraphicsContext gc;

    private final BorderPane uiOverlay;
    private final ListView<String> actionList = new ListView<>();
    private final ListView<String> moveList = new ListView<>();

    private Image backgroundImage;
    private final Image[] heart_img = { new Image("/battle/icons/heart/Sprite_heart.png"),
            new Image("/battle/icons/heart/Sprite_heart_2.png"),
            new Image("/battle/icons/heart/Sprite_heart_3.png"),
            new Image("/battle/icons/heart/Sprite_heart_4.png") };

    // ---- Stato animazioni ----
    private double playerAttackOffset = 0;
    private int activePlayerIndex = 0;

    public BattleView(Battle battle) {
        this.battle = battle;
        this.party = GameState.getInstance().getParty();
        this.controller = new BattleController(battle, this);

        uiOverlay = new BorderPane();

        setPrefSize(800, 600);

        canvas = new Canvas();
        canvas.widthProperty().bind(widthProperty());
        canvas.heightProperty().bind(heightProperty());
        gc = canvas.getGraphicsContext2D();

        initUI();
        initGameLoop();
    }

    // ---------------- UI ----------------

    private void initUI() {
        backgroundImage = new Image("/battle/Battleground.png");
        getChildren().add(canvas);

        actionList.getItems().addAll("Move", "Item", "Flee");
        actionList.setOrientation(javafx.geometry.Orientation.HORIZONTAL);
        actionList.setPrefHeight(45);
        actionList.setStyle("-fx-font-size: 14px;");
        actionList.setOnMouseClicked(e -> {
            String selected = actionList.getSelectionModel().getSelectedItem();
            if (selected != null)
                controller.handleAction(selected);
        });

        moveList.setOrientation(javafx.geometry.Orientation.VERTICAL);
        moveList.setStyle("-fx-font-size: 14px;");
        moveList.setVisible(false); // nascosta di default
        moveList.setManaged(false); // IMPORTANTISSIMO per layout
        moveList.setOnMouseClicked(e -> {
            String move = moveList.getSelectionModel().getSelectedItem();
            if (move != null)
                controller.handleMoveSelection(move);
        });

        HBox bottomBox = new HBox(10, actionList, moveList);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));
        bottomBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-background-radius: 10;");

        HBox playersBox = new HBox(15);
        playersBox.setPadding(new Insets(20));
        playersBox.getChildren().addAll(
                party.getMembers().stream().map(HUD::new).toList());

        uiOverlay.setTop(playersBox);
        uiOverlay.setBottom(bottomBox);
        uiOverlay.prefWidthProperty().bind(widthProperty());
        uiOverlay.prefHeightProperty().bind(heightProperty());
        uiOverlay.setPickOnBounds(false);

        getChildren().add(uiOverlay);
    }

    private void resizeListViewToFitItems(ListView<?> list) {
        list.setFixedCellSize(36);
        double height = list.getItems().size() * list.getFixedCellSize() + 4;
        list.setPrefHeight(height);
        list.setMaxHeight(height);
    }

    public void updateMoveList(List<String> moves) {
        moveList.getItems().clear();
        moveList.getItems().addAll(moves);
        moveList.getItems().add("Back");// Per dare sempre la possibilità di tornare indietro
        resizeListViewToFitItems(moveList);
    }

    public void showMoveList() {
        actionList.setVisible(false);
        actionList.setManaged(false);

        moveList.setVisible(true);
        moveList.setManaged(true);
    }

    public void hideMoveList() {
        moveList.setVisible(false);
        moveList.setManaged(false);

        actionList.setVisible(true);
        actionList.setManaged(true);

        moveList.getSelectionModel().clearSelection();
    }

    public void setActivePlayer(int index) {
        this.activePlayerIndex = index;
    }

    // ---------------- GAME LOOP ----------------

    private void initGameLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        }.start();
    }

    private void update() {
        // easing dell'animazione di scatto in avanti
        playerAttackOffset *= 0.85;
        if (Math.abs(playerAttackOffset) < 0.1)
            playerAttackOffset = 0;
    }

    // ---------------- RENDER ----------------

    private void render() {
        double w = getWidth();
        double h = getHeight();
        if (w <= 0 || h <= 0)
            return;

        gc.clearRect(0, 0, w, h);

        // SFONDO
        gc.drawImage(backgroundImage, 0, 0, w, h);
        gc.setImageSmoothing(false);

        double groundY = h * 0.8;

        drawPlayers(w, groundY, h);
        drawEnemy(w, groundY, h);
    }

    private void drawPlayers(double w, double groundY, double h) {
        List<Player> members = party.getMembers();
        double size = Math.floor(h * 0.3);

        for (int i = 0; i < members.size(); i++) {
            Player p = members.get(i);
            if (p.getCurrentStats().getHp() <= 0)
                continue; // Non disegnare i morti o falli vedere diversamente

            double x = w * 0.10 + i * (size * 0.6);
            // Applichiamo l'offset solo se è il turno di questo giocatore
            double currentX = (i == activePlayerIndex) ? x + playerAttackOffset : x;
            double y = groundY - size;

            // --- INDICATORE TURNO ---
            if (i == activePlayerIndex && actionList.isDisabled() == false) {
                drawTurnIndicator(currentX, groundY, size);
            }

            gc.save();
            // Flip per guardare verso destra (il nemico)
            gc.translate(currentX + size / 2, 0);
            gc.scale(-1, 1);
            gc.translate(-currentX - size / 2, 0);

            gc.drawImage(p.getImg(), p.getJob().getX(), p.getJob().getY(), Job.SIZE, Job.SIZE,
                    Math.floor(currentX), Math.floor(y), size, size);
            gc.restore();
        }
    }

    // --- NUOVO: Disegna un cerchio luminoso sotto il giocatore attivo ---
    private void drawTurnIndicator(double x, double y, double size) {
        gc.setFill(Color.rgb(255, 255, 255, 0.4));
        gc.fillOval(x + size * 0.1, y - 10, size * 0.8, 15);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeOval(x + size * 0.1, y - 10, size * 0.8, 15);
    }

    private void drawEnemy(double w, double groundY, double h) {
        Enemy enemy = battle.getEnemy();
        if (enemy == null || enemy.getImg() == null)
            return;

        double size = Math.floor(h * 0.4);
        double x = Math.floor(w * 0.60);

        // Compensa lo spazio vuoto nello sprite sheet
        double enemyYOffset = size * 0.20;

        double y = Math.floor(groundY - size + enemyYOffset);

        int sx = enemy.getJob().getX();
        int sy = enemy.getJob().getY();
        int sw = Job.SIZE;
        int sh = Job.SIZE;

        gc.drawImage(enemy.getImg(),
                sx, sy, sw, sh,
                x, y, size, size);

        drawEnemyHP(enemy, x, y, size);
    }

    private void drawEnemyHP(Enemy enemy, double x, double y, double size) {

        double barWidth = 110;
        double barHeight = 10;

        double hpPerc = Math.max(0, Math.min(1, enemy.getCurrentStats().getHpPerc()));
        double barX = x + (size - barWidth) / 2;
        double barY = y - 22;

        // Testo HP
        String hpText = enemy.getCurrentStats().getHp() + "/" + enemy.getCurrentStats().getMaxHp();
        gc.setFont(Font.font("Verdana", 11));

        // Ombra
        gc.setFill(Color.BLACK);
        gc.fillText(hpText, barX + barWidth / 2 - 1, barY - 4);

        // Testo bianco
        gc.setFill(Color.WHITE);
        gc.fillText(hpText, barX + barWidth / 2, barY - 5);

        // Icona cuore (frame in base alla percentuale)
        int frame = (int) Math.floor((1 - hpPerc) * (heart_img.length - 1));
        frame = Math.min(frame, heart_img.length - 1);
        double iconSize = 20;
        double iconX = barX - iconSize - 6;
        double iconY = barY - (iconSize - barHeight) / 2;

        gc.drawImage(heart_img[frame], iconX, iconY, iconSize, iconSize);

        // Cornice esterna pixel-art
        gc.setFill(Color.BLACK);
        gc.fillRect(barX - 2, barY - 2, barWidth + 4, barHeight + 4);

        // Sfondo barra
        gc.setFill(Color.rgb(60, 0, 0));
        gc.fillRect(barX, barY, barWidth, barHeight);

        // Riempimento rosso
        gc.setFill(new LinearGradient(
                barX, barY, barX + barWidth, barY,
                false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(255, 80, 80)),
                new Stop(1, Color.rgb(180, 0, 0))));
        gc.fillRect(barX, barY, barWidth * hpPerc, barHeight);

        // Bordo pixel-art
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(barX, barY, barWidth, barHeight);
    }

    public void setPlayerAttackOffset(double playerAttackOffset) {
        this.playerAttackOffset = playerAttackOffset;
    }

    // ---------------- INPUT ----------------

    public void disableInput() {
        actionList.setDisable(true);
        moveList.setDisable(true);
    }

    public void enableInput() {
        actionList.setDisable(false);
        moveList.setDisable(false);
    }

    // ---------------- SCENE ----------------

    public void showBattle() {
        Scene scene = new Scene(this, ViewManager.getInstance().getWidth(),
                ViewManager.getInstance().getHeight());
        ViewManager.getInstance().setAndShowScene(scene);
    }
}