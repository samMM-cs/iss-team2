package com.game.model.character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.game.model.Position;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class PartyTest {
    /*
     * private Party party;
     * private List<Player> members;
     * private Player mainPlayer;
     */

    Party setUpEmptyParty() {
        Party party = new Party(new ArrayList<Player>());
        return party;
    }

    Party setUpNPlayersParty(int n) {
        List<Player> members = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Player p = mock(Player.class);
            members.add(p);
        }
        /*
         * Player p1= mock(Player.class);
         * Player p2= mock(Player.class);
         * Player p3= mock(Player.class);
         * Player p4= mock(Player.class);
         */
        // Party party= new Party(Arrays.asList(p1,p2,p3,p4));
        Party party = new Party(members);
        return party;
    }

    Party setUpNPlayersParty_Positions(int n, Position pos) {
        List<Player> members = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Player p = new Player(Job.ARCHER, pos);
            members.add(p);
        }
        Party party = new Party(members);
        return party;
    }

    @Test
    void testNewParty_EmptyParty() {
        Party party = setUpEmptyParty();

        assertNotNull(party);
        assertTrue(party.getMembers().isEmpty());
    }

    @Test
    void testNewParty_4PlayersParty() {
        Party party = setUpNPlayersParty(4);

        assertEquals(4, party.getMembers().size());
    }

    @Test
    void testGetMainPlayer_4PlayersParty() {
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        Player p3 = mock(Player.class);
        Player p4 = mock(Player.class);
        Party party = new Party(Arrays.asList(p1, p2, p3, p4));
        Player mainPlayer = p1;

        Player result = party.getMainPlayer();

        assertNotNull(result);
        assertSame(mainPlayer, result);
    }

    @Test
    void testGetMainPlayer_EmptyParty() {
        Party party = setUpEmptyParty();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> party.getMainPlayer());
    }

    @Test
    void testGetMembers() {
        Player p1 = mock(Player.class);
        Player p2 = mock(Player.class);
        List<Player> members = Arrays.asList(p1, p2);
        Party party = new Party(members);

        List<Player> result = party.getMembers();

        assertNotNull(result);
        assertSame(members, result);
    }

    @Test
    void testUpdateFollowPosition_1PlayerParty() {
        Position p = new Position(200, 50);
        Party party = setUpNPlayersParty_Positions(1, p);

        party.updateFollowPosition(p);

        assertEquals(200, (int) party.getMainPlayer().getPosition().x());
        assertEquals(50, (int) party.getMainPlayer().getPosition().y());
    }

    @Test
    void testUpdateFollowPosition_4PlayerParty() {
        Position pos = new Position(200, 50);
        Party party = setUpNPlayersParty_Positions(4, pos);

        party.updateFollowPosition(pos);

        for (Player p : party.getMembers()) {
            assertEquals(200, (int) p.getPosition().x());
            assertEquals(50, (int) p.getPosition().y());
        }
    }
}
