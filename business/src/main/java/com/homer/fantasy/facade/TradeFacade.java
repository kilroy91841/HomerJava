package com.homer.fantasy.facade;

import com.homer.exception.DisallowedTradeException;
import com.homer.exception.DisallowedTransactionException;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.fantasy.*;
import com.homer.fantasy.dao.ITradeDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by arigolub on 2/22/15.
 */
public class TradeFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TradeFacade.class);
    private static ITradeDAO dao;
    private static PlayerFacade playerFacade;
    private static MoneyFacade moneyFacade;
    private static RosterFacade rosterFacade;

    public TradeFacade() {
        dao = ITradeDAO.FACTORY.getInstance();
        playerFacade = new PlayerFacade();
        moneyFacade = new MoneyFacade();
    }

    public Trade createTrade(Team proposingTeam, Team proposedToTeam,
                             List<Player> proposingTeamPlayers, List<Player> proposedToTeamPlayers,
                             List<Money> proposingTeamMoney, List<Money> proposedToTeamMoney,
                             List<MinorLeagueDraftPick> proposingTeamDraftPicks, List<MinorLeagueDraftPick> proposedToTeamDraftPicks)
        throws DisallowedTradeException {
        LOG.debug("BEGIN: createTrade");
        Trade trade = createTradeHelper(proposingTeam, proposedToTeam, proposingTeamPlayers, proposedToTeamPlayers,
                proposingTeamMoney, proposedToTeamMoney, proposingTeamDraftPicks, proposedToTeamDraftPicks, false);
        LOG.debug("END: createTrade");
        return trade;
    }

    private Trade createTradeHelper(Team proposingTeam, Team proposedToTeam,
                             List<Player> proposingTeamPlayers, List<Player> proposedToTeamPlayers,
                             List<Money> proposingTeamMoney, List<Money> proposedToTeamMoney,
                             List<MinorLeagueDraftPick> proposingTeamDraftPicks, List<MinorLeagueDraftPick> proposedToTeamDraftPicks,
                             boolean isDraft)
            throws DisallowedTradeException {
        LOG.debug("BEGIN: createTradeHelper [isDraft=" + isDraft + "]");

        Set<TradeAsset> assets = new HashSet<TradeAsset>();

        //Verify everything is owned properly and would not break any rules by being traded
        assets.addAll(verifyPlayers(proposingTeam, proposedToTeam, proposingTeamPlayers, proposedToTeamPlayers));
        assets.addAll(verifyMoney(proposingTeam, proposedToTeam, proposingTeamMoney, proposedToTeamMoney));
        assets.addAll(verifyDraftPicks(proposingTeam, proposedToTeam, proposingTeamDraftPicks, proposedToTeamDraftPicks));

        //No exceptions have been thrown, create the trade
        Trade trade = new Trade();
        trade.setProposingTeam(proposingTeam);
        trade.setProposedToTeam(proposedToTeam);
        trade.setCreatedDate(LocalDateTime.now());
        if(!isDraft) {
            trade.setTradeStatus(Trade.Status.PROPOSED);
            trade.setDeadline(LocalDateTime.now().plusDays(5));
        } else {
            trade.setTradeStatus(Trade.Status.DRAFT);
        }

        assets.forEach((a) -> a.setTrade(trade));
        trade.setTradeAssets(assets);

        Trade dbTrade = dao.saveTrade(trade);

        LOG.debug("END: createTradeHelper [trade=" + dbTrade + "]");
        return dbTrade;
    }

    public Trade saveDraft(Team proposingTeam, Team proposedToTeam,
                           List<Player> proposingTeamPlayers, List<Player> proposedToTeamPlayers,
                           List<Money> proposingTeamMoney, List<Money> proposedToTeamMoney,
                           List<MinorLeagueDraftPick> proposingTeamDraftPicks, List<MinorLeagueDraftPick> proposedToTeamDraftPicks)
            throws DisallowedTradeException {
        LOG.debug("BEGIN: saveDraft");
        Trade trade = createTradeHelper(proposingTeam, proposedToTeam, proposingTeamPlayers, proposedToTeamPlayers,
                proposingTeamMoney, proposedToTeamMoney, proposingTeamDraftPicks, proposedToTeamDraftPicks, true);
        LOG.debug("END: saveDraft [trade=" + trade + "]");
        return trade;
    }

    public Trade acceptTrade(Trade trade) throws DisallowedTradeException {
        LOG.debug("BEGIN: acceptTrade");

        //Get trade from DB
        trade = dao.getTradeById(trade.getTradeId());

        //Create list objects for verification
        Team proposingTeam = trade.getProposingTeam();
        Team proposedToTeam = trade.getProposedToTeam();
        List<Player> proposingTeamPlayers = new ArrayList<Player>();
        List<Player> proposedToTeamPlayers = new ArrayList<Player>();
        List<Money> proposingTeamMoney = new ArrayList<Money>();
        List<Money> proposedToTeamMoney = new ArrayList<Money>();
        List<MinorLeagueDraftPick> proposingTeamDraftPicks = new ArrayList<MinorLeagueDraftPick>();
        List<MinorLeagueDraftPick> proposedToTeamDraftPicks = new ArrayList<MinorLeagueDraftPick>();

        LOG.debug("Creating lists from trade assets");
        Iterator<TradeAsset> iterator = trade.getTradeAssets().iterator();
        while(iterator.hasNext()) {
            TradeAsset ta = iterator.next();
            if(ta.getPlayer() != null) {
                if(ta.getTeam().equals(proposingTeam)) {
                    proposingTeamPlayers.add(ta.getPlayer());
                } else {
                    proposedToTeamPlayers.add(ta.getPlayer());
                }
            } else if(ta.getMoney() != null) {
                if(ta.getTeam().equals(proposingTeam)) {
                    proposingTeamMoney.add(ta.getMoney());
                } else {
                    proposedToTeamMoney.add(ta.getMoney());
                }
            } else if(ta.getMinorLeagueDraftPick() != null) {
                if(ta.getTeam().equals(proposingTeam)) {
                    proposingTeamDraftPicks.add(ta.getMinorLeagueDraftPick());
                } else {
                    proposedToTeamDraftPicks.add(ta.getMinorLeagueDraftPick());
                }
            }
        }

        //Verify everything is owned properly and would not break any rules by being traded
        verifyPlayers(proposingTeam, proposedToTeam, proposingTeamPlayers, proposedToTeamPlayers);
        verifyMoney(proposingTeam, proposedToTeam, proposingTeamMoney, proposedToTeamMoney);
        verifyDraftPicks(proposingTeam, proposedToTeam, proposingTeamDraftPicks, proposedToTeamDraftPicks);

        //No exceptions were thrown, proceed with trade acceptance
        trade.setTradeStatus(Trade.Status.ACCEPTED);

        boolean allSuccessful = true;
        //Transfer players to new teams
        for(Player p : proposingTeamPlayers) {
            Player tempPlayer = playerFacade.transferPlayer(p, proposingTeam, proposedToTeam);
            if(tempPlayer == null) {
                allSuccessful = false;
            }
        }
        for(Player p : proposedToTeamPlayers) {
            Player tempPlayer = playerFacade.transferPlayer(p, proposedToTeam, proposingTeam);
            if(tempPlayer == null) {
                allSuccessful = false;
            }
        }

        //TODO transfer draft picks
        //TODO transfer money

        trade = dao.saveTrade(trade);

        if(!allSuccessful) {
            LOG.error("There was an issue moving one of the traded entities for trade id " + trade.getTradeId());
        }

        LOG.debug("END: acceptTrade [trade=" + trade + ", allSuccessful=" + allSuccessful + "]");
        return trade;
    }

    public Trade declineTrade(Trade trade) {
        LOG.debug("BEGIN: declineTrade");

        trade = changeTradeStatus(trade, Trade.Status.DECLINED);

        LOG.debug("END: declineTrade [trade=" + trade + "]");
        return trade;
    }

    public Trade cancelTrade(Trade trade) {
        LOG.debug("BEGIN: cancelTrade");

        trade = changeTradeStatus(trade, Trade.Status.CANCELLED);

        LOG.debug("END: cancelTrade [trade=" + trade + "]");
        return trade;
    }

    public Trade counterTrade(Trade trade,
                              List<Player> proposingTeamPlayers, List<Player> proposedToTeamPlayers,
                              List<Money> proposingTeamMoney, List<Money> proposedToTeamMoney,
                              List<MinorLeagueDraftPick> proposingTeamDraftPicks, List<MinorLeagueDraftPick> proposedToTeamDraftPicks)
            throws DisallowedTradeException {
        LOG.debug("BEGIN: counterTrade");

        changeTradeStatus(trade, Trade.Status.DECLINED);

        Trade newTrade = createTradeHelper(trade.getProposedToTeam(), trade.getProposingTeam(),
                proposingTeamPlayers, proposedToTeamPlayers,
                proposingTeamMoney, proposedToTeamMoney, proposingTeamDraftPicks, proposedToTeamDraftPicks, false);

        LOG.debug("END: counterTrade [trade=" + newTrade + "]");
        return newTrade;
    }

    private Set<TradeAsset> verifyPlayers(Team proposingTeam, Team proposedToTeam,
                              List<Player> proposingTeamPlayers, List<Player> proposedToTeamPlayers) throws DisallowedTradeException {
        LOG.debug("BEGIN: verifyPlayers");
        Set<TradeAsset> playerAssets = new HashSet<TradeAsset>();
        try {
            LOG.debug("Verify players on team: " + proposingTeam);
            for (Player p : proposingTeamPlayers) {
                LOG.debug("Verifying player id: " + p.getPlayerId());
                p = playerFacade.getPlayer(p.getPlayerId());
                if (p.getCurrentFantasyTeam().getTeamId() != proposingTeam.getTeamId()) {
                    LOG.debug("Player does not have daily player info");
                    throw new DisallowedTradeException("Player " + p.getPlayerId() + " is not owned by team " + proposingTeam.getTeamId());
                }
                playerAssets.add(createTradeAsset(proposingTeam, p, null, null));
            }

            //TODO implement roster verification: wouldn't go over 10 minor leaguers

            LOG.debug("Verify players on team: " + proposedToTeam);
            for (Player p : proposedToTeamPlayers) {
                LOG.debug("Verifying player id: " + p.getPlayerId());
                p = playerFacade.getPlayer(p.getPlayerId());
                if (p.getCurrentFantasyTeam().getTeamId() != proposedToTeam.getTeamId()) {
                    LOG.debug("Player is not on team");
                    throw new DisallowedTradeException("Player " + p.getPlayerId() + " is not owned by team " + proposedToTeam.getTeamId());
                }
                playerAssets.add(createTradeAsset(proposedToTeam, p, null, null));
            }

            //TODO implement roster verification: wouldn't go over 10 minor leaguers
        } catch (NoDailyPlayerInfoException e) {
            LOG.error("No daily info found for player", e);
            throw new DisallowedTradeException("A player in the trade was in an invalid state");
        }

        LOG.debug("Verification was successful, trade can continue");
        LOG.debug("END: verifyPlayers");
        return playerAssets;
    }

    private Set<TradeAsset> verifyMoney(Team proposingTeam, Team proposedToTeam,
                            List<Money> proposingTeamMoney, List<Money> proposedToTeamMoney) throws DisallowedTradeException {
        LOG.debug("BEGIN: verifyMoney");
        Set<TradeAsset> moneyAssets = new HashSet<TradeAsset>();
        try {
            LOG.debug("Verify money for team " + proposingTeam);
            for(Money m : proposingTeamMoney) {
                
            }
        }
        LOG.debug("END: verifyMoney");
        return new HashSet<TradeAsset>();
    }

    private Set<TradeAsset> verifyDraftPicks(Team proposingTeam, Team proposedToTeam,
                             List<MinorLeagueDraftPick> proposingTeamDraftPicks, List<MinorLeagueDraftPick> proposedToTeamDraftPicks)
            throws DisallowedTradeException {
        //TODO implement verify draft picks
        return new HashSet<TradeAsset>();
    }

    private TradeAsset createTradeAsset(Team team, Player player, Money money, MinorLeagueDraftPick draftPick) {
        TradeAsset asset = new TradeAsset();
        asset.setTeam(team);
        asset.setPlayer(player);
        asset.setMoney(money);
        asset.setMinorLeagueDraftPick(draftPick);
        return asset;
    }

    private Trade changeTradeStatus(Trade trade, Trade.Status status) {
        trade = dao.getTradeById(trade.getTradeId());
        trade.setTradeStatus(status);
        return dao.saveTrade(trade);
    }
}
