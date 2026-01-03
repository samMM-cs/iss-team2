package com.game.controller;

import java.util.ArrayList;
import java.util.List;

import com.game.model.GameState;
import com.game.model.battle.Action;
import com.game.model.battle.ActionStrategy;
import com.game.model.battle.Move;
import com.game.model.battle.MoveReader;
import com.game.model.battle.Battle;
import com.game.model.battle.BattleResult;
import com.game.model.character.CharacterPG;
import com.game.model.character.Party;
import com.game.view.battleview.BattleView;

public class BattleController {
    private Battle battle;
    private BattleResult result;
    private BattleView view;
    private Party party;
    private int currentPlayerActingIndex = 0;
    private List<Action> plannedActionList = new ArrayList<>();
    private List<Move> availableMove;
    private List<Move> loadMove;

    public BattleController(Battle battle, BattleView view) {
        this.battle = battle;
        this.view = view;
        this.availableMove = new ArrayList<>();
        this.loadMove = MoveReader.readMove("/battle/moves.json");// Carico le mosse dal file JSON
        if (loadMove != null)
            this.availableMove.addAll(loadMove);
        else
            System.out.println("Impossibile caricare il file");
        this.party = GameState.getInstance().getParty();
    }

    public void handleAction(String selected) {
        if (selected == null)
            return;

        switch (selected) {
            case "Flee" ->
                backToMap();
            case "Move" -> {
                view.updateMoveList(availableMove.stream().map(Move::getName).toList());
                view.showMoveList();
            }
        }
    }

    public void handleMoveSelection(String moveName) {
        if (moveName == null || moveName.equals("Back")) {
            view.hideMoveList();
            return;
        }
        Move moveData = availableMove.stream().filter(m -> m.getName().equalsIgnoreCase(moveName)).findFirst()
                .orElse(null);
        if (moveData != null) {
            CharacterPG currentPlayerActing = party.getMembers().get(currentPlayerActingIndex);

            ActionStrategy actionStrategy = moveData.getType().createMove(moveData);
            plannedActionList.add(new Action(actionStrategy, currentPlayerActing, List.of(battle.getEnemy())));
            nextPlayerAction();
            view.hideMoveList();
        }
        
    }

    private void updatePlayerUI() {
        // 1. Diciamo alla View chi evidenziare graficamente
        view.setActivePlayer(currentPlayerActingIndex);

        // 2. Estraiamo i nomi delle mosse dal JSON (availableMoves)
        // Se ogni personaggio ha mosse diverse, qui puoi filtrare per Job o Id.
        // Per ora prendiamo tutti i nomi delle mosse caricate:
        if (availableMove == null)
            availableMove = new ArrayList<>();
        List<String> moveNames = availableMove.stream()
                .map(Move::getName)
                .toList();

        // 3. Inviamo i nomi alla View
        view.updateMoveList(moveNames);
    }

    private void nextPlayerAction() {
        if (allPlayerActed()) {
            // Esecuzione logica del turno
            battle.setPlannedActionList(new ArrayList<>(plannedActionList));
            view.disableInput();

            this.result = battle.nextTurn();
            // Esegue i calcoli e restituisce il risultato
            handleBattleResult(this.result);
        } 
        currentPlayerActingIndex = getNextPlayerIndex();
        updatePlayerUI();
        view.enableInput();
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
        return getNextPlayerIndex() <= currentPlayerActingIndex;
    }

    private void backToMap() {
        ViewManager.getInstance().showExplorationView();
    }

    public int getCurrentPlayerActionIndex() {
        return this.currentPlayerActingIndex;
    }

    public List<Move> getAvailableMove() {
        return availableMove;
    }
}
