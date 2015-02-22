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

/**
 * Created by arigolub on 2/21/15.
 */
public class TransactionFacadeTest {

    private TransactionFacade facade = new TransactionFacade();

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

        Player dbPlayer = getPlayer("Free Agent");
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

    private Player getPlayer(String name) {
        IPlayerDAO dao = IPlayerDAO.FACTORY.getInstance();
        Player example = new Player();
        example.setPlayerName(name);
        Player dbPlayer = dao.getPlayer(example);
        return dbPlayer;
    }
}
