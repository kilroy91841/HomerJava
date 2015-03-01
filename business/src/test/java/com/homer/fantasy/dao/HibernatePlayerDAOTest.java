package com.homer.fantasy.dao;

import com.homer.PlayerStatus;
import com.homer.fantasy.*;
import com.homer.fantasy.dao.impl.HibernatePlayerDAO;
import com.homer.mlb.MLBJSONObject;
import com.homer.mlb.Stats;
import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by arigolub on 2/15/15.
 */
public class HibernatePlayerDAOTest {

    private static HibernatePlayerDAO playerDAO = new HibernatePlayerDAO();
    private static String name;
    private static int days;
    public static final long mlbPlayerId = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
    private static final Random rand = new Random();

    @BeforeClass
    public static void beforeClass() {
        name = LocalDateTime.now().toString();
        days = rand.nextInt((10000 - 0) + 1) + 0;
    }

    @Test
    public void createOrSavePlayer() {
        Player p = new Player();
        p.setPlayerName(name);
        p.setNameLastFirst("Bulog, Ira");
        p.setFirstName("Ira");
        p.setLastName("Bulog");
        p.setPrimaryPosition(Position.CENTERFIELD);
        p.setMlbPlayerId(mlbPlayerId);
        DailyPlayerInfo info = new DailyPlayerInfo();
        info.setMlbStatus(PlayerStatus.ACTIVE);
        info.setDate(LocalDate.now());
        info.getDailyPlayerInfoKey().setPlayer(p);
        p.getDailyPlayerInfoList().add(info);
        PlayerHistory history = new PlayerHistory();
        history.setPlayer(p);
        history.setSeason(2015);
        Team keeperTeam = new Team();
        keeperTeam.setTeamId(2);
        history.setKeeperTeam(keeperTeam);
        p.addPlayerHistory(history);
        history = new PlayerHistory();
        history.setPlayer(p);
        history.setSeason(2016);
        p.getPlayerHistoryList().add(history);

        Player dbPlayer = playerDAO.createOrSave(p);
        Assert.assertNotNull(dbPlayer);
        Assert.assertEquals(p.getPlayerName(), dbPlayer.getPlayerName());
        Assert.assertEquals(p.getPlayerName(), dbPlayer.getPlayerName());
        Assert.assertEquals(p.getNameLastFirst(), dbPlayer.getNameLastFirst());
        Assert.assertEquals(p.getFirstName(), dbPlayer.getFirstName());
        Assert.assertEquals(p.getLastName(), dbPlayer.getLastName());
    }

    @Test
    public void getPlayer() {
        Player example = new Player();
        example.setPlayerId(1L);
        Player dbPlayer = playerDAO.getPlayer(example);
        Assert.assertNotNull(dbPlayer);

        example = new Player();
        example.setPlayerName(name);
        dbPlayer = playerDAO.getPlayer(example);
        Assert.assertNotNull(dbPlayer);
    }

    @Test
    public void getPlayerListBySeason() {
        List<Player> players = playerDAO.getPlayersByYear(2015);
        Assert.assertNotNull(players);
        Assert.assertTrue(players.size() > 0);

        players = playerDAO.getPlayersByYear(2018);
        Assert.assertNotNull(players);
        Assert.assertTrue(players.size() == 0);
    }

    @Test
    public void getPlayersOnTeamForDate() {
        createFantasyTeam(1);

        List<Player> players = playerDAO.getPlayersOnTeamForDate(new Team(1), LocalDate.now().plusDays(days));

        Assert.assertEquals(23, players.size());
    }

    private void createFantasyTeam(int teamId) {
        Team fantasyTeam = new Team(teamId);
        Position[] positions = { Position.FANTASYCATCHER, Position.FANTASYCATCHER, Position.FANTASYFIRSTBASE,
                Position.FANTASYSECONDBASE, Position.FANTASYTHIRDBASE, Position.FANTASYSHORTSTOP, Position.FANTASYCORNERINFIELD,
                Position.FANTASYMIDDLEINFIELD, Position.FANTASYOUTFIELD, Position.FANTASYOUTFIELD, Position.FANTASYOUTFIELD,
                Position.FANTASYOUTFIELD, Position.FANTASYOUTFIELD, Position.FANTASYUTILITY, Position.FANTASYPITCHER,
                Position.FANTASYPITCHER, Position.FANTASYPITCHER, Position.FANTASYPITCHER, Position.FANTASYPITCHER, Position.FANTASYPITCHER,
                Position.FANTASYPITCHER, Position.FANTASYPITCHER, Position.FANTASYPITCHER };
        for(Position p : positions) {
            Player player = new Player();
            player.setPlayerName(p.getPositionName());
            player.addDailyPlayerInfo(getDailyPlayerInfo(player, p, fantasyTeam));
            playerDAO.createOrSave(player);
        }
    }

    private DailyPlayerInfo getDailyPlayerInfo(Player player, Position position, Team team) {
        DailyPlayerInfo dpi = new DailyPlayerInfo();
        dpi.setPlayer(player);
        dpi.setFantasyPosition(position);
        dpi.setFantasyStatus(PlayerStatus.ACTIVE);
        dpi.setDate(LocalDate.now().plusDays(days));
        dpi.setFantasyTeam(team);
        return dpi;
    }
}
