package com.game.controller;

import com.game.view.GameView;
import com.game.view.NewGameView;

import javafx.stage.Stage;

public class NewGameViewCreator extends GameViewCreator{
    @Override
    public GameView createGameView(Stage stage) {
        return new NewGameView(stage);
    }
}