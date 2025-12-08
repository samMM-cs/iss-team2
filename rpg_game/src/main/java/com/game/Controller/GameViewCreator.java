package com.game.controller;

import com.game.view.GameView;

import javafx.stage.Stage;

public abstract class GameViewCreator {
    public abstract GameView createGameView(Stage stage);
}