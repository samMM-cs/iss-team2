package com.game.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.game.controller.exploration.ExplorationController;
import com.game.controller.exploration.MapBuilder;
import com.game.model.GameState;
import com.game.model.Position;
import com.game.model.WorldPosition;
import com.game.model.character.Job;
import com.game.model.map.Map1;
import com.game.model.map.Map2;
import com.game.model.map.Map3;
import com.game.model.story.FlagMap;
import com.game.view.mapview.MapView;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class ExplorationControllerTest {
    Scene scene;

    @BeforeEach
    public void setup() {
        scene = new Scene(new Pane());
        trueGameState();
    }

    @BeforeAll
    public static void init() {
        try {
            Platform.startup(() -> {
            });
        } catch (Exception e) {
        }
    }

    @Test
    public void T1_ExplorationController() {
        assertDoesNotThrow(() -> {
            new ExplorationController(scene, niceMapView());
        }, "Controller creation failed");
    }

    @Test
    public void T2_PositionLimitFromController() {
        ExplorationController contr = new ExplorationController(scene, niceMapView());
        assertEquals(contr.getPosLimit(), new Position(49, 49));
    }

    @Test
    public void T3_UpdateCallsMovePlayer() {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        contr.getActiveKeys().offer(KeyCode.W);

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);

            contr.update();

            verify(contr).movePlayer(KeyCode.W);
        }
    }

    @Test
    public void T4_UpdateStartsBattle() {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        GameState.getInstance().getParty()
                .updateFollowPosition(GameState.getInstance().getEnemies().get(0).getPosition());

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();
            verify(contr).handleBattle(GameState.getInstance().getEnemies().get(0));
        }
    }

    @Test
    public void T5_MovementUp() {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        GameState.getInstance().getParty().updateFollowPosition(new Position(20, 20));

        contr.getActiveKeys().offer(KeyCode.W);

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();

            assertEquals(GameState.getInstance().getParty().getMainPlayer().getPosition(), new Position(20, 19));
        }
    }

    @Test
    public void T6_MovementRight() {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        GameState.getInstance().getParty().updateFollowPosition(new Position(20, 20));

        contr.getActiveKeys().offer(KeyCode.D);

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();

            assertEquals(GameState.getInstance().getParty().getMainPlayer().getPosition(), new Position(21, 20));
        }
    }

    @Test
    public void T7_NoMovement() {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        GameState.getInstance().getParty().updateFollowPosition(new Position(20, 20));

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();

            assertEquals(GameState.getInstance().getParty().getMainPlayer().getPosition(), new Position(20, 20));
        }
    }

    @Test
    public void T8_ChangeDirection() {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        GameState.getInstance().getParty().updateFollowPosition(new Position(20, 20));
        contr.getActiveKeys().offer(KeyCode.W);
        contr.getActiveKeys().offer(KeyCode.D);

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();
            contr.update();

            assertEquals(GameState.getInstance().getParty().getMainPlayer().getPosition(), new Position(21, 19));
        }
    }

    @Test
    public void T9_Border() {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        Position pos = new Position(contr.getPosLimit().x(), 20);
        GameState.getInstance().getParty().updateFollowPosition(pos);
        contr.getActiveKeys().offer(KeyCode.D);

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();

            assertEquals(GameState.getInstance().getParty().getMainPlayer().getPosition(), pos);
        }
    }

    @Test
    public void T10_ValidMovementCallsUpdatePlayerPositions() throws Exception {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        contr.getActiveKeys().offer(KeyCode.D);

        // spy the Party and inject into GameState singleton
        var realParty = GameState.getInstance().getParty();
        var spyParty = spy(realParty);
        var partyField = GameState.class.getDeclaredField("party");
        partyField.setAccessible(true);
        partyField.set(GameState.getInstance(), spyParty);

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();

            verify(spyParty).updateFollowPosition(any());
        }
    }

    @Test
    public void T11_UpdatePositions() {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        GameState.getInstance().getParty().updateFollowPosition(new Position(20, 20));
        contr.getActiveKeys().offer(KeyCode.D);

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();

            assertEquals(GameState.getInstance().getParty().getMainPlayer().getPosition(), new Position(21, 20));
            assertEquals(GameState.getInstance().getParty().getMembers().get(1).getPosition(), new Position(20, 20));
        }
    }

    @Test
    public void T12_UpdateProcessesOneKeyAtATime() {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        GameState.getInstance().getParty().updateFollowPosition(new Position(20, 20));

        contr.getActiveKeys().offer(KeyCode.W);
        contr.getActiveKeys().offer(KeyCode.A);
        contr.getActiveKeys().offer(KeyCode.S);

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();

            assertEquals(GameState.getInstance().getParty().getMainPlayer().getPosition(), new Position(20, 19));
        }
    }

    @Test
    public void T13_FirstInput() {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        GameState.getInstance().getParty().updateFollowPosition(new Position(20, 20));
        assertNull(contr.getPrevPosition());
        contr.getActiveKeys().offer(KeyCode.W);

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();

            assertEquals(GameState.getInstance().getParty().getMainPlayer().getPosition(), new Position(20, 19));
        }
    }

    @Test
    public void T14_ConstructorThrowsWithNullScene() {
        assertThrows(NullPointerException.class, () -> {
            new ExplorationController(null, niceMapView());
        });
    }

    @Test
    public void T15_InteractsNearbyNpc() {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        Position pos = GameState.getInstance().getNpc().get(0).getPosition().add(new Position(-1, 0));
        GameState.getInstance().getParty().updateFollowPosition(pos);
        contr.getActiveKeys().offer(KeyCode.E);

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();
            verify(contr).handlePossibleInteractions();
        }
    }

    @Test
    public void T16_HandleBattleStartsBattle() throws Exception {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        Position battleStartPos = GameState.getInstance().getEnemies().get(0).getPosition();
        GameState.getInstance().getParty().updateFollowPosition(battleStartPos);

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();
            verify(contr).handleBattle(any());
            verify(vminst).showBattleView(any());
        }
    }

    @Test
    public void T17_CanGoThere() {
        ExplorationController contr = spy(new ExplorationController(scene, niceMapView()));
        GameState.getInstance().getParty().updateFollowPosition(new Position(20, 20));
        contr.getActiveKeys().offer(KeyCode.D);

        try (var mockVm = mockStatic(ViewManager.class)) {
            ViewManager vminst = mock(ViewManager.class);
            when(vminst.isUIVisible()).thenReturn(false);
            mockVm.when(ViewManager::getInstance).thenReturn(vminst);
            contr.update();
            verify(contr).movePlayer(KeyCode.D);
            ArgumentCaptor<Position> posCaptor = ArgumentCaptor.forClass(Position.class);
            verify(contr).canGoThere(posCaptor.capture());

            assertTrue(contr.canGoThere(posCaptor.getValue()));
        }
    }

    private GameState trueGameState() {
        GameState gameState = new GameState.GameStateBuilder()
                .setNPlayers(2)
                .setSelectedCharacters(List.of(Job.ARCHER, Job.MAGE))
                .enableAutoSave(true)
                .setInventory()
                .setFlagMap(new FlagMap())
                .setWorldPosition(new WorldPosition(0, 0))
                .setMaps(List.of(new Map1(), new Map2(), new Map3()))
                .build();
        gameState.createParty();
        gameState.createEnemy();
        gameState.createNpc();
        return gameState;
    }

    private MapView niceMapView() {
        Image tileSet = null;
        try (InputStream is = MapBuilder.class.getResourceAsStream("/images/punyworld-overworld-tileset.png")) {
            tileSet = new Image(is);
        } catch (Exception e) {
            System.err.println("Error while loading the TileSetImage: " + e.getMessage());
            e.printStackTrace();
        }

        boolean[][] walkable = new boolean[50][50];
        for (int i = 0; i < walkable.length; i++) {
            for (int j = 0; j < walkable[i].length; j++) {
                walkable[i][j] = true;
            }
        }
        MapView mapView = new MapView(null, 0, 55, 50, 50, walkable, 55, tileSet);
        return mapView;
    }
}
