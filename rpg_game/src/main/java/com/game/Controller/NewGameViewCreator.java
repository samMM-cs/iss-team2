package com.game.controller;

import com.game.view.GameView;
import com.game.view.NewGameView;

import javafx.stage.Stage;

public class NewGameViewCreator extends GameViewCreator{
    
    public NewGameViewCreator(Stage stage) {
        this.stage= stage;
    }
    @Override
    public GameView createGameView() {
        return new NewGameView(this.stage);
    }
}