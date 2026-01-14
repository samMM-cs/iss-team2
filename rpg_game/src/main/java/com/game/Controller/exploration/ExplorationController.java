package com.game.controller.exploration;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import com.game.controller.StoryController;
import com.game.controller.ViewManager;
import com.game.model.GameState;
import com.game.model.Position;
import com.game.model.SerializablePredicate.CanChangeMap;
import com.game.model.battle.Battle;
import com.game.model.character.Player;
import com.game.model.character.NPC;
import com.game.model.character.Party;
import com.game.model.character.Enemy;
import com.game.view.mapview.MapView;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class ExplorationController {
    private Scene scene;

    private Queue<KeyCode> activeKeys = new LinkedList<>();
    private Position posLimit;
    private MapView mapView;
    private static final Set<KeyCode> movementKeys = Set.of(KeyCode.W, KeyCode.A, KeyCode.S,
            KeyCode.D, KeyCode.DOWN, KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT);
    private Position prevPosition = null;
    private boolean battleStarted = false;
    private final StoryController storyController= new StoryController();

    public ExplorationController(Scene scene, MapView mapView) {
        this.scene = scene;
        this.mapView = mapView;
        this.scene.setOnKeyPressed(e -> {
            activeKeys.offer(e.getCode());
        });
        // Listener to update limits when window gets resized
        this.scene.widthProperty().addListener((obs, oldVal, newVal) -> updatePositionLimit());
        this.scene.heightProperty().addListener((obs, oldVal, newVal) -> updatePositionLimit());

        updatePositionLimit();
    }

    private void updatePositionLimit() {
        int tileSize = mapView.getTileSize();
        int maxTilesX = (int) (mapView.getMapWidth() / tileSize);
        int maxTilesY = (int) (mapView.getMapHeight() / tileSize);
        int maxX = Math.max(0, maxTilesX - 1);
        int maxY = Math.max(0, maxTilesY - 1);
        this.posLimit = new Position(maxX, maxY);
    }

    public void update() {
        GameState gameState = GameState.getInstance();
        if (gameState == null)
            return;

        // Cambio mappa se necessario
        if (gameState.getCurrentStoryNode().getTrigger() instanceof CanChangeMap
                && gameState.getCurrentStoryNode().getTrigger().test(gameState)) {
            //gameState.nextMap();
            //ViewManager.getInstance().updateMaps();
            prevPosition = null;
            //return;
        }

        // Controllo battaglia
        Optional<Enemy> optEnemy = gameState.getEnemies().stream()
                .filter(enemy -> enemy.getPosition().equals(
                        gameState.getParty().getMainPlayer().getPosition()))
                .findFirst();

        if (!battleStarted && optEnemy.isPresent()) {
            battleStarted = true;
            optEnemy.ifPresent(this::handleBattle);
            battleStarted = false;
            return; // blocca update finché la battaglia non parte
        }

        // Prendo il tasto premuto
        KeyCode key = activeKeys.poll();

        // Se il gioco è in pausa o c'è qualsiasi overlay UI aperto, blocco tutto
        if (ViewManager.getInstance().isPaused() || ViewManager.getInstance().isUIVisible()) {
            return;
        }

        // Movimento player
        if (key != null && movementKeys.contains(key)) {
            movePlayer(key);
        }

        // Interazione con NPC
        if (key == KeyCode.E) {
            handlePossibleInteractions();
        }

        /*System.out.println(gameState.getCurrentStoryNode().getName() + ", "
                + ViewManager.getInstance().getDialogView().isVisible() + ", "
                + ViewManager.getInstance().getStoryView());*/

        // Mostra la storia solo se c'è un nodo e nessun dialogo aperto
        if (gameState.getCurrentStoryNode() != null
                && !ViewManager.getInstance().getDialogView().isVisible()
                && gameState.getCurrentStoryNode().getTrigger().test(gameState)
                && !gameState.getStoryShown()) {
            //ViewManager.getInstance().showStory();
            //new StoryController().enter();
            storyController.enter();
        }
    }

    public void handlePossibleInteractions() {
        NPC target = null;
        Party party = GameState.getInstance().getParty();
        Player mainPlayer = party.getMainPlayer();
        for (NPC npc : GameState.getInstance().getNpc()) {
            if (Math.abs(npc.getPosition().sub(mainPlayer.getPosition()).max()) <= 1) {
                target = npc;
                break;
            }
        }

        if (target != null) {
            ViewManager.getInstance().showDialogView(scene, mainPlayer, target);
            target.interact(party);
        }
    }

    public void handleBattle(Enemy e) {
        // Versatile for multiple enemy
        Battle battle = new Battle(e);

        ViewManager.getInstance().showBattleView(battle);
        if (prevPosition != null)
            GameState.getInstance().getParty().updateFollowPosition(prevPosition);
        System.out.println("starting battle with: " + e.toString());
    }

    public void movePlayer(KeyCode key) {
        Player mainPlayer = GameState.getInstance().getParty().getMainPlayer();
        int dx = 0;
        int dy = 0;

        if (key == KeyCode.W || key == KeyCode.UP)
            dy -= 1;
        if (key == KeyCode.A || key == KeyCode.LEFT)
            dx -= 1;
        if (key == KeyCode.S || key == KeyCode.DOWN)
            dy += 1;
        if (key == KeyCode.D || key == KeyCode.RIGHT)
            dx += 1;

        if (dx != 0 || dy != 0) {
            Position nextPosition = mainPlayer.getPosition().add(dx, dy);
            if (canGoThere(nextPosition)) {
                prevPosition = mainPlayer.getPosition();
                GameState.getInstance().getParty().updateFollowPosition(nextPosition);
                mapView.updatePlayerPosition(nextPosition.scale(mapView.getTileSize()));
            }
        }
    }

    public boolean canGoThere(Position nextPosition) {
        return nextPosition.isInside(posLimit) // player isn't going outside the map
                // player isn't walking on some obstacle
                && this.mapView.getWalkableTiles()[(int) nextPosition.x()][(int) nextPosition.y()]
                // prevent player from trampling npcs
                && !GameState.getInstance().getNpc().stream().anyMatch(npc -> npc.getPosition().equals(nextPosition));
    }

    public Position getPosLimit() {
        return posLimit;
    }

    public Queue<KeyCode> getActiveKeys() {
        return activeKeys;
    }

    public Position getPrevPosition() {
        return prevPosition;
    }
}
