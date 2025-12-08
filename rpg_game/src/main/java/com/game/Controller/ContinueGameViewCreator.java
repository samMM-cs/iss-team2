package com.game.controller;

import com.game.view.GameView;
import com.game.view.ContinueGameView;

import javafx.stage.Stage;

public class ContinueGameViewCreator extends GameViewCreator{
    @Override
    public GameView createGameView(Stage stage) {
        return new ContinueGameView();
    }
}