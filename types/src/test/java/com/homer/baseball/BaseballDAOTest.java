package com.homer.baseball;

import com.homer.SportType;
import com.homer.dao.BaseballDAO;
import com.homer.dao.MySQLDAO;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
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
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2015);
        List<Player> players = dao.getPlayersByTeam(1, SportType.FANTASY, cal.getTime());
        Assert.assertEquals(2, players.size());

        players = dao.getPlayersByTeam(100, SportType.FANTASY, cal.getTime());
        Assert.assertEquals(0, players.size());
    }

    @Test
    public void testGetPlayersOnMLBTeam() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2015);
        List<Player> players = dao.getPlayersByTeam(134, SportType.MLB, cal.getTime());
        Assert.assertEquals(players.size(), 1);

        players = dao.getPlayersByTeam(1, SportType.MLB, cal.getTime());
        Assert.assertEquals(players.size(), 0);
    }

    @Test
    public void testGetDailies() {
        List<DailyPlayer> dailies = dao.getPlayerDailies(1);
        Assert.assertEquals(2, dailies.size());
        for(DailyPlayer dp : dailies) {
            Assert.assertNotNull(dp.getPlayerId());
        }
    }

    @Test
    public void testGetDailyTeam() throws SQLException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2015);
        DailyTeam dailyTeam = dao.getTeamDaily(1, SportType.FANTASY, cal.getTime());
        Assert.assertEquals(2, dailyTeam.getPlayers().size());
        Assert.assertNotNull(dailyTeam.getTeam().getTeamId());
    }
}
