package com.homer.fantasy.dao;

import com.homer.fantasy.FreeAgentAuction;
import com.homer.fantasy.FreeAgentAuctionBid;
import com.homer.fantasy.Player;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.impl.HibernateFreeAgentAuctionDAO;
import com.homer.fantasy.dao.impl.HibernatePlayerDAO;
import com.homer.fantasy.key.FreeAgentAuctionBidKey;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
* Created by arigolub on 2/28/15.
*/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HibernateFreeAgentAuctionDAOTest {

    private HibernateFreeAgentAuctionDAO dao = new HibernateFreeAgentAuctionDAO();

    private static Player player;
    private static Long playerId;
    private static String playerName = LocalDateTime.now().toString();
    private static int freeAgentAuctionId;
    private static Team requestingTeam = new Team(1);

    @BeforeClass
    public static void setup() {
        player = new Player();
        player.setPlayerName(playerName);

        HibernatePlayerDAO playerDAO = new HibernatePlayerDAO();
        player = playerDAO.createOrSave(player);
        playerId = player.getPlayerId();
    }

    @Test
    public void a_createFreeAgentAuction() {
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        FreeAgentAuction faa = new FreeAgentAuction();
        faa.setPlayer(player);
        faa.setDeadline(deadline);
        faa.setRequestingTeam(requestingTeam);
        faa.setStatus(FreeAgentAuction.Status.REQUESTED);

        FreeAgentAuction dbFaa = dao.saveFreeAgentAuction(faa);
        Assert.assertNotNull(dbFaa.getFreeAgentAuctionId());
        freeAgentAuctionId = dbFaa.getFreeAgentAuctionId();
        Assert.assertEquals(playerId, dbFaa.getPlayer().getPlayerId());
        Assert.assertEquals(FreeAgentAuction.Status.REQUESTED, dbFaa.getStatus());
        Assert.assertEquals(deadline, dbFaa.getDeadline());
    }

    @Test
    public void b_getFreeAgentAuction() {
        FreeAgentAuction dbFaa = dao.getFreeAgentAuction(freeAgentAuctionId);
        Assert.assertNotNull(dbFaa.getFreeAgentAuctionId());
        Assert.assertEquals(playerId, dbFaa.getPlayer().getPlayerId());
        Assert.assertEquals(FreeAgentAuction.Status.REQUESTED, dbFaa.getStatus());
    }

    @Test
    public void ba_getFreeAgentAuctionByPlayerId() {
        FreeAgentAuction dbFaa = dao.getFreeAgentAuctionByPlayerId(playerId);
        Assert.assertNotNull(dbFaa.getFreeAgentAuctionId());
        Assert.assertEquals(playerId, dbFaa.getPlayer().getPlayerId());
        Assert.assertEquals(FreeAgentAuction.Status.REQUESTED, dbFaa.getStatus());
    }

    @Test
    public void c_updateFreeAgentAuction() {
        FreeAgentAuction dbFaa = dao.getFreeAgentAuction(freeAgentAuctionId);

        dbFaa.setStatus(FreeAgentAuction.Status.ACTIVE);

        dbFaa = dao.saveFreeAgentAuction(dbFaa);
        Assert.assertEquals(FreeAgentAuction.Status.ACTIVE, dbFaa.getStatus());
        Assert.assertEquals(freeAgentAuctionId, dbFaa.getFreeAgentAuctionId());
    }

    @Test
    public void d_getFreeAgentAuctionByStatus() {
        List<FreeAgentAuction> activeList = dao.getFreeAgentAuctionsByStatus(FreeAgentAuction.Status.ACTIVE);
        List<FreeAgentAuction> requestedList = dao.getFreeAgentAuctionsByStatus(FreeAgentAuction.Status.REQUESTED);

        Assert.assertNotNull(activeList);
        Assert.assertNotNull(requestedList);

        Assert.assertTrue(activeList.size() > 0 );
        Assert.assertTrue(requestedList.size() > 0);
        Assert.assertTrue(activeList.size() != requestedList.size());
    }

    @Test
    public void e_saveBid() {
        FreeAgentAuction dbFaa = dao.getFreeAgentAuction(freeAgentAuctionId);

        FreeAgentAuctionBid bid = new FreeAgentAuctionBid();
        FreeAgentAuctionBidKey key = new FreeAgentAuctionBidKey();
        key.setTeam(requestingTeam);
        key.setFreeAgentAuction(dbFaa);
        bid.setFreeAgentAuctionBidKey(key);
        bid.setAmount(50);

        dbFaa.getFreeAgentAuctionBids().add(bid);

        dbFaa = dao.saveFreeAgentAuction(dbFaa);

        boolean found = false;
        Assert.assertEquals(freeAgentAuctionId, dbFaa.getFreeAgentAuctionId());
        Assert.assertTrue(dbFaa.getFreeAgentAuctionBids().size() > 0);
        for(FreeAgentAuctionBid dbBid : dbFaa.getFreeAgentAuctionBids()) {
            if(bid.equals(dbBid)) {
                found = true;
            }
        }
        Assert.assertTrue(found);
    }

    @Test
    public void f_updateBid() {
        FreeAgentAuction dbFaa = dao.getFreeAgentAuction(freeAgentAuctionId);
        Assert.assertTrue(dbFaa.getFreeAgentAuctionBids().size() == 1);

        FreeAgentAuctionBid bid = new FreeAgentAuctionBid();
        FreeAgentAuctionBidKey key = new FreeAgentAuctionBidKey();
        key.setTeam(requestingTeam);
        key.setFreeAgentAuction(dbFaa);
        bid.setFreeAgentAuctionBidKey(key);
        bid.setAmount(25);

        dbFaa.getFreeAgentAuctionBids().remove(bid);
        dbFaa.getFreeAgentAuctionBids().add(bid);

        dbFaa = dao.saveFreeAgentAuction(dbFaa);

        boolean updated = false;
        Assert.assertEquals(freeAgentAuctionId, dbFaa.getFreeAgentAuctionId());
        Assert.assertTrue(dbFaa.getFreeAgentAuctionBids().size() == 1);
        for(FreeAgentAuctionBid dbBid : dbFaa.getFreeAgentAuctionBids()) {
            if(bid.equals(dbBid)) {
                if(dbBid.getAmount() == 25) {
                    updated = true;
                }
            }
        }
        Assert.assertTrue(updated);
    }
}
