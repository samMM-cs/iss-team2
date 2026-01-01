package com.game.controller;

import com.game.model.battle.Battle;
import com.game.model.character.NPC;
import com.game.model.character.Player;
import com.game.view.CharacterSelectionView;
import com.game.view.DialogueView;
import com.game.view.ShopView;
import com.game.view.battleview.BattleView;
import com.game.view.gameview.MainMenuView;
import com.game.view.gameview.NewGameView;
import com.game.view.gameview.PauseMenu;
import com.game.view.mapview.ExplorationView;

import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ViewManager {
  private static ViewManager instance;
  private final Stage stage;

  private MainMenuView mainMenuView;
  private NewGameView newGameView;
  private PauseMenu pauseMenu;
  private CharacterSelectionView characterSelectionView;
  private ExplorationView explorationView;
  private BattleView battleView;
  private DialogueView dialogView;
  private ShopView shopView;

  private boolean paused = false;
  private Pane root;

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

  // Menu pausa
  public void initPauseMenu(Scene scene) {
    if (pauseMenu == null) {
      pauseMenu = new PauseMenu();
      pauseMenu.setVisible(false);
      pauseMenu.prefWidthProperty().bind(scene.widthProperty());
      pauseMenu.prefHeightProperty().bind(scene.heightProperty());

      ((Pane) scene.getRoot()).getChildren().add(pauseMenu);
    }
  }

  public void enableGlobalPause(Scene scene) {
    scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        togglePause();
        e.consume();
      }
    });
  }

  public void togglePause() {
    if (pauseMenu == null)
      return;
    paused = !paused;
    root = (Pane) pauseMenu.getParent();
    if (paused) {
      pauseMenu.setVisible(paused);
      pauseMenu.toFront();
      explorationView.stop();
      root.getChildren().forEach(node -> {
        if (node != pauseMenu)
          node.setEffect(new GaussianBlur(10));
      });
    } else {
      root.getChildren().forEach(node -> {
        if (node != pauseMenu)
          node.setEffect(null);
      });
      explorationView.showMap();
      pauseMenu.setVisible(paused);
    }
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
    explorationView.stop();
    battleView = new BattleView(battle);
    battleView.showBattle();
  }

  public void showDialogView(Scene scene, Player player, NPC target) {
    System.out.println("Apro la DialogueView");
    explorationView.stop();
    root = (Pane) scene.getRoot();
    if (dialogView == null) {
      dialogView = new DialogueView();
      if (dialogView.isVisible()) {
        dialogView.handleAdvance();
        explorationView.stop();
      } else
        explorationView.showMap();
      dialogView.showDialogue(target.getDialogue());
      dialogView.setOnCloseClick(() -> {
        showShop(player, target);
        dialogView.setVisible(false);
        dialogView = null;
      });
      root.getChildren().add(dialogView);
    }
  }

  public void showShop(Player player, NPC npc) {
    root = (Pane) ViewManager.getInstance().getStage().getScene().getRoot();
    if (shopView == null) {
      shopView = new ShopView(player);
      root.getChildren().add(shopView);
    }
    shopView.open(npc);
    shopView.toFront();
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

  public boolean isPaused() {
    return this.paused;
  }

  public class ViewManagerBuilder {
    public static ViewManager build(Stage stage) {
      instance = new ViewManager(stage);
      return instance;
    }
  }

}
