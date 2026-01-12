package com.game.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.game.model.GameState;
import com.game.model.battle.Action;
import com.game.model.battle.ActionStrategy;
import com.game.model.battle.Move;
import com.game.model.battle.Battle;
import com.game.model.battle.BattleResult;
import com.game.model.character.CharacterPG;
import com.game.model.character.Party;
import com.game.model.character.Player;
import com.game.view.battleview.BattleView; 

public class BattleController {
    private Battle battle;
    private BattleResult result;
    private BattleView view;
    private Party party;
    private List<CharacterPG> battlingPGs;
    private int currentPlayerActingIndex = 0;
    private List<Action> plannedActionList = new ArrayList<>();
    private CharacterPG currentPlayerActing;
    private ActionStrategy actionStrategy;

    public BattleController(Battle battle, BattleView view) {
        this.battle = battle;
        this.view = view;
        this.party = GameState.getInstance().getParty();
        this.battlingPGs = view.getBattlingPGs();

        updatePlayerUI();
    }

    public void handleAction(String selected) {
        if (selected == null)
            return;

        switch (selected) {
            case "Flee":
                backToMap();
            case "Move": {
                view.showMoveList();
                view.hideActionList();
            }
        }
    }

    public void handleMoveSelection(String moveName) {
        if (moveName == null || moveName.equals("Back")) {
            view.hideMoveList();
            view.showActionList();
            return;
        }
        CharacterPG character = party.getMembers().get(currentPlayerActingIndex);
        Move moveData = character.getCurrentMove().stream().filter(m -> m.getName().equalsIgnoreCase(moveName))
                .findFirst()
                .orElse(null);
        if (moveData != null) {
            currentPlayerActing = party.getMembers().get(currentPlayerActingIndex);
            actionStrategy = moveData.getType().createMove(moveData);

            view.hideMoveList();
            view.showTargetList();
        }

    }

    public void handleTargetSelection(String target) {
        switch (target) {
            case ("Back"): {
                view.hideTargetList();
                view.showMoveList();
                break;
            }

            default: {
                CharacterPG suitableTarget = battlingPGs.stream()
                        .filter(character -> character.getJob().name().equals(target))
                        .findFirst()
                        .orElse(null);

                plannedActionList.add(new Action(actionStrategy, currentPlayerActing, List.of(suitableTarget)));

                nextPlayerAction();
                view.hideTargetList();
                view.hideMoveList();
                view.showActionList();
                break;
            }
        }
    }

    private void updatePlayerUI() {
        // 1. Diciamo alla View chi evidenziare graficamente
        view.setActivePlayer(currentPlayerActingIndex);

        CharacterPG character = party.getMembers().get(currentPlayerActingIndex);
        // 2. Estraiamo i nomi delle mosse dal JSON (availableMoves)
        // Se ogni personaggio ha mosse diverse, qui puoi filtrare per Job o Id.
        // Per ora prendiamo tutti i nomi delle mosse caricate:
        List<String> moveNames = character.getCurrentMove().stream().map(Move::getName).collect(Collectors.toList());

        // 3. Inviamo i nomi alla View
        view.updateMoveList(moveNames);
    }

    private void nextPlayerAction() {
        if (allPlayerActed()) {
            // choose random target
            Player target = party.getMembers()
                    .get((int) (party.getMembers().size()
                            * Math.random() / Math.nextDown(1.0)));
            for (int i = 0; i < battle.getEnemies().size(); i++) {
                plannedActionList.add(new Action(battle.enemyAIString(i), battle.getEnemies().get(i), target));
            }
            // Mosse del nemico
            // Esecuzione logica del turno
            battle.setPlannedActionList(new ArrayList<>(plannedActionList));
            view.disableInput();
            this.result = battle.nextTurn();
            // Esegue i calcoli e restituisce il risultato
            handleBattleResult(this.result);
            view.enableInput();
        }
        currentPlayerActingIndex = getNextPlayerIndex();
        updatePlayerUI();
        view.hideMoveList();
        view.showActionList();
    }

    private void handleBattleResult(BattleResult br) {
        switch (br) {
            case BattleResult.ONGOING:
                // Reset per il prossimo round
                plannedActionList.clear();
                break;
            case BattleResult.PARTY_DEFEATED: {
                backToMap();
                break;
            }
            case BattleResult.PARTY_WON: {
                battle.assignRewards();
                // battle.getEnemy().hide();
                GameState.getInstance().getEnemies().remove(battle.getEnemies().get(0));
                backToMap();
                break;
            }
        }

    }

    // Calcolo l'indice del prossimo player vivo. Salta chi ha getHp()<=0
    private int getNextPlayerIndex() {
        int size = party.getMembers().size();
        for (int i = 1; i <= size; i++) {
            int next = (currentPlayerActingIndex + i) % size;
            if (party.getMembers().get(next).getCurrentStats().getHp() > 0)
                return next;
        }
        return currentPlayerActingIndex;

    }

    private boolean allPlayerActed() {
        return plannedActionList.size() >= party.getMembers().stream().filter(p -> p.getCurrentStats().getHp() > 0)
                .count();
    }

    private void backToMap() {
        ViewManager.getInstance().showExplorationView();
    }

    public int getCurrentPlayerActionIndex() {
        return this.currentPlayerActingIndex;
    }
}
