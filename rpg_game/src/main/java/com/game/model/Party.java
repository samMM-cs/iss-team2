package com.game.model;

import java.util.List;
public class Party {
    private Player mainPlayer;
    private List<Player> members;

    public Party(Player mainPlayer, List<Player> members) {
        this.mainPlayer = mainPlayer;
        this.members = members;
    }

    public static void updateFollowPosition() {

    }

    public final Player getMainPlayer() {
        return this.mainPlayer;
    }

    public final List<Player> getMembers() {
        return this.members;
    }
}
