package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
import com.homer.espn.Transaction;
import com.homer.exception.DisallowedTransactionException;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.Player;
import com.homer.fantasy.PlayerHistory;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.IPlayerDAO;
import com.homer.fantasy.dao.impl.MockExternalDAO;
import com.homer.fantasy.dao.impl.MockPlayerDAO;
import com.homer.fantasy.key.DailyPlayerInfoKey;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created by arigolub on 2/21/15.
 */
public class TransactionFacadeTest {

    private TransactionFacade facade = new TransactionFacade();

    private static Player minorLeaguer;
    private static String minorLeaguerPlayerName = "Minor Leaguer";
    private static long minorLeaguerPlayerId;

    private static Player suspended;
    private static String suspendedPlayerName = "Suspended";
    private static long suspendedPlayerId;

    @Before
    public void setup() {
        MockPlayerDAO.clearMap();
        Player p = new Player();
        p.setPlayerName("Not Free Agent");
        p.setPlayerId(1);
        DailyPlayerInfo dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.ACTIVE);
        DailyPlayerInfoKey key = new DailyPlayerInfoKey();
        key.setPlayer(p);
        dpi.setDailyPlayerInfoKey(key);
        dpi.setFantasyTeam(new Team(3));
        p.getDailyPlayerInfoList().add(dpi);
        PlayerHistory history = new PlayerHistory();
        history.setKeeperSeason(3);
        p.getPlayerHistoryList().add(history);
        MockPlayerDAO.addPlayerToMap(p);

        p = new Player();
        p.setPlayerName("Free Agent");
        p.setPlayerId(1);
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.FREEAGENT);
        key = new DailyPlayerInfoKey();
        key.setPlayer(p);
        dpi.setDailyPlayerInfoKey(key);
        dpi.setFantasyTeam(new Team(0));
        p.getDailyPlayerInfoList().add(dpi);
        history = new PlayerHistory();
        history.setKeeperSeason(3);
        p.getPlayerHistoryList().add(history);
        MockPlayerDAO.addPlayerToMap(p);

        Random rand = new Random();
        minorLeaguer = new Player();
        minorLeaguer.setPlayerName(minorLeaguerPlayerName);
        minorLeaguerPlayerId = rand.nextInt((1000 - 0) + 1) + 0;
        minorLeaguer.setPlayerId(minorLeaguerPlayerId);
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.MINORS);
        key = new DailyPlayerInfoKey();
        key.setPlayer(p);
        dpi.setDailyPlayerInfoKey(key);
        dpi.setFantasyTeam(new Team(1));
        minorLeaguer.getDailyPlayerInfoList().add(dpi);
        history = new PlayerHistory();
        history.setKeeperSeason(3);
        minorLeaguer.getPlayerHistoryList().add(history);
        MockPlayerDAO.addPlayerToMap(minorLeaguer);

        rand = new Random();
        suspended = new Player();
        suspended.setPlayerName(suspendedPlayerName);
        suspendedPlayerId = rand.nextInt((1000 - 0) + 1) + 0;
        suspended.setPlayerId(suspendedPlayerId);
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.SUSPENDED);
        key = new DailyPlayerInfoKey();
        key.setPlayer(p);
        dpi.setDailyPlayerInfoKey(key);
        dpi.setFantasyTeam(new Team(2));
        suspended.getDailyPlayerInfoList().add(dpi);
        history = new PlayerHistory();
        history.setKeeperSeason(3);
        suspended.getPlayerHistoryList().add(history);
        MockPlayerDAO.addPlayerToMap(suspended);
    }

    @Test
    public void addPlayer_failure() throws NoDailyPlayerInfoException {
        Transaction t = new Transaction();
        t.setPlayerName("Not Free Agent");
        t.setMove(Transaction.ADD);
        t.setTeamId(3);
        t.setNodeText("dummy text");
        t.setTime(LocalDateTime.now());

        try {
            boolean success = facade.consumeTransaction(t);
            Assert.assertTrue(false);
        } catch (NoDailyPlayerInfoException e) {
            Assert.assertTrue(false);
        } catch (DisallowedTransactionException e) {
            Assert.assertTrue(true);
        }

        Player dbPlayer = getPlayer("Not Free Agent");
        Assert.assertEquals(PlayerStatus.ACTIVE, dbPlayer.getMostRecentFantasyStatus());
    }

    @Test
    public void addPlayer_success() throws NoDailyPlayerInfoException {
        Transaction t = new Transaction();
        t.setPlayerName("Free Agent");
        t.setMove(Transaction.ADD);
        t.setTeamId(3);
        t.setNodeText("dummy text");
        t.setTime(LocalDateTime.now());

        try {
            boolean success = facade.consumeTransaction(t);
            Assert.assertTrue(success);
        } catch (NoDailyPlayerInfoException e) {
            Assert.assertTrue(false);
        } catch (DisallowedTransactionException e) {
            Assert.assertTrue(false);
        }

        Player dbPlayer = getPlayer("Free Agent");
        Assert.assertEquals(PlayerStatus.ACTIVE, dbPlayer.getMostRecentFantasyStatus());
    }

    @Test
    public void dropPlayer_failure() throws NoDailyPlayerInfoException {
        Transaction t = new Transaction();
        t.setPlayerName("Free Agent");
        t.setMove(Transaction.DROP);
        t.setTeamId(3);
        t.setNodeText("dummy text");
        t.setTime(LocalDateTime.now());

        try {
            boolean success = facade.consumeTransaction(t);
            Assert.assertFalse(success);
        } catch (NoDailyPlayerInfoException e) {
            Assert.assertTrue(false);
        } catch (DisallowedTransactionException e) {
            Assert.assertTrue(true);
        }

        Player dbPlayer = getPlayer("Free Agent");
        Assert.assertEquals(PlayerStatus.FREEAGENT, dbPlayer.getMostRecentFantasyStatus());
    }

    @Test
    public void dropPlayer_success() throws NoDailyPlayerInfoException {
        Transaction t = new Transaction();
        t.setPlayerName("Not Free Agent");
        t.setMove(Transaction.DROP);
        t.setTeamId(3);
        t.setNodeText("dummy text");
        t.setTime(LocalDateTime.now());

        try {
            boolean success = facade.consumeTransaction(t);
            Assert.assertTrue(true);
        } catch (NoDailyPlayerInfoException e) {
            Assert.assertTrue(false);
        } catch (DisallowedTransactionException e) {
            Assert.assertTrue(false);
        }

        Player dbPlayer = getPlayer("Not Free Agent");
        Assert.assertEquals(PlayerStatus.FREEAGENT, dbPlayer.getMostRecentFantasyStatus());
    }

    @Test
    public void transferPlayer_failure() {

        Transaction t = new Transaction();
        t.setPlayerName("Not Free Agent");
        t.setMove(Transaction.TRADE);
        t.setTeamId(3);
        t.setNodeText("dummy text");
        t.setTime(LocalDateTime.now());

        try {
            boolean success = facade.consumeTransaction(t);
            Assert.assertTrue(false);
        } catch (NoDailyPlayerInfoException e) {
            Assert.assertTrue(false);
        } catch (DisallowedTransactionException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void transferPlayer_success() {

        Transaction t = new Transaction();
        t.setPlayerName("Not Free Agent");
        t.setMove(Transaction.TRADE);
        t.setTeamId(5);
        t.setNodeText("dummy text");
        t.setTime(LocalDateTime.now());

        try {
            boolean success = facade.consumeTransaction(t);
        } catch (NoDailyPlayerInfoException e) {
            Assert.assertTrue(false);
        } catch (DisallowedTransactionException e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void addPlayerDoNotResetContractYear() throws NoDailyPlayerInfoException {
        Transaction drop = new Transaction();
        drop.setPlayerName("Free Agent");
        drop.setMove(Transaction.DROP);
        drop.setTeamId(1);
        drop.setNodeText("dummy text");
        drop.setTime(LocalDateTime.now().minusHours(10));
        MockExternalDAO.addTransaction(drop);

        Transaction t = new Transaction();
        t.setPlayerName("Free Agent");
        t.setMove(Transaction.ADD);
        t.setTeamId(1);
        t.setNodeText("dummy text");
        t.setTime(LocalDateTime.now());

        try {
            boolean success = facade.consumeTransaction(t);
            Assert.assertTrue(success);
        } catch (DisallowedTransactionException e) {
            Assert.assertTrue(false);
        } catch (NoDailyPlayerInfoException e) {
            Assert.assertTrue(false);
        }

        Player dbPlayer = getPlayer("Free Agent");
        Assert.assertEquals(3, dbPlayer.getPlayerHistoryList().get(0).getKeeperSeason());
        Assert.assertEquals(PlayerStatus.ACTIVE, dbPlayer.getMostRecentFantasyStatus());
    }

    @Test
    public void addPlayerResetContractYear() throws NoDailyPlayerInfoException {
        Transaction drop = new Transaction();
        drop.setPlayerName("Free Agent");
        drop.setMove(Transaction.DROP);
        drop.setTeamId(1);
        drop.setNodeText("dummy text");
        drop.setTime(LocalDateTime.now().minusHours(30));
        MockExternalDAO.addTransaction(drop);

        Transaction t = new Transaction();
        t.setPlayerName("Free Agent");
        t.setMove(Transaction.ADD);
        t.setTeamId(1);
        t.setNodeText("dummy text");
        t.setTime(LocalDateTime.now());

        try {
            boolean success = facade.consumeTransaction(t);
            Assert.assertTrue(success);
        } catch (DisallowedTransactionException e) {
            Assert.assertTrue(false);
        } catch (NoDailyPlayerInfoException e) {
            Assert.assertTrue(false);
        }

        Player dbPlayer = getPlayer("Free Agent");
        Assert.assertEquals(0, dbPlayer.getPlayerHistoryList().get(0).getKeeperSeason());
        Assert.assertEquals(PlayerStatus.ACTIVE, dbPlayer.getMostRecentFantasyStatus());
    }

    @Test
    public void addMinorLeaguerOnCorrectTeam() {
        Transaction addMinorLeaguer = new Transaction();
        addMinorLeaguer.setPlayerName(minorLeaguerPlayerName);
        addMinorLeaguer.setMove(Transaction.ADD);
        addMinorLeaguer.setTeamId(1);
        addMinorLeaguer.setNodeText("dummy text");
        addMinorLeaguer.setTime(LocalDateTime.now());

        try {
            boolean success = facade.consumeTransaction(addMinorLeaguer);
            Assert.assertTrue(success);

            Player dbPlayer = getPlayer(minorLeaguerPlayerName);
            Assert.assertEquals(minorLeaguerPlayerId, (long)dbPlayer.getPlayerId());
            Assert.assertEquals(PlayerStatus.ACTIVE, dbPlayer.getMostRecentFantasyStatus());
        } catch (NoDailyPlayerInfoException e) {
            Assert.fail();
        } catch (DisallowedTransactionException e) {
            Assert.fail();
        }
    }

    @Test
    public void addMinorLeaguerOnSomeoneElsesTeam() throws NoDailyPlayerInfoException {
        Transaction addMinorLeaguer = new Transaction();
        addMinorLeaguer.setPlayerName(minorLeaguerPlayerName);
        addMinorLeaguer.setMove(Transaction.ADD);
        addMinorLeaguer.setTeamId(2);
        addMinorLeaguer.setNodeText("dummy text");
        addMinorLeaguer.setTime(LocalDateTime.now());

        try {
            facade.consumeTransaction(addMinorLeaguer);
            Assert.fail();
        } catch (NoDailyPlayerInfoException e) {
            Assert.fail();
        } catch (DisallowedTransactionException e) {
            Assert.assertTrue(true);
        }

        Player dbPlayer = getPlayer(minorLeaguerPlayerName);
        Assert.assertEquals(minorLeaguerPlayerId, (long)dbPlayer.getPlayerId());
        Assert.assertEquals(PlayerStatus.MINORS, dbPlayer.getMostRecentFantasyStatus());
        Assert.assertEquals(1, (int)dbPlayer.getCurrentFantasyTeam().getTeamId());
    }

    @Test
    public void addSuspendedPlayerOnCorrectTeam() {
        Transaction addMinorLeaguer = new Transaction();
        addMinorLeaguer.setPlayerName(suspendedPlayerName);
        addMinorLeaguer.setMove(Transaction.ADD);
        addMinorLeaguer.setTeamId(2);
        addMinorLeaguer.setNodeText("dummy text");
        addMinorLeaguer.setTime(LocalDateTime.now());

        try {
            boolean success = facade.consumeTransaction(addMinorLeaguer);
            Assert.assertTrue(success);

            Player dbPlayer = getPlayer(suspendedPlayerName);
            Assert.assertEquals(suspendedPlayerId, (long)dbPlayer.getPlayerId());
            Assert.assertEquals(PlayerStatus.ACTIVE, dbPlayer.getMostRecentFantasyStatus());
        } catch (NoDailyPlayerInfoException e) {
            Assert.fail();
        } catch (DisallowedTransactionException e) {
            Assert.fail();
        }
    }

    @Test
    public void addSuspendedPlayerOnSomeoneElsesTeam() throws NoDailyPlayerInfoException {
        Transaction addMinorLeaguer = new Transaction();
        addMinorLeaguer.setPlayerName(suspendedPlayerName);
        addMinorLeaguer.setMove(Transaction.ADD);
        addMinorLeaguer.setTeamId(1);
        addMinorLeaguer.setNodeText("dummy text");
        addMinorLeaguer.setTime(LocalDateTime.now());

        try {
            facade.consumeTransaction(addMinorLeaguer);
            Assert.fail();
        } catch (NoDailyPlayerInfoException e) {
            Assert.fail();
        } catch (DisallowedTransactionException e) {
            Assert.assertTrue(true);
        }

        Player dbPlayer = getPlayer(suspendedPlayerName);
        Assert.assertEquals(suspendedPlayerId, (long)dbPlayer.getPlayerId());
        Assert.assertEquals(PlayerStatus.SUSPENDED, dbPlayer.getMostRecentFantasyStatus());
        Assert.assertEquals(2, (int)dbPlayer.getCurrentFantasyTeam().getTeamId());
    }

    @Test
    public void dropDemotedPlayer() {
        Transaction tran = new Transaction();
        tran.setPlayerName(minorLeaguerPlayerName);
        tran.setMove(Transaction.DROP);
        tran.setTeamId(1);
        tran.setNodeText("dummy text");
        tran.setTime(LocalDateTime.now());

        try {
            boolean success = facade.consumeTransaction(tran);
            Assert.assertTrue(success);

            Player dbPlayer = getPlayer(minorLeaguerPlayerName);
            Assert.assertEquals(minorLeaguerPlayerId, (long)dbPlayer.getPlayerId());
            Assert.assertEquals(PlayerStatus.MINORS, dbPlayer.getMostRecentFantasyStatus());
            Assert.assertEquals(1, (int)dbPlayer.getCurrentFantasyTeam().getTeamId());
        } catch (DisallowedTransactionException e) {
            Assert.fail();
        } catch (NoDailyPlayerInfoException e) {
            Assert.fail();
        }
    }

    @Test
    public void dropSuspendedPlayer() {
        Transaction tran = new Transaction();
        tran.setPlayerName(suspendedPlayerName);
        tran.setMove(Transaction.DROP);
        tran.setTeamId(2);
        tran.setNodeText("dummy text");
        tran.setTime(LocalDateTime.now());

        try {
            boolean success = facade.consumeTransaction(tran);
            Assert.assertTrue(success);

            Player dbPlayer = getPlayer(suspendedPlayerName);
            Assert.assertEquals(suspendedPlayerId, (long)dbPlayer.getPlayerId());
            Assert.assertEquals(PlayerStatus.SUSPENDED, dbPlayer.getMostRecentFantasyStatus());
            Assert.assertEquals(2, (int)dbPlayer.getCurrentFantasyTeam().getTeamId());
        } catch (DisallowedTransactionException e) {
            Assert.fail();
        } catch (NoDailyPlayerInfoException e) {
            Assert.fail();
        }
    }

    private Player getPlayer(String name) {
        IPlayerDAO dao = IPlayerDAO.FACTORY.getInstance();
        Player example = new Player();
        example.setPlayerName(name);
        Player dbPlayer = dao.getPlayer(example);
        return dbPlayer;
    }
}
