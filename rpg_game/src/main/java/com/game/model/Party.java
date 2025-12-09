package com.game.model;

import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Party {
    private static final int FOLLOW = 6;
    private final Queue<Position> history = new LinkedList<>();
    private final List<Player> members;

    public Party(List<Player> members) {
        this.members = members;
    }

    public void updateFollowPosition() {
        Player mainPlayer = members.get(0);
        
        history.offer(mainPlayer.getPosition());

        //Dimensione max dello storico
        int maxSize = FOLLOW * (members.size() - 1);
        while (history.size() > maxSize) {
            history.poll();
        }

        Position[] historyArray = history.toArray(new Position[0]);
        for(int i=0;i<members.size()-1;i++) {
            int index = historyArray.length - FOLLOW * (members.size() - 1 - i);
            if(index>=0) {
                members.get(i + 1).setPosition(historyArray[index]);
            }
        }
    }

    public final List<Player> getMembers() {
        return this.members;
    }

    public final Player getMainPlayer() {
        return members.get(0);
    }

}
