package com.homer.baseball;

import com.homer.SportType;
import com.homer.dao.BaseballDAO;
import com.homer.dao.MySQLDAO;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

/**
 * Created by arigolub on 1/26/15.
 */
public class BaseballDAOTest {

    private static BaseballDAO dao;

    static {
        dao = new BaseballDAO();
    }

    @Test
    public void testPlayerSearch() {
        Player player = dao.getPlayer("Mike Trout");
        Assert.assertEquals("Mike Trout", player.getPlayerName());
    }

    @Test
    public void testGetTeams() {
        List<Team> teams = dao.getTeams();
        Assert.assertTrue(teams.size() > 0);
    }

    @Test
    public void testGetPlayersOnFantasyTeam() {
        List<Player> players = dao.getPlayersByTeam(1, SportType.FANTASY, null);
        Assert.assertEquals(players.size(), 2);

        players = dao.getPlayersByTeam(100, SportType.FANTASY, null);
        Assert.assertEquals(players.size(), 0);
    }

    @Test
    public void testGetPlayersOnMLBTeam() {
        List<Player> players = dao.getPlayersByTeam(134, SportType.MLB, null);
        Assert.assertEquals(players.size(), 1);

        players = dao.getPlayersByTeam(1, SportType.MLB, null);
        Assert.assertEquals(players.size(), 0);
    }
}
