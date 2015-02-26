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
    private static MinorLeagueDraftPickFacade minorLeagueDraftPickFacade;

    public TradeFacade() {
        dao = ITradeDAO.FACTORY.getInstance();
        playerFacade = new PlayerFacade();
        moneyFacade = new MoneyFacade();
        minorLeagueDraftPickFacade = new MinorLeagueDraftPickFacade();
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

        for(MinorLeagueDraftPick minorLeagueDraftPick : proposingTeamDraftPicks) {
            MinorLeagueDraftPick tempMinorLeagueDraftPick = minorLeagueDraftPickFacade.transferMinorLeagueDraftPick(minorLeagueDraftPick, proposedToTeam);
            if(tempMinorLeagueDraftPick == null) {
                allSuccessful = false;
            }
        }
        for(MinorLeagueDraftPick minorLeagueDraftPick : proposedToTeamDraftPicks) {
            MinorLeagueDraftPick tempMinorLeagueDraftPick = minorLeagueDraftPickFacade.transferMinorLeagueDraftPick(minorLeagueDraftPick, proposingTeam);
            if(tempMinorLeagueDraftPick == null) {
                allSuccessful = false;
            }
        }

        for(Money money : proposingTeamMoney) {
            try {
                Money tempMoney = moneyFacade.transferMoney(money, proposedToTeam);
                if(tempMoney == null) {
                    allSuccessful = false;
                }
            } catch (DisallowedTransactionException e) {
                LOG.error("Disallowed transaction exception", e);
                allSuccessful = false;
            }
        }
        for(Money money : proposedToTeamMoney) {
            try {
                Money tempMoney = moneyFacade.transferMoney(money, proposingTeam);
                if(tempMoney == null) {
                    allSuccessful = false;
                }
            } catch (DisallowedTransactionException e) {
                LOG.error("Disallowed transaction exception", e);
                allSuccessful = false;
            }
        }

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

        playerAssets.addAll(verifyPlayerHelper(proposingTeam, proposingTeamPlayers));
        //TODO implement roster verification: wouldn't go over 10 minor leaguers
        playerAssets.addAll(verifyPlayerHelper(proposedToTeam, proposedToTeamPlayers));
        //TODO implement roster verification: wouldn't go over 10 minor leaguers

        LOG.debug("Verification was successful, trade can continue");
        LOG.debug("END: verifyPlayers");
        return playerAssets;
    }

    private Set<TradeAsset> verifyPlayerHelper(Team team, List<Player> players) throws DisallowedTradeException {
        Set<TradeAsset> playerAssets = new HashSet<TradeAsset>();
        LOG.debug("Verify players on team: " + team);
        for (Player p : players) {
            LOG.debug("Verifying player id: " + p.getPlayerId());
            p = playerFacade.getPlayer(p.getPlayerId());
            try {
                if (p.getCurrentFantasyTeam().getTeamId() != team.getTeamId()) {
                    LOG.debug("Player does not have daily player info");
                    throw new DisallowedTradeException("Player " + p.getPlayerId() + " is not owned by team " + team.getTeamId());
                }
            } catch (NoDailyPlayerInfoException e) {
                LOG.error("No daily info found for player", e);
                throw new DisallowedTradeException("A player in the trade was in an invalid state");
            }
            playerAssets.add(createTradeAsset(team, p, null, null));
        }
        return playerAssets;
    }

    private Set<TradeAsset> verifyMoney(Team proposingTeam, Team proposedToTeam,
                            List<Money> proposingTeamMoney, List<Money> proposedToTeamMoney) throws DisallowedTradeException {
        LOG.debug("BEGIN: verifyMoney");
        Set<TradeAsset> moneyAssets = new HashSet<TradeAsset>();
        moneyAssets.addAll(verifyMoneyHelper(proposingTeam, proposingTeamMoney));
        moneyAssets.addAll(verifyMoneyHelper(proposedToTeam, proposedToTeamMoney));
        LOG.debug("END: verifyMoney");
        return moneyAssets;
    }

    private Set<TradeAsset> verifyMoneyHelper(Team team, List<Money> moneyList) throws DisallowedTradeException {
        Set<TradeAsset> moneyAssets = new HashSet<TradeAsset>();
        LOG.debug("Verify money for team " + team);
        List<Money> teamDBMoneys = moneyFacade.getMoneyForTeam(team);
        for(Money m : moneyList) {
            LOG.debug("Verifying money: " + m);
            for(Money dbMoney : teamDBMoneys) {
                if(dbMoney.equals(m)) {
                    int proposedAmount = m.getAmount();
                    int hasAmount = dbMoney.getAmount();
                    int possibleAmount = hasAmount - proposedAmount;
                    if(Money.MoneyType.MAJORLEAGUEDRAFT.equals(dbMoney.getMoneyType()) && possibleAmount <
                            MoneyFacade.MAJOR_LEAGUE_DRAFT_MINIMUM) {
                        throw new DisallowedTradeException("Proposed money transfer would put team below MLB limit");
                    } else if (proposedAmount < 0) {
                        throw new DisallowedTradeException("Proposd money transfer would give team negative dollars");
                    }
                    moneyAssets.add(createTradeAsset(team, null, m, null));
                }
            }
        }
        return moneyAssets;
    }

    private Set<TradeAsset> verifyDraftPicks(Team proposingTeam, Team proposedToTeam,
                             List<MinorLeagueDraftPick> proposingTeamDraftPicks, List<MinorLeagueDraftPick> proposedToTeamDraftPicks)
            throws DisallowedTradeException {
        LOG.debug("BEGIN: verifyDraftPicks");
        Set<TradeAsset> pickAssets = new HashSet<TradeAsset>();
        pickAssets.addAll(verifyDraftPicksHelper(proposingTeam, proposingTeamDraftPicks));
        pickAssets.addAll(verifyDraftPicksHelper(proposedToTeam, proposedToTeamDraftPicks));
        LOG.debug("END: verifyDraftPicks");
        return pickAssets;
    }

    private Set<TradeAsset> verifyDraftPicksHelper(Team team, List<MinorLeagueDraftPick> minorLeagueDraftPicks) throws DisallowedTradeException {
        Set<TradeAsset> pickAssets = new HashSet<TradeAsset>();
        LOG.debug("Verify draft picks for team " + team);
        List<MinorLeagueDraftPick> dbDraftPicks = minorLeagueDraftPickFacade.getMinorLeagueDraftPicksForTeam(team);
        for(MinorLeagueDraftPick minorLeagueDraftPick : minorLeagueDraftPicks) {
            LOG.debug("Verifying draft pick: " + minorLeagueDraftPick);
            for(MinorLeagueDraftPick dbPick : dbDraftPicks) {
                if(dbPick.equals(minorLeagueDraftPick)) {
                    pickAssets.add(createTradeAsset(team, null, null, minorLeagueDraftPick));
                }
            }
        }
        if(minorLeagueDraftPicks.size() != pickAssets.size()) {
            throw new DisallowedTradeException("One or more of the picks involved in this trade was missing or not owned by the team in question");
        }
        return pickAssets;
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
