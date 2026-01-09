package com.game.controller;

import com.game.controller.ViewManager;
import com.game.controller.exploration.*;
import com.game.model.Position;
import com.game.model.battle.Battle;
import com.game.model.character.Player;
import com.game.model.creator.Game;
import com.game.model.character.NPC;
import com.game.model.character.Enemy;
import com.game.model.character.Job;
import com.game.model.GameState;
import com.game.view.mapview.MapView;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Queue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;

public class ExplorationControllerTest {
    Scene scene;
    MapView mapView;
    ExplorationController controller;
    Player player;

    @BeforeEach
    void setup() {
        scene = mock(Scene.class);
        mapView = mock(MapView.class);

        // Mock MapView per test del movimento
        when(mapView.getTileSize()).thenReturn(32);
        when(mapView.getMapWidth()).thenReturn(320.0);
        when(mapView.getMapHeight()).thenReturn(320.0);
        when(mapView.getWalkableTiles()).thenReturn(new boolean[10][10]);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mapView.getWalkableTiles()[i][j] = true;
            }
        }

        controller = new ExplorationController(scene, mapView);
        player = mock(Player.class);
        when(player.getPosition()).thenReturn(new Position(5, 5));

        GameState gameState = GameState.getInstance();
        gameState.createNpc();
        gameState.createParty();
    }

    @Test
    void testHandlePossibleInteractionsNearNPC() {
        NPC npc = mock(NPC.class);
        when(npc.getPosition()).thenReturn(new Position(4, 6));

        GameState.getInstance().getNpc().add(npc);

        ViewManager viewManager = mock(ViewManager.class);
        controller.update();

        verify(npc).interact(player);
        verify(viewManager).showDialogView(any(), eq(player), eq(npc));
    }

    @Test
    void testHandleBattle() {
        Enemy enemy = mock(Enemy.class);
        when(enemy.getPosition()).thenReturn(new Position(10, 10));
    }
}
