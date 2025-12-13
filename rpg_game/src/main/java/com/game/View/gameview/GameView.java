package com.game.view.gameview;

import com.game.controller.GameController;
import javafx.stage.Stage;

public abstract class GameView {
    protected Stage stage;
    protected GameController controller;

    public abstract void load();

    public abstract Stage getStage();

    public abstract void showMessage(String msg);
}
