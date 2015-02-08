package com.homer.fantasy.dao;

import com.homer.PlayerStatus;
import com.homer.SportType;
import com.homer.fantasy.*;
import com.homer.fantasy.util.DBPreparer;
import com.homer.mlb.MLBJSONObject;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 2/8/15.
 */
public class HomerDAOTest {

    private Calendar cal;
    private HomerDAO dao = new HomerDAO();

    @Before
    public void prepare() throws Exception {

        Operation operation = Operations.sequenceOf(
                Operations.deleteAllFrom("PLAYERHISTORY", "PLAYERTOTEAM", "PLAYER"),
                Operations.sql("alter table PLAYER AUTO_INCREMENT=1"),
                Operations.sql("insert into PLAYER (playerName, primaryPositionId, mlbPlayerId, espnPlayerId) " +
                "values (\"Mike Trout\", 8, 100, 200)")
                );

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DATE, 8);
        cal.set(Calendar.HOUR, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    @Test
    public void findByExample_PlayerName() {
        
        Player player = new Player("Mike Trout");
        Player dbPlayer = dao.findByExample(player);
        Assert.assertEquals(player.getPlayerName(), dbPlayer.getPlayerName());
    }

    @Test
    public void findByExample_PlayerId() {
        
        Player player = new Player();
        player.setPlayerId(1L);
        Player dbPlayer = dao.findByExample(player);
        Assert.assertEquals(player.getPlayerId(), dbPlayer.getPlayerId());
    }

    @Test
    public void findByExample_MLBPlayerId() {
        
        Player player = new Player();
        player.addThirdPartyPlayerInfo(new ThirdPartyPlayerInfo(100L, ThirdPartyPlayerInfo.MLB));
        Player dbPlayer = dao.findByExample(player);
        Assert.assertEquals(player.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB),
                dbPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB));
    }

    @Test
    public void findByExample_ESPNPlayerId() {
        
        Player player = new Player();
        player.addThirdPartyPlayerInfo(new ThirdPartyPlayerInfo(200L, ThirdPartyPlayerInfo.ESPN));
        Player dbPlayer = dao.findByExample(player);
        Assert.assertEquals(player.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.ESPN),
                dbPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.ESPN));
    }

    @Test
    public void createPlayer_MLB() throws Exception {
        JSONObject json = new JSONObject();
        json.put("name_display_first_last", "Mike Trout");
        json.put("primary_position", 8);
        json.put("player_id", 200);
        MLBJSONObject mlbJson = new MLBJSONObject(json);
        com.homer.mlb.Player player = new com.homer.mlb.Player(mlbJson);
        
        boolean success = dao.createPlayer(player);
        Assert.assertTrue(success);
    }

    @Test
    public void createPlayer_Fantasy() throws Exception {
        Player player = new Player(1L, "Mike Trout", Position.CENTERFIELD);
        player.addThirdPartyPlayerInfo(new ThirdPartyPlayerInfo(100L, ThirdPartyPlayerInfo.MLB));
        player.addThirdPartyPlayerInfo(new ThirdPartyPlayerInfo(200L, ThirdPartyPlayerInfo.ESPN));
        
        boolean success = dao.createPlayer(player);
        Assert.assertTrue(success);
    }

    @Test
    public void updatePlayer() throws Exception {
        Player existingPlayer = new Player();
        existingPlayer.setPlayerId(1L);
        Player newPlayer = new Player();
        newPlayer.setPlayerName("Ari Golub");
        newPlayer.setPrimaryPosition(Position.CATCHER);
        newPlayer.addThirdPartyPlayerInfo(new ThirdPartyPlayerInfo(101L, ThirdPartyPlayerInfo.MLB));
        newPlayer.addThirdPartyPlayerInfo(new ThirdPartyPlayerInfo(201L, ThirdPartyPlayerInfo.ESPN));
        
        boolean success = dao.updatePlayer(existingPlayer, newPlayer);
        Assert.assertTrue(success);
        Player dbPlayer = dao.findByExample(new Player("Ari Golub"));
        Assert.assertEquals(newPlayer, dbPlayer);
    }

    @Test
    public void getMLBTeams() throws SQLException {
        
        List<Team> teams = dao.getMLBTeams();
        Assert.assertEquals(30, teams.size());
    }

    @Test
    public void getFantasyTeams() throws SQLException {
        
        List<Team> teams = dao.getFantasyTeams();
        Assert.assertEquals(2, teams.size());
    }

    @Test
    public void getTeamByName() throws SQLException {
        
        Team team = dao.getTeamByName("New York Yankees");
        Assert.assertEquals("New York Yankees", team.getTeamName());
        Assert.assertEquals("NYY", team.getTeamCode());
        Assert.assertEquals(147, (int)team.getTeamId());
        Assert.assertEquals(SportType.MLB, team.getTeamType());
    }

    @Test
    public void createPlayerToTeam() throws SQLException {
        Player player = new Player();
        player.setPlayerId(1L);
        Integer fantasyTeamId = 1;
        Integer mlbTeamId = 147;
        String fantasyPlayerStatusCode = "A";
        String mlbPlayerStatusCode = "A";
        Position fantasyPosition = Position.FANTASYOUTFIELD;

        
        boolean success =
                dao.createPlayerToTeam(player, cal.getTime(), fantasyTeamId, mlbTeamId, fantasyPlayerStatusCode, mlbPlayerStatusCode, fantasyPosition);
        Assert.assertTrue(success);

        Player dbPlayer = dao.findByExample(player);
        DailyPlayerInfo info = dbPlayer.getDailyPlayerInfoList().get(0);
        Assert.assertEquals(cal.getTime(), info.getDate());
        Assert.assertEquals(fantasyTeamId, info.getFantasyTeam().getTeamId());
        Assert.assertEquals(mlbTeamId, info.getMlbTeam().getTeamId());
        Assert.assertEquals(fantasyPlayerStatusCode, info.getFantasyStatus().getCode());
        Assert.assertEquals(mlbPlayerStatusCode, info.getMlbStatus().getCode());
        Assert.assertEquals(fantasyPosition, info.getFantasyPosition());
    }

    @Test
    public void updateDailyFantasyProperties() throws SQLException {
        createPlayerToTeam();

        Player player = new Player();
        player.setPlayerId(1L);
        int fantasyTeamId = 2;
        Position fantasyPosition = Position.FANTASYOUTFIELD;
        PlayerStatus playerStatus = PlayerStatus.DISABLEDLIST;
        
        boolean success = dao.updateDailyFantasyProperties(player, fantasyTeamId, playerStatus, fantasyPosition);
        Assert.assertTrue(success);

        Player dbPlayer = dao.findByExample(player);
        DailyPlayerInfo info = dbPlayer.getDailyPlayerInfoList().get(0);
        Assert.assertEquals(cal.getTime(), info.getDate());
        Assert.assertEquals(fantasyTeamId, (int)info.getFantasyTeam().getTeamId());
        Assert.assertEquals(playerStatus, info.getFantasyStatus());
        Assert.assertEquals(fantasyPosition, info.getFantasyPosition());
    }

    @Test
    public void updateDailyMLBProperties() throws SQLException {
        createPlayerToTeam();

        Player player = new Player();
        player.setPlayerId(1L);
        int mlbTeamId = 109;
        PlayerStatus playerStatus = PlayerStatus.DISABLEDLIST;
        
        boolean success = dao.updateDailyMLBProperties(player, mlbTeamId, playerStatus);
        Assert.assertTrue(success);

        Player dbPlayer = dao.findByExample(player);
        DailyPlayerInfo info = dbPlayer.getDailyPlayerInfoList().get(0);
        Assert.assertEquals(cal.getTime(), info.getDate());
        Assert.assertEquals(mlbTeamId, (int)info.getMlbTeam().getTeamId());
        Assert.assertEquals(playerStatus, info.getMlbStatus());
    }

    @Test
    public void createPlayerHistory() throws SQLException {
        Player player = new Player();
        player.setPlayerId(1L);
        Team team = new Team();
        team.setTeamId(1);
        
        boolean success = dao.createPlayerHistory(player, 2015, 30, 1, true, team, team, true);
        Assert.assertTrue(success);
    }

    @Test
    public void updatePlayerHistory() throws SQLException {
        createPlayerHistory();

        Player player = new Player();
        player.setPlayerId(1L);

        Team team = new Team();
        team.setTeamId(1);

        PlayerHistory history = new PlayerHistory(2015, 20, 2, false, team, team, false);
        
        boolean success = dao.updatePlayerHistory(player, history);
        Assert.assertTrue(success);
    }
    
    @Test
    public void getPlayerHistory() throws Exception {
        createPlayerHistory();

        Player player = new Player();
        player.setPlayerId(1L);
        
        List<PlayerHistory> playerHistoryList = dao.getPlayerHistory(player);
        Assert.assertEquals(1, playerHistoryList.size());
        PlayerHistory history = playerHistoryList.get(0);
        Assert.assertEquals(2015, history.getSeason());
        Assert.assertEquals(30, history.getSalary());
        Assert.assertEquals(1, history.getKeeperSeason());
        Assert.assertEquals(true, (boolean)history.isMinorLeaguer());
        Assert.assertEquals(true, (boolean)history.hasRookieStatus());
        Assert.assertEquals(1, (int)history.getDraftTeam().getTeamId());
        Assert.assertEquals(1, (int)history.getKeeperTeam().getTeamId());
    }
}
