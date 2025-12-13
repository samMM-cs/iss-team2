package com.game.controller.gamecreation;

import com.game.view.gameview.GameView;

import javafx.stage.Stage;

public abstract class GameViewCreator {
    protected Stage stage;

    public abstract GameView createGameView();
}