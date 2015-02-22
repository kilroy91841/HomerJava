package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
import com.homer.exception.IllegalVultureException;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.fantasy.*;
import com.homer.fantasy.dao.IPlayerDAO;
import com.homer.fantasy.dao.impl.MockPlayerDAO;
import com.homer.fantasy.dao.impl.MockVultureDAO;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by arigolub on 2/21/15.
 */
public class VultureFacadeTest {

    private static Player activeMLBactiveFantasy;
    private static Player dlMLBdlFantasy;
    private static Player minorMLBminorFantasy;

    private static Player minorMLBactiveFantasy;
    private static Player minorMLBdlFantasy;

    private static Player activeMLBminorFantasy;
    private static Player activeMLBdlFantasy;

    private static Player dlMLBactiveFantasy;
    private static Player dlMLBminorFantasy;

    private static Player anyMLBinactiveFantasy;

    private static Player hasRookieStatus;

    private static Player playerToDrop;

    private static Player beingVultured;
    private static Player wasVulturedNowFixed;

    private static Vulture existingVulture;
    private static Vulture fixedVulture;

    private VultureFacade facade = new VultureFacade();

    @Before
    public void setup() {
        activeMLBactiveFantasy = new Player();
        activeMLBactiveFantasy.setPlayerId(1);
        activeMLBactiveFantasy.setPlayerName("Active MLB Active Fantasy");
        DailyPlayerInfo dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.ACTIVE);
        dpi.setMlbStatus(PlayerStatus.ACTIVE);
        activeMLBactiveFantasy.getDailyPlayerInfoList().add(dpi);
        MockPlayerDAO.addPlayerToMapUsingId(activeMLBactiveFantasy);

        dlMLBdlFantasy = new Player();
        dlMLBdlFantasy.setPlayerId(2);
        dlMLBdlFantasy.setPlayerName("DL MLB DL Fantasy");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.DISABLEDLIST);
        dpi.setMlbStatus(PlayerStatus.DISABLEDLIST);
        dlMLBdlFantasy.getDailyPlayerInfoList().add(dpi);
        MockPlayerDAO.addPlayerToMapUsingId(dlMLBdlFantasy);

        minorMLBminorFantasy = new Player();
        minorMLBminorFantasy.setPlayerId(3);
        minorMLBminorFantasy.setPlayerName("Minor MLB Minor Fantasy");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.MINORS);
        dpi.setMlbStatus(PlayerStatus.MINORS);
        minorMLBminorFantasy.getDailyPlayerInfoList().add(dpi);
        MockPlayerDAO.addPlayerToMapUsingId(minorMLBminorFantasy);

        minorMLBactiveFantasy = new Player();
        minorMLBactiveFantasy.setPlayerId(4);
        minorMLBactiveFantasy.setPlayerName("Minor MLB Active Fantasy");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.ACTIVE);
        dpi.setMlbStatus(PlayerStatus.MINORS);
        minorMLBactiveFantasy.getDailyPlayerInfoList().add(dpi);
        MockPlayerDAO.addPlayerToMapUsingId(minorMLBactiveFantasy);

        minorMLBdlFantasy = new Player();
        minorMLBdlFantasy.setPlayerId(5);
        minorMLBdlFantasy.setPlayerName("Minor MLB DL Fantasy");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.DISABLEDLIST);
        dpi.setMlbStatus(PlayerStatus.MINORS);
        minorMLBdlFantasy.getDailyPlayerInfoList().add(dpi);
        MockPlayerDAO.addPlayerToMapUsingId(minorMLBdlFantasy);

        activeMLBminorFantasy = new Player();
        activeMLBminorFantasy.setPlayerId(6);
        activeMLBminorFantasy.setPlayerName("Active MLB Minor Fantasy");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.MINORS);
        dpi.setMlbStatus(PlayerStatus.ACTIVE);
        PlayerHistory history = new PlayerHistory();
        history.setRookieStatus(false);
        activeMLBminorFantasy.getPlayerHistoryList().add(history);
        activeMLBminorFantasy.getDailyPlayerInfoList().add(dpi);
        MockPlayerDAO.addPlayerToMapUsingId(activeMLBminorFantasy);

        activeMLBdlFantasy = new Player();
        activeMLBdlFantasy.setPlayerId(7);
        activeMLBdlFantasy.setPlayerName("Active MLB DL Fantasy");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.DISABLEDLIST);
        dpi.setMlbStatus(PlayerStatus.ACTIVE);
        history = new PlayerHistory();
        history.setRookieStatus(false);
        activeMLBdlFantasy.getPlayerHistoryList().add(history);
        activeMLBdlFantasy.getDailyPlayerInfoList().add(dpi);
        MockPlayerDAO.addPlayerToMapUsingId(activeMLBdlFantasy);

        dlMLBactiveFantasy = new Player();
        dlMLBactiveFantasy.setPlayerId(8);
        dlMLBactiveFantasy.setPlayerName("DL MLB Active Fantasy");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.ACTIVE);
        dpi.setMlbStatus(PlayerStatus.DISABLEDLIST);
        dlMLBactiveFantasy.getDailyPlayerInfoList().add(dpi);
        MockPlayerDAO.addPlayerToMapUsingId(dlMLBactiveFantasy);

        dlMLBminorFantasy = new Player();
        dlMLBminorFantasy.setPlayerId(9);
        dlMLBminorFantasy.setPlayerName("DL MLB Minor Fantasy");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.MINORS);
        dpi.setMlbStatus(PlayerStatus.DISABLEDLIST);
        dpi.setMlbStatus(PlayerStatus.ACTIVE);
        history = new PlayerHistory();
        history.setRookieStatus(false);
        dlMLBminorFantasy.getPlayerHistoryList().add(history);
        dlMLBminorFantasy.getDailyPlayerInfoList().add(dpi);
        MockPlayerDAO.addPlayerToMapUsingId(dlMLBminorFantasy);

        anyMLBinactiveFantasy = new Player();
        anyMLBinactiveFantasy.setPlayerId(10);
        anyMLBinactiveFantasy.setPlayerName("Any MLB Inactive Fantasy");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.INACTIVE);
        dpi.setMlbStatus(PlayerStatus.UNKNOWN);
        anyMLBinactiveFantasy.getDailyPlayerInfoList().add(dpi);
        MockPlayerDAO.addPlayerToMapUsingId(anyMLBinactiveFantasy);

        playerToDrop = new Player();
        playerToDrop.setPlayerId(11);
        playerToDrop.setPlayerName("Player to drop");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.ACTIVE);
        dpi.setMlbStatus(PlayerStatus.ACTIVE);
        dpi.setFantasyTeam(new Team(1));
        playerToDrop.getDailyPlayerInfoList().add(dpi);
        MockPlayerDAO.addPlayerToMapUsingId(playerToDrop);

        hasRookieStatus = new Player();
        hasRookieStatus.setPlayerId(12);
        hasRookieStatus.setPlayerName("Has rookie status");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.MINORS);
        dpi.setMlbStatus(PlayerStatus.ACTIVE);
        dpi.setFantasyTeam(new Team(1));
        hasRookieStatus.getDailyPlayerInfoList().add(dpi);
        history = new PlayerHistory();
        history.setRookieStatus(true);
        hasRookieStatus.getPlayerHistoryList().add(history);
        MockPlayerDAO.addPlayerToMapUsingId(hasRookieStatus);

        beingVultured = new Player();
        beingVultured.setPlayerId(13);
        beingVultured.setPlayerName("Being Vultured");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.ACTIVE);
        dpi.setMlbStatus(PlayerStatus.DISABLEDLIST);
        dpi.setFantasyTeam(new Team(2));
        beingVultured.getDailyPlayerInfoList().add(dpi);
        history = new PlayerHistory();
        history.setRookieStatus(false);
        beingVultured.getPlayerHistoryList().add(history);
        MockPlayerDAO.addPlayerToMapUsingId(beingVultured);

        wasVulturedNowFixed = new Player();
        wasVulturedNowFixed.setPlayerId(14);
        wasVulturedNowFixed.setPlayerName("Was Vultured Now Fixed");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyStatus(PlayerStatus.ACTIVE);
        dpi.setMlbStatus(PlayerStatus.ACTIVE);
        dpi.setFantasyTeam(new Team(2));
        wasVulturedNowFixed.getDailyPlayerInfoList().add(dpi);
        history = new PlayerHistory();
        history.setRookieStatus(false);
        wasVulturedNowFixed.getPlayerHistoryList().add(history);
        MockPlayerDAO.addPlayerToMapUsingId(wasVulturedNowFixed);

        existingVulture = new Vulture();
        existingVulture.setVultureStatus(Vulture.Status.ACTIVE);
        existingVulture.setDroppingPlayer(hasRookieStatus);
        existingVulture.setPlayer(beingVultured);
        existingVulture.setOffendingTeam(new Team(2));
        existingVulture.setVulturingTeam(new Team(1));
        MockVultureDAO.addVultureToMap(existingVulture);

        fixedVulture = new Vulture();
        fixedVulture.setVultureStatus(Vulture.Status.ACTIVE);
        fixedVulture.setDroppingPlayer(hasRookieStatus);
        fixedVulture.setPlayer(wasVulturedNowFixed);
        fixedVulture.setOffendingTeam(new Team(2));
        fixedVulture.setVulturingTeam(new Team(1));
        MockVultureDAO.addVultureToMap(existingVulture);
    }

    @Test
    public void createVulture() throws NoDailyPlayerInfoException {
        Vulture vulture = null;
        try {
            vulture = facade.createVulture(activeMLBactiveFantasy, new Team(1), playerToDrop);
        	Assert.fail();
        } catch (IllegalVultureException e) {
            Assert.assertTrue(true);
        }

        try {
            vulture = facade.createVulture(dlMLBdlFantasy, new Team(1), playerToDrop);
        	Assert.fail();
        } catch (IllegalVultureException e) {
            Assert.assertTrue(true);
        }

        try {
            vulture = facade.createVulture(minorMLBminorFantasy, new Team(1), playerToDrop);
        	Assert.fail();
        } catch (IllegalVultureException e) {
            Assert.assertTrue(true);
        }

        try {
            vulture = facade.createVulture(minorMLBactiveFantasy, new Team(1), playerToDrop);
        } catch (IllegalVultureException e) {
            Assert.fail();
        }
        Assert.assertNotNull(vulture);

        try {
            vulture = facade.createVulture(minorMLBdlFantasy, new Team(1), playerToDrop);
        } catch (IllegalVultureException e) {
            Assert.fail();
        }
        Assert.assertNotNull(vulture);

        try {
            vulture = facade.createVulture(activeMLBminorFantasy, new Team(1), playerToDrop);
        } catch (IllegalVultureException e) {
            Assert.fail();
        }
        Assert.assertNotNull(vulture);

        try {
            vulture = facade.createVulture(activeMLBdlFantasy, new Team(1), playerToDrop);
        } catch (IllegalVultureException e) {
            Assert.fail();
        }
        Assert.assertNotNull(vulture);

        try {
            vulture = facade.createVulture(dlMLBactiveFantasy, new Team(1), playerToDrop);
        } catch (IllegalVultureException e) {
            Assert.fail();
        }
        Assert.assertNotNull(vulture);

        try {
            vulture = facade.createVulture(dlMLBminorFantasy, new Team(1), playerToDrop);
        } catch (IllegalVultureException e) {
            Assert.fail();
        }
        Assert.assertNotNull(vulture);

        try {
            vulture = facade.createVulture(anyMLBinactiveFantasy, new Team(1), playerToDrop);
        } catch (IllegalVultureException e) {
            Assert.fail();
        }
        Assert.assertNotNull(vulture);

        try {
            vulture = facade.createVulture(hasRookieStatus, new Team(1), playerToDrop);
        	Assert.fail();
        } catch (IllegalVultureException e) {
            Assert.assertTrue(true);
        }

        try {
            vulture = facade.createVulture(dlMLBactiveFantasy, new Team(1), null);
        } catch (IllegalVultureException e) {
            Assert.fail();
        }
        Assert.assertNotNull(vulture);
    }

    @Test
    public void resolveVulture() throws NoDailyPlayerInfoException {
        Vulture resolvedVulture = facade.resolveVulture(existingVulture);
        Assert.assertEquals(Vulture.Status.GRANTED, resolvedVulture.getVultureStatus());

        Player vulturedPlayer = IPlayerDAO.FACTORY.getInstance().getPlayer(resolvedVulture.getPlayer());
        Player droppedPlayer = IPlayerDAO.FACTORY.getInstance().getPlayer(resolvedVulture.getDroppingPlayer());
        Assert.assertEquals(1, (int)vulturedPlayer.getCurrentFantasyTeam().getTeamId());
        Assert.assertEquals(0, (int)droppedPlayer.getCurrentFantasyTeam().getTeamId());
    }

    @Test
    public void resolveVulture_Fixed() throws NoDailyPlayerInfoException {
        Vulture resolvedVulture = facade.resolveVulture(fixedVulture);
        Assert.assertEquals(Vulture.Status.RESOLVED, resolvedVulture.getVultureStatus());

        Player vulturedPlayer = IPlayerDAO.FACTORY.getInstance().getPlayer(resolvedVulture.getPlayer());
        Player droppedPlayer = IPlayerDAO.FACTORY.getInstance().getPlayer(resolvedVulture.getDroppingPlayer());
        Assert.assertEquals(2, (int)vulturedPlayer.getCurrentFantasyTeam().getTeamId());
        Assert.assertEquals(1, (int)droppedPlayer.getCurrentFantasyTeam().getTeamId());
    }
}
