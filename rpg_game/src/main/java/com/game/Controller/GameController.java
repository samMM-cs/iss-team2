package com.game.controller;

import com.game.model.GameState;
import com.game.view.ExplorationView;

import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class GameController {
    private GameState gameState;

    public GameController() {
    }

    public GameController(GameState gameState) {
        this.gameState = gameState;
    }

    public void startExploration(Stage stage) {
        ExplorationView explorationView = new ExplorationView(stage);
        explorationView.showMap();
    }

    public void handleStoryChoice(String choice) {
    }

    public void on1player(ActionEvent e) {

    }

    public GameState getGameState() {
        return gameState;
    }
}
