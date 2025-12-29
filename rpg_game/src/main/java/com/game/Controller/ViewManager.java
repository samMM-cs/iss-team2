package com.game.controller;

import com.game.model.battle.Battle;
import com.game.view.CharacterSelectionView;
import com.game.view.MainMenuView;
import com.game.view.battleview.BattleView;
import com.game.view.gameview.NewGameView;
import com.game.view.mapview.ExplorationView;

import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ViewManager {
  private static ViewManager instance;
  private final Stage stage;

  private MainMenuView mainMenuView;
  private NewGameView newGameView;
  private CharacterSelectionView characterSelectionView;
  private ExplorationView explorationView;
  private BattleView battleView;

  private ViewManager(Stage stage) {
    this.stage = stage;
    this.stage.setTitle("RPG Game");
    this.stage.setWidth(Screen.getPrimary().getBounds().getWidth());
    this.stage.setHeight(Screen.getPrimary().getBounds().getHeight());
    this.stage.centerOnScreen();
    this.stage.setResizable(true);
    this.stage.setMaximized(true);
  }

  public void showMainMenu() {
    if (mainMenuView == null)
      mainMenuView = new MainMenuView();
    mainMenuView.show();
  }

  public void showNewGameView(GameController gameController) {
    if (newGameView == null)
      newGameView = new NewGameView(gameController);
    newGameView.show();
  }

  public void showCharacterSelectionView(GameController gameController) {
    if (characterSelectionView == null)
      characterSelectionView = new CharacterSelectionView(gameController);
    characterSelectionView.show();
  }

  public void showExplorationView() {
    if (explorationView == null)
      explorationView = new ExplorationView();
    explorationView.showMap();
  }

  public void showBattleView(Battle battle) {
    //if (battleView == null)
    explorationView.stop();
    battleView = new BattleView(battle);
    battleView.showBattle();
  }

  public void setAndShowScene(Scene scene) {
    stage.setScene(scene);
    stage.show();
  }

  public void exit() {
    stage.close();
  }

  public Stage getStage() {
    return stage;
  }

  public static ViewManager getInstance() {
    return instance;
  }

  public double getWidth() {
    return stage.getWidth();
  }

  public double getHeight() {
    return stage.getHeight();
  }

  public class ViewManagerBuilder {
    public static ViewManager build(Stage stage) {
      instance = new ViewManager(stage);
      return instance;
    }
  }
}
