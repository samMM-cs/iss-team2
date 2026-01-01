package com.game.controller;

import java.util.ArrayList;
import java.util.List;

import com.game.model.GameState;
import com.game.model.battle.Action;
import com.game.model.battle.Battle;
import com.game.model.battle.BattleResult;
import com.game.model.character.CharacterPG;
import com.game.model.character.Party;
import com.game.view.battleview.BattleView;

public class BattleController {
    private Battle battle;
    private BattleView view;
    private Party party;
    private int currentPlayerActingIndex= 0;
    private List<Action> plannedActionList= new ArrayList<>();

    public BattleController(Battle battle, BattleView view) {
        this.battle = battle;
        this.view = view;
        this.party = GameState.getInstance().getParty();
    }

    public void handleAction(String selected) {
        if (selected == null)
            return;

        switch (selected) {
            case "Flee" -> {
                backToMap();
                break;
            }
            case "Move" -> {
                view.showMoveList();
                break;
            }
        }
    }

    public void handleMoveSelection(String move) {
        if (move == null)
            return;

        CharacterPG currentPlayerActing= party.getMembers().get(currentPlayerActingIndex);

        switch (move) {
            case "Attack" -> {
                view.setPlayerAttackOffset(80);
                // qui poi: calcolo danno, animazione, turno nemico
                break;
            }

            case "Back" -> {
                view.hideMoveList();
                break;
            }

            default -> {
                plannedActionList.add(new Action(move, currentPlayerActing, List.of(battle.getEnemy())));
                nextPlayerAction();
                break;
            }
        }
    }

    private void nextPlayerAction() {
        if (allPlayerActed()) {
            //TODO define enemy AI
            battle.setPlannedActionList(plannedActionList);
            view.disableInput();
            handleBattleResult(battle.nextTurn());
            view.enableInput();
            plannedActionList.clear();
        }
        else {
            //TODO notifyView to highlight nextPlayer
        }
        currentPlayerActingIndex = getNextPlayerIndex();
    }

    private void handleBattleResult (BattleResult br) {
        switch (br) {
            case BattleResult.ONGOING : break;
            case BattleResult.PARTY_DEFEATED : {
                backToMap();
                break;
            }
            case BattleResult.PARTY_WON : {
                battle.assignRewards();
                backToMap();
                break;
            }
        }

    }

    private int getNextPlayerIndex() {
        return (currentPlayerActingIndex + 1) % party.getMembers().size();
    }

    private boolean allPlayerActed() {
        return getNextPlayerIndex() <= currentPlayerActingIndex;
    }

    private void backToMap() {
        ViewManager.getInstance().showExplorationView();
    }
}
