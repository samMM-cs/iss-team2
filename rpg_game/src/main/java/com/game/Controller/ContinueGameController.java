package com.game.controller;

public class ContinueGameController extends GameController {
    @Override
    public void start() {
        ViewManager.getInstance().showContinueGameView(this);
    }
}
