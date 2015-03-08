package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
import com.homer.exception.DisallowedTransactionException;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.exception.PlayerNotFoundException;
import com.homer.fantasy.*;
import com.homer.fantasy.Player;
import com.homer.fantasy.dao.impl.MockPlayerDAO;
import com.homer.fantasy.facade.PlayerFacade;
import com.homer.mlb.MLBJSONObject;
import junit.framework.Assert;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Random;

/**
 * Created by arigolub on 2/15/15.
 */
public class PlayerFacadeTest {

    private static PlayerFacade facade;

    private static String minorLeaguerPlayerName = "Minor Leaguer";
    private static String minorLeaguerESPNPlayerName = "Minor Leaguer ESPN";
    private static long minorLeaguerPlayerId;
    private static String majorLeaguerToDemotePlayerName = "Major Leaguer";
    private static long majorLeaguerPlayerId;
    private static String suspendedPlayerName = "Suspended";
    private static long suspendedPlayerESPNId = 100L;
    private static long suspendedPlayerId;
    private static Player minorLeaguer;
    private static Player majorLeaguerToDemote;
    private static Player suspendedPlayer;

    @BeforeClass
    public static void beforeClass() {
        MockPlayerDAO.clearMap();

        facade = new PlayerFacade();

        Random rand = new Random();
        minorLeaguer = new Player();
        minorLeaguer.setPlayerName(minorLeaguerPlayerName);
        minorLeaguerPlayerId = rand.nextInt((1000 - 0) + 1) + 0;
        minorLeaguer.setPlayerId(minorLeaguerPlayerId);
        DailyPlayerInfo dpi = new DailyPlayerInfo();
        dpi.setFantasyTeam(new Team(1));
        dpi.setFantasyStatus(PlayerStatus.MINORS);
        dpi.setFantasyPosition(Position.FANTASYBENCH);
        dpi.setPlayer(minorLeaguer);
        PlayerHistory history = new PlayerHistory();
        history.setRookieStatus(true);
        minorLeaguer.getPlayerHistoryList().add(history);
        minorLeaguer.getDailyPlayerInfoList().add(dpi);
        minorLeaguer.setEspnPlayerName(minorLeaguerESPNPlayerName);
        MockPlayerDAO.addPlayerToMapUsingId(minorLeaguer);
        MockPlayerDAO.addPlayerToMapUsingESPNPlayerName(minorLeaguer);

        rand = new Random();
        majorLeaguerToDemote = new Player();
        majorLeaguerToDemote.setPlayerName(majorLeaguerToDemotePlayerName);
        majorLeaguerPlayerId = rand.nextInt((1000 - 0) + 1) + 0;
        majorLeaguerToDemote.setPlayerId(majorLeaguerPlayerId);
        dpi = new DailyPlayerInfo();
        dpi.setFantasyTeam(new Team(1));
        dpi.setFantasyStatus(PlayerStatus.ACTIVE);
        dpi.setFantasyPosition(Position.FANTASYBENCH);
        dpi.setPlayer(majorLeaguerToDemote);
        history = new PlayerHistory();
        history.setRookieStatus(false);
        majorLeaguerToDemote.getPlayerHistoryList().add(history);
        majorLeaguerToDemote.getDailyPlayerInfoList().add(dpi);
        MockPlayerDAO.addPlayerToMapUsingId(majorLeaguerToDemote);
        MockPlayerDAO.addPlayerToMap(majorLeaguerToDemote);

        rand = new Random();
        suspendedPlayer = new Player();
        suspendedPlayer.setPlayerName(suspendedPlayerName);
        suspendedPlayerId = rand.nextInt((1000 - 0) + 1) + 0;
        suspendedPlayer.setPlayerId(suspendedPlayerId);
        dpi = new DailyPlayerInfo();
        dpi.setFantasyTeam(new Team(1));
        dpi.setFantasyStatus(PlayerStatus.ACTIVE);
        dpi.setFantasyPosition(Position.FANTASYBENCH);
        dpi.setPlayer(suspendedPlayer);
        history = new PlayerHistory();
        history.setRookieStatus(false);
        suspendedPlayer.getPlayerHistoryList().add(history);
        suspendedPlayer.getDailyPlayerInfoList().add(dpi);
        suspendedPlayer.setEspnPlayerId(suspendedPlayerESPNId);
        MockPlayerDAO.addPlayerToMapUsingId(suspendedPlayer);
        MockPlayerDAO.addPlayerToMapUsingESPNPlayerId(suspendedPlayer);
    }

    @Test
    public void createOrUpdatePlayer() {
        Player player = new Player();
        player.setPlayerName("Mike Trout");

        player = facade.createOrUpdatePlayer(player);
        Assert.assertNotNull(player);
    }

    @Test
    public void createOrUpdatePlayer_MLB() throws Exception {
        JSONObject json = new JSONObject();
        String nameFull = "Ari Golub";
        String nameFirst = "Ari";
        String nameLast = "Golub";
        Position position = Position.CATCHER;
        json.put("name_display_first_last", nameFull);
        json.put("name_first", nameFirst);
        json.put("name_last", nameLast);
        json.put("primary_position", position.getPositionId());

        com.homer.mlb.Player mlbPlayer = new com.homer.mlb.Player(new MLBJSONObject(json));
        Player player = facade.createOrUpdatePlayer(mlbPlayer);
        Assert.assertNotNull(player);
    }

    @Test
    public void promotePlayer() throws NoDailyPlayerInfoException, PlayerNotFoundException {
        Player player = new Player();
        player.setPlayerId(minorLeaguerPlayerId);

        Player dbPlayer  = facade.promoteToMajorLeagues(player);
        Assert.assertNotNull(dbPlayer);
        Assert.assertEquals(minorLeaguerPlayerName, dbPlayer.getPlayerName());
        Assert.assertEquals(PlayerStatus.ACTIVE, dbPlayer.getDailyPlayerInfoList().get(0).getFantasyStatus());
        Assert.assertEquals(1, (long)dbPlayer.getCurrentFantasyTeam().getTeamId());
    }

    @Test
    public void demotePlayer() throws NoDailyPlayerInfoException, PlayerNotFoundException {
        Player player = new Player();
        player.setPlayerId(majorLeaguerPlayerId);

        Player dbPlayer = facade.demoteToMinorLeagues(player);
        Assert.assertNotNull(dbPlayer);
        Assert.assertEquals(majorLeaguerToDemotePlayerName, dbPlayer.getPlayerName());
        Assert.assertEquals(PlayerStatus.MINORS, dbPlayer.getDailyPlayerInfoList().get(0).getFantasyStatus());
        Assert.assertEquals(1, (long)dbPlayer.getCurrentFantasyTeam().getTeamId());
    }

    @Test
    public void suspendPlayer() throws NoDailyPlayerInfoException, PlayerNotFoundException {
        Player player = new Player();
        player.setPlayerId(suspendedPlayerId);

        Player dbPlayer = facade.addToSuspendedList(player);
        Assert.assertNotNull(dbPlayer);
        Assert.assertEquals(suspendedPlayerName, dbPlayer.getPlayerName());
        Assert.assertEquals(PlayerStatus.SUSPENDED, dbPlayer.getDailyPlayerInfoList().get(0).getFantasyStatus());
        Assert.assertEquals(1, (long)dbPlayer.getCurrentFantasyTeam().getTeamId());
    }

    @Test
    public void updateESPNAttributesByESPNPlayerId() {
        com.homer.espn.Player espnPlayer = new com.homer.espn.Player();
        espnPlayer.setPlayerId(suspendedPlayerESPNId);
        espnPlayer.setPosition(Position.FANTASYCORNERINFIELD);
        espnPlayer.setTeamId(1);

        Player dbPlayer = null;
        try {
            dbPlayer = facade.updateESPNAttributes(espnPlayer);
            Assert.assertEquals(suspendedPlayerId, (long)dbPlayer.getPlayerId());
            Assert.assertEquals(Position.FANTASYCORNERINFIELD, dbPlayer.getDailyPlayerInfoList().get(0).getFantasyPosition());
        } catch (PlayerNotFoundException e) {
            Assert.fail();
        } catch (NoDailyPlayerInfoException e) {
            Assert.fail();
        } catch (DisallowedTransactionException e) {
            Assert.fail();
        }
    }

    @Test
    public void updateESPNAttributesByESPNPlayerName() {
        com.homer.espn.Player espnPlayer = new com.homer.espn.Player();
        espnPlayer.setPlayerName(minorLeaguerESPNPlayerName);
        espnPlayer.setPosition(Position.FANTASYCORNERINFIELD);
        espnPlayer.setTeamId(1);

        Player dbPlayer = null;
        try {
            dbPlayer = facade.updateESPNAttributes(espnPlayer);
            Assert.assertEquals(minorLeaguerPlayerId, (long)dbPlayer.getPlayerId());
            Assert.assertEquals(Position.FANTASYCORNERINFIELD, dbPlayer.getDailyPlayerInfoList().get(0).getFantasyPosition());
        } catch (PlayerNotFoundException e) {
            Assert.fail();
        } catch (NoDailyPlayerInfoException e) {
            Assert.fail();
        } catch (DisallowedTransactionException e) {
            Assert.fail();
        }
    }

    @Test
    public void updateESPNAttributesByPlayerName() {
        com.homer.espn.Player espnPlayer = new com.homer.espn.Player();
        espnPlayer.setPlayerName(majorLeaguerToDemotePlayerName);
        espnPlayer.setPosition(Position.FANTASYCORNERINFIELD);
        espnPlayer.setTeamId(1);

        Player dbPlayer = null;
        try {
            dbPlayer = facade.updateESPNAttributes(espnPlayer);
            Assert.assertEquals(majorLeaguerPlayerId, (long)dbPlayer.getPlayerId());
            Assert.assertEquals(Position.FANTASYCORNERINFIELD, dbPlayer.getDailyPlayerInfoList().get(0).getFantasyPosition());
        } catch (PlayerNotFoundException e) {
            Assert.fail();
        } catch (NoDailyPlayerInfoException e) {
            Assert.fail();
        } catch (DisallowedTransactionException e) {
            Assert.fail();
        }
    }

    @Test
    public void updateESPNAttributesPlayerOnWrongTeam() {
        com.homer.espn.Player espnPlayer = new com.homer.espn.Player();
        espnPlayer.setPlayerName(majorLeaguerToDemotePlayerName);
        espnPlayer.setPosition(Position.FANTASYCORNERINFIELD);
        espnPlayer.setTeamId(2);

        Player dbPlayer = null;
        try {
            dbPlayer = facade.updateESPNAttributes(espnPlayer);
            Assert.fail();
        } catch (PlayerNotFoundException e) {
            Assert.fail();
        } catch (NoDailyPlayerInfoException e) {
            Assert.fail();
        } catch (DisallowedTransactionException e) {
            Assert.assertTrue(true);
        }
    }
}
