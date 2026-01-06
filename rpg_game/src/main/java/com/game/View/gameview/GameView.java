package com.game.view.gameview;

import javafx.scene.layout.Pane;

public abstract class GameView extends Pane {

    public abstract void show(); // Costruisce e mostra la view

    public abstract void showMessage(String msg); // Messaggi all'utente (UI)
}
