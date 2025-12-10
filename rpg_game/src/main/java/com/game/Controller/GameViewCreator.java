package com.game.controller;

import com.game.view.GameView;

import javafx.stage.Stage;

public abstract class GameViewCreator {
    protected Stage stage;

    public abstract GameView createGameView();
}