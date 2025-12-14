package com.game.view.gameview;

import javafx.stage.Stage;

public abstract class GameView {
    protected Stage stage;

    public GameView(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage()
    {
        return this.stage;
    }

    public abstract void show(); //Costruisce e mostra la view
    public abstract void showMessage(String msg); //Messaggi all'utente (UI)
}
