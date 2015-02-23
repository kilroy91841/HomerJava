package com.homer.fantasy.dao;

import com.homer.fantasy.*;
import com.homer.fantasy.dao.impl.HibernateTradeDAO;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by arigolub on 2/22/15.
 */
public class HibernateTradeDAOTest {

    private static final HibernateTradeDAO dao = new HibernateTradeDAO();

    private static Player player1;
    private static Player player2;

    private static Team team1;
    private static Team team2;

    @BeforeClass
    public static void setup() {
        team1 = new Team();
        team1.setTeamId(1);

        team2 = new Team();
        team2.setTeamId(2);

        player1 = new Player(1);
        player1.setPlayerName("Player 1");
        DailyPlayerInfo dpi = new DailyPlayerInfo();
        dpi.setFantasyTeam(team1);
        player1.getDailyPlayerInfoList().add(dpi);

        player2 = new Player(2);
        player2.setPlayerName("Player 2");
        dpi = new DailyPlayerInfo();
        dpi.setFantasyTeam(team2);
        player2.getDailyPlayerInfoList().add(dpi);
    }

    @Test
    public void createTrade() {
        Trade trade = new Trade();
        trade.setTradeStatus(Trade.Status.DRAFT);
        trade.setProposingTeam(team1);
        trade.setProposedToTeam(team2);
        TradeAsset asset1 = new TradeAsset();
        asset1.setTeam(team1);
        asset1.setTrade(trade);
        asset1.setPlayer(player1);
        trade.getTradeAssets().add(asset1);

        trade = dao.saveTrade(trade);
        Assert.assertNotNull(trade);
        Assert.assertNotNull(trade.getTradeId());
        Assert.assertEquals(Trade.Status.DRAFT, trade.getTradeStatus());
        Assert.assertEquals(1, trade.getTradeAssets().size());

        int tradeId = trade.getTradeId();

        TradeAsset asset2 = new TradeAsset();
        asset2.setTeam(team2);
        asset2.setTrade(trade);
        asset2.setPlayer(player2);
        trade.getTradeAssets().add(asset2);

        trade.setTradeStatus(Trade.Status.PROPOSED);
        dao.saveTrade(trade);
        Assert.assertEquals(Trade.Status.PROPOSED, trade.getTradeStatus());
        Assert.assertEquals(2, trade.getTradeAssets().size());

        Assert.assertEquals(tradeId, trade.getTradeId());
    }
}
