package com.game.controller.exploration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import com.game.controller.ViewManager;
import com.game.model.Position;
import com.game.model.battle.Battle;
import com.game.model.character.Party;
import com.game.model.character.Player;
import com.game.model.character.NPC;
import com.game.model.character.Enemy;
import com.game.view.DialogueView;
import com.game.view.ShopView;
import com.game.view.mapview.MapView;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class ExplorationController {
    private Party party;
    private Scene scene;
    private List<Enemy> enemies = new ArrayList<>();
    private List<NPC> npcs = new ArrayList<>();

    private Queue<KeyCode> activeKeys = new LinkedList<>();
    private Position posLimit;
    private MapView mapView;
    private static final Set<KeyCode> movementKeys = Set.of(KeyCode.W, KeyCode.A, KeyCode.S,
            KeyCode.D, KeyCode.DOWN, KeyCode.UP, KeyCode.LEFT, KeyCode.RIGHT);
    private Position prevPosition = Position.Origin;
    private DialogueView dialogueView;
    private ShopView shopView;
    private boolean battleStarted = false;

    public ExplorationController(Party party, Scene scene, MapView mapView, List<Enemy> enemies, List<NPC> npc,
            DialogueView dialogueView, ShopView shopView) {
        this.scene = scene;
        this.party = party;
        this.mapView = mapView;
        this.enemies = enemies;
        this.npcs = npc;
        this.dialogueView = dialogueView;
        this.shopView = shopView;
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
        Optional<Enemy> optEnemy = this.enemies.stream()
                .filter(enemy -> enemy.getPos().equals(party.getMainPlayer().getPos())).findFirst();
        if (!battleStarted && optEnemy.isPresent()) {
            battleStarted = true;
            optEnemy.ifPresent(this::handleBattle);
        }

        KeyCode key = activeKeys.poll();
        if (key != null) {
            if (movementKeys.contains(key))
                movePlayer(key);

            if (dialogueView.isVisible()) {
                if (key == KeyCode.E)
                    dialogueView.handleAdvance();
                return;
            }
            if (key == KeyCode.E)
                handlePossibleInteractions();
        }
        updateSpritePositions();
    }

    private void handlePossibleInteractions() {
        NPC target = null;
        Player mainPlayer = party.getMainPlayer();
        for (NPC npc : npcs) {
            if (Math.abs(npc.getPos().sub(mainPlayer.getPos()).max()) <= 1) {
                target = npc;
                break;
            }
        }

        final NPC nearbyNPC = target;
        if (target != null) {
            dialogueView.showDialogue(target.getDialogue());
            dialogueView.setOnCloseClick(() -> {
                shopView.open(nearbyNPC);
            });

            target.interact(mainPlayer);

        }
    }

    private void handleBattle(Enemy e) {
        // Versatile for multiple enemy
        List<Enemy> enemiesList = new ArrayList<>();
        enemiesList.add(e);
<<<<<<< HEAD
        Battle battle = new Battle(e);
        battleView = new BattleView(this.stage, battle);
        battleView.showBattle();
=======
        Battle battle = new Battle(enemiesList);
        ViewManager.getInstance().showBattleView(battle);
>>>>>>> acb1b99a730cb37e4a809f0cbf9169caf4ee15b6

        party.updateFollowPosition(prevPosition);
        System.out.println("starting battle with: " + e.toString());
    }

    private void updateSpritePositions() {
        int tileSize = mapView.getTileSize();

        for (Player player : party.getMembers()) {
            if (player.getSprite() != null) {
                player.getSprite().setFitWidth(tileSize);
                player.getSprite().setFitHeight(tileSize);
                player.getSprite().setX(mapView.getOffset().x() + player.getPos().x() * tileSize);
                player.getSprite().setY(mapView.getOffset().y() + player.getPos().y() * tileSize);
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy.getSprite() != null) {
                enemy.getSprite().setFitWidth(tileSize);
                enemy.getSprite().setFitHeight(tileSize);
                enemy.getSprite().setX(mapView.getOffset().x() + enemy.getPos().x() * tileSize);
                enemy.getSprite().setY(mapView.getOffset().y() + enemy.getPos().y() * tileSize);
            }
        }
        for (NPC n : npcs) {
            if (n.getSprite() != null) {
                n.getSprite().setFitWidth(tileSize);
                n.getSprite().setFitHeight(tileSize);
                n.getSprite().setX(mapView.getOffset().x() + n.getPos().x() * tileSize);
                n.getSprite().setY(mapView.getOffset().y() + n.getPos().y() * tileSize);
            }
        }
    }

    void movePlayer(KeyCode key) {
        Player mainPlayer = party.getMainPlayer();
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
            Position nextPosition = mainPlayer.getPos().add(dx, dy);
            if (canGoThere(nextPosition)) {
                prevPosition = mainPlayer.getPos();
                party.updateFollowPosition(nextPosition);
                mapView.updatePlayerPosition(nextPosition.scale(mapView.getTileSize()));
            }
        }
    }

    public boolean canGoThere(Position nextPosition) {
        return nextPosition.isInside(posLimit) // player isn't going outside the map
                // player isn't walking on some obstacle
                && this.mapView.getWalkableTiles()[(int) nextPosition.x()][(int) nextPosition.y()]
                // prevent player from trampling npcs
                && !this.npcs.stream().anyMatch(npc -> npc.getPos().equals(nextPosition));
    }
}
