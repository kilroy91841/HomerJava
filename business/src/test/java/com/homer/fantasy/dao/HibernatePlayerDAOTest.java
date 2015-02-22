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

/**
 * Created by arigolub on 2/15/15.
 */
public class HibernatePlayerDAOTest {

    private static HibernatePlayerDAO playerDAO = new HibernatePlayerDAO();
    private static String name;
    public static final long mlbPlayerId = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

    @BeforeClass
    public static void beforeClass() {
        name = LocalDateTime.now().toString();
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
}
