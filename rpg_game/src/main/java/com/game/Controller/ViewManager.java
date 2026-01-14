package com.game.controller;

import java.io.IOException;

import com.game.model.GameState;
import com.game.model.battle.Battle;
import com.game.model.character.NPC;
import com.game.model.character.Player;
import com.game.model.character.Party;
import com.game.view.CharacterSelectionView;
import com.game.view.DialogueView;
import com.game.view.ShopView;
import com.game.view.StoryView;
import com.game.view.battleview.BattleView;
import com.game.view.gameview.MainMenuView;
import com.game.view.gameview.NewGameView;
import com.game.view.gameview.ContinueGameView;
import com.game.view.gameview.PauseMenu;
import com.game.view.gameview.SaveMenuView;
import com.game.view.mapview.ExplorationView;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
  private ContinueGameView continueGameView;
  private PauseMenu pauseMenu;
  private SaveMenuView saveMenuView;
  private CharacterSelectionView characterSelectionView;
  private ExplorationView explorationView;
  private BattleView battleView;
  private DialogueView dialogView;
  private ShopView shopView;
  private boolean paused = false;
  private Pane root;
  private GameController gameController;
  private StoryView storyView;

  public StoryView getStoryView() {
    return storyView;
  }

  private ViewManager(Stage stage) {
    this.stage = stage;
    this.stage.setTitle("The Cycle");
    this.stage.setWidth(Screen.getPrimary().getBounds().getWidth());
    this.stage.setHeight(Screen.getPrimary().getBounds().getHeight());
    this.stage.centerOnScreen();
    this.stage.setResizable(true);
    this.stage.setMaximized(true);
  }

  public void destroyViews() {
    pauseMenu = null;
    explorationView = null;
    newGameView = null;
  }

  public void showMainMenu() {
    if (mainMenuView == null)
      mainMenuView = new MainMenuView();
    if (explorationView != null)
      explorationView.stop();
    mainMenuView.show();
  }

  public void showNewGameView(NewGameController gameController) {
    if (this.gameController == null)
      this.gameController = gameController;
    if (newGameView == null)
      newGameView = new NewGameView(gameController);
    newGameView.show();
  }

  public void showContinueGameView(GameController gameController) {
    if (this.gameController == null)
      this.gameController = gameController;
    root = (Pane) stage.getScene().getRoot();
    if (continueGameView == null)
      continueGameView = new ContinueGameView(gameController);
    if (!root.getChildren().contains(continueGameView)) {
      continueGameView.prefWidthProperty().bind(root.widthProperty());
      continueGameView.prefHeightProperty().bind(root.heightProperty());
      root.getChildren().add(continueGameView);
    }
    continueGameView.toFront();
    continueGameView.show();
  }

  // Menu pausa
  public void initPauseMenu(Scene scene, GameController gameController) {
    if (pauseMenu == null) {
      pauseMenu = new PauseMenu(gameController);
      pauseMenu.setVisible(false);
      pauseMenu.prefWidthProperty().bind(scene.widthProperty());
      pauseMenu.prefHeightProperty().bind(scene.heightProperty());

      ((Pane) scene.getRoot()).getChildren().add(pauseMenu);
    }
  }

  // SaveMenu
  public void showSaveMenu(Scene scene, GameController gameController) {
    root = ((Pane) scene.getRoot());
    if (saveMenuView == null) {
      saveMenuView = new SaveMenuView(gameController);
      saveMenuView.setVisible(true);
      saveMenuView.prefWidthProperty().bind(scene.widthProperty());
      saveMenuView.prefHeightProperty().bind(scene.heightProperty());

      root.getChildren().add(saveMenuView);
      saveMenuView.setVisible(true);
      saveMenuView.toFront();
      root.getChildren().forEach(node -> {
        if (node != saveMenuView)
          node.setEffect(null);
      });
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
      explorationView.start();
      pauseMenu.setVisible(paused);
    }
  }

  public void showCharacterSelectionView(NewGameController gameController) {
    if (characterSelectionView == null)
      characterSelectionView = new CharacterSelectionView(gameController);
    if (this.gameController == null)
      this.gameController = gameController;
    characterSelectionView.show();
  }

  public void showExplorationView() {
    if (explorationView == null) {
      explorationView = new ExplorationView(GameState.getInstance().getMap(), gameController);
    }
    try {
      gameController.getSaveManager().autosave(GameState.getInstance());
    } catch (IOException e) {
      System.err.println("Failed to autosave");
      e.printStackTrace();
    }

    if (isUIVisible()) {
      if (battleView != null)
        battleView.setVisible(false);
      if (dialogView != null)
        dialogView.setVisible(false);
      if (shopView != null)
        shopView.setVisible(false);
    }

    explorationView.showMap();
    explorationView.start();
  }

  public void showBattleView(Battle battle) {
    explorationView.stop();
    battleView = new BattleView(battle);
    battleView.showBattle();
    try {
      gameController.getSaveManager().autosave(null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showCorruptSave(int slot) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error while loading the save");
    alert.setHeaderText("Slot " + slot + " contains a corrupt save file");
    alert.showAndWait();
  }

  public void showCorruptSave() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error while loading the save");
    alert.setHeaderText("Autosave slot is corrupted");
    alert.showAndWait();
  }

  public void showStory(Scene scene) {
    root = (Pane) scene.getRoot();

    if (dialogView == null) {
      dialogView = ViewManager.getInstance().getDialogView();
    }

    if (storyView == null)
      storyView = new StoryView(dialogView);

    if (!root.getChildren().contains(storyView))
      root.getChildren().add(storyView);
    // explorationView.stop();// Blocca il movimento

    // Mostra la storia corrente
    if (GameState.getInstance().getCurrentStoryNode().getTrigger().test(GameState.getInstance())
        && !GameState.getInstance().getStoryShown())
      storyView.show(GameState.getInstance().getCurrentStoryNode(), GameState.getInstance());
  }

  public void showDialogView(Scene scene, Player player, NPC target) {
    root = (Pane) scene.getRoot();
    if (dialogView == null)
      dialogView = ViewManager.getInstance().getDialogView();

    if (!root.getChildren().contains(dialogView))
      root.getChildren().add(dialogView);

    // Blocco il movimento una volta aperto il dialogo
    explorationView.stop();

    // dialogView.handleAdvance();

    dialogView.showDialogue(target.getDialogue());

    dialogView.setOnCloseClick(() -> {
      showShop(player, target, GameState.getInstance().getParty());
      dialogView.setVisible(false);
      // Una volta finito il player riprende il movimento
      explorationView.start();
    });
  }

  public void showShop(Player player, NPC npc, Party party) {
    root = (Pane) ViewManager.getInstance().getStage().getScene().getRoot();
    if (shopView == null) {
      shopView = new ShopView(party);
      root.getChildren().add(shopView);
    }
    shopView.open(npc);
    shopView.toFront();
  }

  public void setAndShowScene(Scene scene) {
    stage.setScene(scene);
    stage.show();
  }

  public boolean isUIVisible() {
    return (this.battleView != null && this.battleView.isVisible())
        || (this.dialogView != null && this.dialogView.isVisible())
        || (this.shopView != null && this.shopView.isVisible())
        || (this.pauseMenu != null && this.pauseMenu.isVisible());
  }

  public void exit() {
    stage.close();
  }

  public Stage getStage() {
    return stage;
  }

  public DialogueView getDialogView() {
    if (dialogView == null) {
      dialogView = new DialogueView();
      root = (Pane) stage.getScene().getRoot();
      root.getChildren().add(dialogView);
    }
    return dialogView;
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

  public void updateMaps() {
    explorationView = null;
    pauseMenu = null;
    showExplorationView();
  }

}