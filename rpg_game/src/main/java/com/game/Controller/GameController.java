package com.game.controller;

import com.game.view.NewGameView;

import com.game.view.GameView;
import com.game.model.GameState;
import com.game.view.ExplorationView;

import javafx.stage.Stage;
import javafx.scene.control.Toggle;
import javafx.scene.control.RadioButton;

public class GameController {
    private GameState gameState;
    private GameView gameView;

    public GameController(NewGameView newGameView) {
        this.gameView= newGameView;
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

    public void setupGameState(Toggle nPlayers, Toggle autoSaveEnabler) throws ParsingButtonException {
        if (nPlayers == null) {
            gameView.showMessage("How many players want to play?");
            return;
        }
        if (autoSaveEnabler == null) {
            gameView.showMessage("Do you want to enable autoSave?");
            return;
        }

        String nPlayersString = ((RadioButton) nPlayers).getText();
        String autoSaveEnablerString= ((RadioButton) autoSaveEnabler).getText();
        int nPlayersInt=0;
        boolean autoSaveEnablerBool=false;

        gameView.showMessage("Hai scelto: " + nPlayersString + " e " + autoSaveEnablerString);
        switch (nPlayersString) {
            case "1 Player":
                nPlayersInt= 1;
                break;
            case "2 Player":
                nPlayersInt= 2;
                break;
            case "3 Player":
                nPlayersInt= 3;
                break;
            case "4 Player":
                nPlayersInt= 4;
                break;
            default:
                throw new ParsingButtonException();         
        }
       //nPlayersInt= Integer.parseInt(nPlayersString.split(" ")[0]);
        try {
            switch (autoSaveEnablerString) {
                case "Enable AutoSave":
                    autoSaveEnablerBool= true;
                    break;
                case "Disable AutoSave":
                    autoSaveEnablerBool= false;
                    break;
                default:
                    throw new ParsingButtonException();
            }
            this.gameState= new GameState(nPlayersInt, autoSaveEnablerBool);
        }
        finally {}
    }
}

class ParsingButtonException extends RuntimeException {
    @Override
    public String toString() {
        return "Invalid Button value";
    }
}
