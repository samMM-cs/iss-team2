package com.game.model.character;

import java.util.List;

import com.game.model.Position;

public class Party {
    private final List<Player> members;

    public Party(List<Player> members) {
        this.members = members;
    }

    public void updateFollowPosition(Position nextPosition) {
        Player mainPlayer = members.get(0);
        mainPlayer.notifyFollower();
        mainPlayer.setPosition(nextPosition);
    }

    public final List<Player> getMembers() {
        return this.members;
    }

    public final Player getMainPlayer() {
        return members.get(0);
    }

}
