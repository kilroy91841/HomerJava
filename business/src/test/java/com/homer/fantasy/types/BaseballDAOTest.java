package com.homer.fantasy.types;

import com.homer.SportType;
import com.homer.fantasy.dao.BaseballDAO;
import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.DailyTeam;
import com.homer.fantasy.Player;
import com.homer.fantasy.Team;
import com.homer.fantasy.types.util.DBPreparer;
import com.homer.util.PropertyRetriever;

import com.ninja_squad.dbsetup.Operations;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

/**
 * Created by arigolub on 1/26/15.
 */
public class BaseballDAOTest {

    private static BaseballDAO dao;

    @BeforeClass
    public static void prepare() throws Exception {

        Operation operation = Operations.sequenceOf(Operations.deleteAllFrom("PLAYER"));

        System.out.println("deleting all players from player in db");

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

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
        List<DailyPlayerInfo> dailies = dao.getPlayerDailies(1);
        Assert.assertEquals(2, dailies.size());
        for(DailyPlayerInfo dp : dailies) {
            Assert.assertNotNull(dp.getDate());
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
