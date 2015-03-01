package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
import com.homer.exception.*;
import com.homer.fantasy.*;
import com.homer.fantasy.dao.IFreeAgentAuctionDAO;
import com.homer.fantasy.dao.IPlayerDAO;
import com.homer.fantasy.dao.impl.MockFreeAgentAuctionDAO;
import com.homer.fantasy.dao.impl.MockMoneyDAO;
import com.homer.fantasy.dao.impl.MockPlayerDAO;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDateTime;

/**
 * Created by arigolub on 2/28/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FreeAgentAuctionFacadeTest {

    private static FreeAgentAuctionFacade facade = new FreeAgentAuctionFacade();

    private static Team team1 = new Team(1);
    private static Team team2 = new Team(2);
    private static Player player;
    private static String playerName = LocalDateTime.now().toString();
    private static long playerId = 1;
    private static Player auctionPlayer;
    private static String auctionPlayerName = LocalDateTime.now().plusDays(1).toString();
    private static long auctionPlayerId = 2;
    private static Player cancelledAuctionPlayer;
    private static String cancelledAuctionPlayerName = LocalDateTime.now().plusDays(2).toString();
    private static long cancelledAuctionPlayerId = 3;
    private static FreeAgentAuction existingAuction;
    private static FreeAgentAuction cancelledAuction;
    private static FreeAgentAuction rejectedAuction;
    private static Player rejectedAuctionPlayer;
    private static String rejectedAuctionPlayerName = LocalDateTime.now().plusDays(3).toString();
    private static long rejecteddAuctionPlayerId = 4 ;
    private static int newFreeAgentAuctionId;

    @BeforeClass
    public static void setup() {
        MockPlayerDAO.clearMap();
        MockFreeAgentAuctionDAO.clearMap();
        MockMoneyDAO.clearMap();

        Money team1Money = new Money();
        team1Money.setTeam(team1);
        team1Money.setSeason(2015);
        team1Money.setMoneyType(Money.MoneyType.FREEAGENTAUCTION);
        team1Money.setAmount(100);
        MockMoneyDAO.addMoney(team1Money);

        Money team2Money = new Money();
        team2Money.setTeam(team2);
        team2Money.setSeason(2015);
        team2Money.setMoneyType(Money.MoneyType.FREEAGENTAUCTION);
        team2Money.setAmount(0);
        MockMoneyDAO.addMoney(team2Money);

        player = new Player();
        player.setPlayerName(playerName);
        player.setPlayerId(playerId);
        DailyPlayerInfo dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.FREEAGENT);
        dpi.setMlbStatus(PlayerStatus.DISABLEDLIST);
        player.getDailyPlayerInfoList().add(dpi);

        auctionPlayer = new Player();
        auctionPlayer.setPlayerName(auctionPlayerName);
        auctionPlayer.setPlayerId(auctionPlayerId);

        existingAuction = new FreeAgentAuction();
        existingAuction.setFreeAgentAuctionId(30);
        existingAuction.setPlayer(auctionPlayer);
        existingAuction.setStatus(FreeAgentAuction.Status.ACTIVE);

        cancelledAuctionPlayer = new Player();
        cancelledAuctionPlayer.setPlayerName(cancelledAuctionPlayerName);
        cancelledAuctionPlayer.setPlayerId(cancelledAuctionPlayerId);

        cancelledAuction = new FreeAgentAuction();
        cancelledAuction .setFreeAgentAuctionId(31);
        cancelledAuction .setPlayer(cancelledAuctionPlayer);
        cancelledAuction .setStatus(FreeAgentAuction.Status.CANCELLED);

        rejectedAuctionPlayer = new Player();
        rejectedAuctionPlayer.setPlayerName(rejectedAuctionPlayerName);
        rejectedAuctionPlayer.setPlayerId(rejecteddAuctionPlayerId);

        rejectedAuction = new FreeAgentAuction();
        rejectedAuction .setFreeAgentAuctionId(32);
        rejectedAuction .setPlayer(rejectedAuctionPlayer);
        rejectedAuction .setStatus(FreeAgentAuction.Status.DENIED);

        MockPlayerDAO.addPlayerToMapUsingId(player);
        MockPlayerDAO.addPlayerToMapUsingId(auctionPlayer);
        MockPlayerDAO.addPlayerToMapUsingId(cancelledAuctionPlayer);
        MockPlayerDAO.addPlayerToMapUsingId(rejectedAuctionPlayer);

        MockFreeAgentAuctionDAO.addToMapByPlayerId(existingAuction);
        MockFreeAgentAuctionDAO.addToMapByPlayerId(cancelledAuction);
        MockFreeAgentAuctionDAO.addToMapByPlayerId(rejectedAuction);
    }

    @Test
    public void a_requestFreeAgentAuction_Allowed() {
        FreeAgentAuction faa = null;
        try {
            faa = facade.requestFreeAgentAuction(player.getPlayerId(), team1);
            Assert.assertEquals(playerId, (long)faa.getPlayer().getPlayerId());
            Assert.assertEquals(FreeAgentAuction.Status.REQUESTED, faa.getStatus());

            newFreeAgentAuctionId = faa.getFreeAgentAuctionId();
        } catch (ActiveAuctionAlreadyExistsException e) {
            Assert.fail();
        } catch (PlayerNotFoundException e) {
            Assert.fail();
        }
    }

    @Test
    public void b_requestFreeAgentAuction_AlreadyExists() {
        FreeAgentAuction faa = null;
        try {
            faa = facade.requestFreeAgentAuction(existingAuction.getPlayer().getPlayerId(), team1);
            Assert.fail();
        } catch (ActiveAuctionAlreadyExistsException e) {
            Assert.assertNotNull(e);
        } catch (PlayerNotFoundException e) {
            Assert.fail();
        }
    }

    @Test
    public void bb_requestFreeAgentAuction_PlayerDoesNotExist() {
        FreeAgentAuction faa = null;
        try {
            faa = facade.requestFreeAgentAuction(1000, team1);
            Assert.fail();
        } catch (ActiveAuctionAlreadyExistsException e) {
            Assert.fail();
        } catch (PlayerNotFoundException e) {
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void c_requestFreeAgentAuction_PreviouslyCancelledOrDenied() {
        FreeAgentAuction faa = null;
        try {
            faa = facade.requestFreeAgentAuction(cancelledAuction.getPlayer().getPlayerId(), team1);

            Assert.assertEquals(cancelledAuctionPlayerId, (long)faa.getPlayer().getPlayerId());
            Assert.assertEquals(FreeAgentAuction.Status.REQUESTED, faa.getStatus());

            faa = facade.requestFreeAgentAuction(rejectedAuction.getPlayer().getPlayerId(), team1);

            Assert.assertEquals(rejecteddAuctionPlayerId, (long)faa.getPlayer().getPlayerId());
            Assert.assertEquals(FreeAgentAuction.Status.REQUESTED, faa.getStatus());
        } catch (ActiveAuctionAlreadyExistsException e) {
            Assert.fail();
        } catch (PlayerNotFoundException e) {
            Assert.fail();
        }
    }

    @Test
    public void d_approveFreeAgentAuction() {
        FreeAgentAuction faa = null;
        try {
            faa = facade.approveFreeAgentAuction(newFreeAgentAuctionId);
        } catch (NotFoundException e) {
            Assert.fail();
        }

        Assert.assertEquals(playerId, (long)faa.getPlayer().getPlayerId());
        Assert.assertEquals(FreeAgentAuction.Status.ACTIVE, faa.getStatus());
        Assert.assertNotNull(faa.getDeadline());
    }

    @Test
    public void e_bidOnFreeAgentAuction_HasEnoughFunds() {
        FreeAgentAuctionBid faab = null;
        try {
            faab = facade.bidOnFreeAgentAuction(newFreeAgentAuctionId, team1, 5);

            Assert.assertEquals(5, faab.getAmount());
            Assert.assertEquals(1, (int) faab.getTeam().getTeamId());
            Assert.assertEquals(newFreeAgentAuctionId, faab.getFreeAgentAuctionBidKey().getFreeAgentAuction().getFreeAgentAuctionId());
        } catch (NotEnoughFundsException e) {
            Assert.fail();
        } catch (NotFoundException e) {
            Assert.fail();
        }
    }

    @Test
    public void f_bidOnFreeAgentAuction_NotEnoughFunds() {
        FreeAgentAuctionBid faab = null;
        try {
            faab = facade.bidOnFreeAgentAuction(newFreeAgentAuctionId, team2, 1);

            Assert.fail();
        } catch (NotEnoughFundsException e) {
            Assert.assertNotNull(e);
        } catch (NotFoundException e) {
            Assert.fail();
        }
    }

    @Test
    public void g_bidOnFreeAgentAuction_zeroBidAllowed() {
        FreeAgentAuctionBid faab = null;
        try {
            faab = facade.bidOnFreeAgentAuction(newFreeAgentAuctionId, team2, 0);

            Assert.assertEquals(0, faab.getAmount());
            Assert.assertEquals(2, (int) faab.getTeam().getTeamId());
            Assert.assertEquals(newFreeAgentAuctionId, faab.getFreeAgentAuctionBidKey().getFreeAgentAuction().getFreeAgentAuctionId());
        } catch (NotEnoughFundsException e) {
            Assert.fail();
        } catch (NotFoundException e) {
            Assert.fail();
        }
    }

    @Test
    public void h_endFreeAgentAuction_WinningTeam() throws NoDailyPlayerInfoException {
        Team winningTeam = null;
        try {
            winningTeam = facade.endFreeAgentAuction(newFreeAgentAuctionId);

            Assert.assertEquals(team1.getTeamId(), winningTeam.getTeamId());
            IPlayerDAO playerDAO = IPlayerDAO.FACTORY.getInstance();
            Player dbPlayer = playerDAO.getPlayer(player);
            Assert.assertEquals(team1.getTeamId(), dbPlayer.getCurrentFantasyTeam().getTeamId());
        } catch (BiddingTiedException e) {
            Assert.fail();
        } catch (NotEnoughFundsException e) {
            Assert.fail();
        } catch (NotFoundException e) {
            Assert.fail();
        }
    }

    @Test
    public void i_endFreeAgentAuction_Tie() throws NotEnoughFundsException, NotFoundException {
        facade.bidOnFreeAgentAuction(newFreeAgentAuctionId, team1, 0);

        try {
            facade.endFreeAgentAuction(newFreeAgentAuctionId);

            Assert.fail();
        } catch (BiddingTiedException e) {
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void j_endFreeAgentAuction_NoBids() {
        MockFreeAgentAuctionDAO dao = new MockFreeAgentAuctionDAO();
        FreeAgentAuction faa = dao.getFreeAgentAuction(newFreeAgentAuctionId);
        faa.setFreeAgentAuctionBids(null);
        dao.saveFreeAgentAuction(faa);

        try {
            Team winningTeam = facade.endFreeAgentAuction(newFreeAgentAuctionId);
            Assert.assertNull(winningTeam);
        } catch (BiddingTiedException e) {
            Assert.fail();
        } catch (NotEnoughFundsException e) {
            Assert.fail();
        } catch (NotFoundException e) {
            Assert.fail();
        }
    }
}
