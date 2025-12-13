package com.game.controller.gamecreation;

import com.game.view.gameview.ContinueGameView;
import com.game.view.gameview.GameView;

//import javafx.stage.Stage;

public class ContinueGameViewCreator extends GameViewCreator {
    @Override
    public GameView createGameView() {
        return new ContinueGameView();
    }
}