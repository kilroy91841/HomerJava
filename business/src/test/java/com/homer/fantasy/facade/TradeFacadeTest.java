package com.homer.fantasy.facade;

import com.homer.exception.DisallowedTradeException;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.fantasy.*;
import com.homer.fantasy.dao.IMinorLeagueDraftPickDAO;
import com.homer.fantasy.dao.IMoneyDAO;
import com.homer.fantasy.dao.IPlayerDAO;
import com.homer.fantasy.dao.ITradeDAO;
import com.homer.fantasy.dao.impl.MockMinorLeagueDraftPickDAO;
import com.homer.fantasy.dao.impl.MockMoneyDAO;
import com.homer.fantasy.dao.impl.MockPlayerDAO;
import com.homer.fantasy.dao.impl.MockTradeDAO;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by arigolub on 2/22/15.
 */
public class TradeFacadeTest {

    private static Player player1Team1;
    private static Player player2Team1;
    private static Player player1Team2;
    private static Player player2Team2;
    private static Team team1;
    private static Team team2;
    private static List<Player> team1Players;
    private static List<Player> team2Players;
    private static List<Money> team1Moneys;
    private static List<Money> team2MoneysInsufficientFunds;
    private static List<Money> team2MoneysSufficientFunds;
    private static List<MinorLeagueDraftPick> team1DraftPicks;
    private static List<MinorLeagueDraftPick> team2DraftPicks;
    private static TradeAsset asset1;
    private static TradeAsset asset2;
    private static TradeAsset asset3;
    private static TradeAsset asset4;
    private static TradeAsset asset5;
    private static MinorLeagueDraftPick draftPick;
    private static Money money;
    private static Money lotOfMoney;

    private static final TradeFacade facade = new TradeFacade();

    @Before
    public void setup() {
        MockTradeDAO.clearMap();
        MockPlayerDAO.clearMap();
        MockMoneyDAO.clearMap();
        MockMinorLeagueDraftPickDAO.clearMap();

        team1 = new Team();
        team1.setTeamId(1);

        team2 = new Team();
        team2.setTeamId(2);

        Money dbMoney = new Money();
        dbMoney.setTeam(team2);
        dbMoney.setMoneyType(Money.MoneyType.MAJORLEAGUEDRAFT);
        dbMoney.setSeason(2015);
        dbMoney.setAmount(225);
        MockMoneyDAO.addMoney(dbMoney);

        Money dbMoney2 = new Money();
        dbMoney2.setTeam(team1);
        dbMoney2.setMoneyType(Money.MoneyType.MAJORLEAGUEDRAFT);
        dbMoney2.setSeason(2015);
        dbMoney2.setAmount(260);
        MockMoneyDAO.addMoney(dbMoney2);

        player1Team1 = initializePlayer(team1);
        player2Team1 = initializePlayer(team1);
        player1Team2 = initializePlayer(team2);
        player2Team2 = initializePlayer(team2);

        money = new Money();
        money.setTeam(team2);
        money.setMoneyType(Money.MoneyType.MAJORLEAGUEDRAFT);
        money.setSeason(2015);
        money.setAmount(5);

        lotOfMoney = new Money();
        lotOfMoney.setTeam(team2);
        lotOfMoney.setMoneyType(Money.MoneyType.MAJORLEAGUEDRAFT);
        lotOfMoney.setSeason(2015);
        lotOfMoney.setAmount(50);

        draftPick = new MinorLeagueDraftPick();
        draftPick.setOriginalTeam(team1);
        draftPick.setRound(1);
        draftPick.setSeason(2015);
        draftPick.setOwningTeam(team1);

        MockMinorLeagueDraftPickDAO.addMinorLeagueDraftPick(draftPick);

        MockPlayerDAO.addPlayerToMapUsingId(player1Team1);
        MockPlayerDAO.addPlayerToMapUsingId(player1Team2);
        MockPlayerDAO.addPlayerToMapUsingId(player2Team1);
        MockPlayerDAO.addPlayerToMapUsingId(player2Team2);

        team1Players = new ArrayList<Player>();
        team1Players.add(player1Team1);
        team1Players.add(player2Team1);
        team2Players = new ArrayList<Player>();
        team2Players.add(player1Team2);
        team2Players.add(player2Team2);
        team1Moneys = new ArrayList<Money>();

        team2MoneysInsufficientFunds = new ArrayList<Money>();
        team2MoneysInsufficientFunds.add(lotOfMoney);
        team2MoneysSufficientFunds = new ArrayList<Money>();
        team2MoneysSufficientFunds.add(money);
        team1DraftPicks = new ArrayList<MinorLeagueDraftPick>();
        team1DraftPicks.add(draftPick);
        team2DraftPicks = new ArrayList<MinorLeagueDraftPick>();

        Trade trade = new Trade();
        trade.setProposingTeam(team1);
        trade.setProposedToTeam(team2);
        trade.setCreatedDate(LocalDateTime.now());
        trade.setDeadline(LocalDateTime.now().plusDays(3));
        trade.setTradeStatus(Trade.Status.PROPOSED);

        asset1 = new TradeAsset();
        asset1.setTrade(trade);
        asset1.setPlayer(player1Team1);
        asset1.setTeam(team1);
        trade.getTradeAssets().add(asset1);

        asset2 = new TradeAsset();
        asset2.setTrade(trade);
        asset2.setPlayer(player2Team1);
        asset2.setTeam(team1);
        trade.getTradeAssets().add(asset2);

        asset3 = new TradeAsset();
        asset3.setTrade(trade);
        asset3.setPlayer(player1Team2);
        asset3.setTeam(team2);
        trade.getTradeAssets().add(asset3);

        asset4 = new TradeAsset();
        asset4.setTrade(trade);
        asset4.setMinorLeagueDraftPick(draftPick);
        asset4.setTeam(team1);
        trade.getTradeAssets().add(asset4);

        asset5 = new TradeAsset();
        asset5.setTrade(trade);
        asset5.setTeam(team2);
        asset5.setMoney(money);
        trade.getTradeAssets().add(asset5);

        trade.setTradeId(1);

        MockTradeDAO.addTrade(trade);
    }

    @Test
    public void createTrade() {
        Trade trade = null;
        try {
            trade = facade.createTrade(team1, team2, team1Players, team2Players, team1Moneys, team2MoneysSufficientFunds, team1DraftPicks, team2DraftPicks);
            Assert.assertNotNull(trade);
            Assert.assertEquals(Trade.Status.PROPOSED, trade.getTradeStatus());
        } catch (DisallowedTradeException e) {
            Assert.fail();
        }
    }

    @Test
    public void createTradeOverdraft() {
        Trade trade = null;
        try {
            trade = facade.createTrade(team1, team2, team1Players, team2Players, team1Moneys, team2MoneysInsufficientFunds, team1DraftPicks, team2DraftPicks);
            Assert.fail();
        } catch (DisallowedTradeException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void createDraft() {
        try {
            Trade trade = facade.saveDraft(team1, team2, team1Players, team2Players, team1Moneys, team2MoneysSufficientFunds, team1DraftPicks, team2DraftPicks);
            Assert.assertNotNull(trade);
            Assert.assertEquals(Trade.Status.DRAFT, trade.getTradeStatus());
        } catch (DisallowedTradeException e) {
            Assert.fail();
        }
    }

    @Test
    public void acceptTrade() throws NoDailyPlayerInfoException {
        try {
            Assert.assertEquals(team1.getTeamId(), player1Team1.getCurrentFantasyTeam().getTeamId());
            Assert.assertEquals(team1.getTeamId(), player2Team1.getCurrentFantasyTeam().getTeamId());
            Assert.assertEquals(team2.getTeamId(), player1Team2.getCurrentFantasyTeam().getTeamId());

            Trade trade = new Trade();
            trade.setTradeId(1);
            trade = facade.acceptTrade(trade);
            Assert.assertNotNull(trade);
            Assert.assertEquals(Trade.Status.ACCEPTED, trade.getTradeStatus());

            IPlayerDAO playerDAO = IPlayerDAO.FACTORY.getInstance();
            Player dbPlayer1Team1 = playerDAO.getPlayer(player1Team1);
            Assert.assertEquals(team2.getTeamId(), dbPlayer1Team1.getCurrentFantasyTeam().getTeamId());
            Player dbPlayer2Team1 = playerDAO.getPlayer(player2Team1);
            Assert.assertEquals(team2.getTeamId(), dbPlayer2Team1.getCurrentFantasyTeam().getTeamId());
            Player dbPlayer1Team2 = playerDAO.getPlayer(player1Team2);
            Assert.assertEquals(team1.getTeamId(), dbPlayer1Team2.getCurrentFantasyTeam().getTeamId());

            IMinorLeagueDraftPickDAO minorLeagueDraftPickDAO = IMinorLeagueDraftPickDAO.FACTORY.getInstance();
            MinorLeagueDraftPick dbPick = minorLeagueDraftPickDAO.getDraftPick(draftPick.getOriginalTeam(), draftPick.getSeason(), draftPick.getRound());
            Assert.assertEquals(2, (int)dbPick.getOwningTeam().getTeamId());

            IMoneyDAO moneyDao = IMoneyDAO.FACTORY.getInstance();
            Money dbMoney = moneyDao.getMoney(2, 2015, Money.MoneyType.MAJORLEAGUEDRAFT);
            Assert.assertEquals(220, (int)dbMoney.getAmount());
            dbMoney = moneyDao.getMoney(1, 2015, Money.MoneyType.MAJORLEAGUEDRAFT);
            Assert.assertEquals(265, (int)dbMoney.getAmount());

        } catch (DisallowedTradeException e) {
            Assert.fail();
        }
    }

    @Test
    public void declineTrade() {
        ITradeDAO dao = ITradeDAO.FACTORY.getInstance();
        Trade trade = dao.getTradeById(1);
        trade = facade.declineTrade(trade);
        Assert.assertNotNull(trade);
        Assert.assertEquals(Trade.Status.DECLINED, trade.getTradeStatus());
    }

    @Test
    public void cancelTrade() {
        ITradeDAO dao = ITradeDAO.FACTORY.getInstance();
        Trade trade = dao.getTradeById(1);
        trade = facade.cancelTrade(trade);
        Assert.assertNotNull(trade);
        Assert.assertEquals(Trade.Status.CANCELLED, trade.getTradeStatus());
    }

    @Test
    public void counterTrade() {
        try {
            ITradeDAO dao = ITradeDAO.FACTORY.getInstance();
            Trade trade = dao.getTradeById(1);
            Trade newTrade = facade.counterTrade(trade, team2Players, team1Players, team2MoneysSufficientFunds, team1Moneys, team2DraftPicks, team1DraftPicks);
            trade = dao.getTradeById(1);
            Assert.assertNotNull(newTrade);
            Assert.assertTrue(trade.getTradeId() != newTrade.getTradeId());
            Assert.assertEquals(Trade.Status.DECLINED, trade.getTradeStatus());
            Assert.assertEquals(Trade.Status.PROPOSED, newTrade.getTradeStatus());
        } catch (DisallowedTradeException e) {
            Assert.fail();
        }
    }

    private Player initializePlayer(Team team) {
        Random rand = new Random();
        Player player = new Player();
        player.setPlayerId(rand.nextInt((1000 - 0) + 1) + 0);
        DailyPlayerInfo dpi = new DailyPlayerInfo();
        dpi.setFantasyTeam(team);
        player.getDailyPlayerInfoList().add(dpi);
        return player;
    }
}
