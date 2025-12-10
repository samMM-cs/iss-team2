package com.game.view;

import javafx.stage.Stage;

public class ContinueGameView extends GameView{
    @Override
    public void load() {
        return;
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }

    @Override
    public void showMessage(String msg) {
        System.out.println(msg);
    }
}
