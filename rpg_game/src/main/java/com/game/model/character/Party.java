package com.game.model.character;

import java.util.List;
import com.game.model.Position;

public class Party {
    private final List<Player> members;

    public Party(List<Player> members) {
        this.members = members;
    }

    public void updateFollowPosition(Position newLeaderPos) {
        Player mainPlayer = getMainPlayer();
        // Salviamo i valori, non l'oggetto, per evitare problemi di puntatori
        int prevX = mainPlayer.getPos().getX();
        int prevY = mainPlayer.getPos().getY();

        // Muoviamo il leader: lo sprite scatterà automaticamente nella nuova cella
        mainPlayer.getPos().setX(newLeaderPos.getX());
        mainPlayer.getPos().setY(newLeaderPos.getY());

        for (int i = 1; i < members.size(); i++) {
            Player current = members.get(i);

            int tempX = current.getPos().getX();
            int tempY = current.getPos().getY();

            // Muoviamo il seguace: lo sprite si sposta da solo
            current.getPos().setX(prevX);
            current.getPos().setY(prevY);

            prevX = tempX;
            prevY = tempY;
        }
    }

    public final List<Player> getMembers() {
        return this.members;
    }

    public final Player getMainPlayer() {
        return members.get(0);
    }

}