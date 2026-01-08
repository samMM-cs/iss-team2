package com.game.model.character;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.game.model.Position;

public class Party {
    private final List<Player> members = new ArrayList<>();

    public Party() {
    }

    public Party(List<Player> members) {
        this.members.addAll(members);
    }

    public void updateFollowPosition(Position newLeaderPos) {
        getMainPlayer().notifyFollower();
        getMainPlayer().setPosition(newLeaderPos);
    }

    public final List<Player> getMembers() {
        return this.members;
    }

    public final void setMembers(List<Player> members) {
        this.members.addAll(members);
        for (int i = 1; i < this.members.size(); i++) {
            this.members.get(i).subscribeToFollowed(this.members.get(i - 1));
        }
    }

    @JsonIgnore
    public final Player getMainPlayer() {
        return members.get(0);
    }

    @Override
    public String toString() {
        return members.toString();
    }
}