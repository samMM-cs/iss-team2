package com.game.controller;

import com.game.model.GameState;
import com.game.view.gameview.GameView;
import com.game.view.gameview.NewGameView;
import com.game.view.mapview.ExplorationView;

import javafx.stage.Stage;
import javafx.scene.control.Toggle;
import javafx.scene.control.RadioButton;

public class GameController {
    private GameState gameState;
    private GameView gameView;

    public GameController(NewGameView newGameView) {
        this.gameView = newGameView;
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

    public void setupGameState(Toggle nPlayers, boolean autoSaveEnabler) throws ParsingButtonException {
        if (nPlayers == null) {
            gameView.showMessage("How many players want to play?");
            return;
        }

        String nPlayersString = ((RadioButton) nPlayers).getText();
        int nPlayersInt = 0;

        gameView.showMessage("Hai scelto: " + nPlayersString + " e " + autoSaveEnabler);
        switch (nPlayersString) {
            case "1 Player":
                nPlayersInt = 1;
                break;
            case "2 Player":
                nPlayersInt = 2;
                break;
            case "3 Player":
                nPlayersInt = 3;
                break;
            case "4 Player":
                nPlayersInt = 4;
                break;
            default:
                throw new ParsingButtonException();
        }
        // nPlayersInt= Integer.parseInt(nPlayersString.split(" ")[0]);
        this.gameState = new GameState(nPlayersInt, autoSaveEnabler);

        ExplorationView map = new ExplorationView(this.gameView.getStage());
        map.showMap();
    }
}

class ParsingButtonException extends RuntimeException {
    @Override
    public String toString() {
        return "Invalid Button value";
    }
}
