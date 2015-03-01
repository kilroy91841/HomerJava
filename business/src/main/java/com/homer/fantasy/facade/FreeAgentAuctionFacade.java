package com.homer.fantasy.facade;

import com.homer.exception.*;
import com.homer.fantasy.*;
import com.homer.fantasy.dao.IFreeAgentAuctionDAO;
import com.homer.fantasy.dao.IMoneyDAO;
import com.homer.fantasy.dao.IPlayerDAO;
import com.homer.fantasy.key.FreeAgentAuctionBidKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Created by arigolub on 2/28/15.
 */
public class FreeAgentAuctionFacade {

    private static final Logger LOG = LoggerFactory.getLogger(FreeAgentAuction.class);
    private IFreeAgentAuctionDAO dao;
    private PlayerFacade playerFacade;
    private MoneyFacade moneyFacade;

    private static final int SEASON = 2015;

    private static final int AUCTION_DAYS_LENGTH = 2;

    public FreeAgentAuctionFacade() {
        dao = IFreeAgentAuctionDAO.FACTORY.getInstance();
        playerFacade = new PlayerFacade();
        moneyFacade = new MoneyFacade();
    }

    public FreeAgentAuction requestFreeAgentAuction(long playerId, Team requestingTeam) throws ActiveAuctionAlreadyExistsException, PlayerNotFoundException {
        LOG.debug("BEGIN: requestFreeAgentAuction [playerId=" + playerId + ", requestTeam=" + requestingTeam + "]");

        FreeAgentAuction freeAgentAuction = dao.getFreeAgentAuctionByPlayerId(playerId);
        if(freeAgentAuction != null &&
                !(FreeAgentAuction.Status.CANCELLED.equals(freeAgentAuction.getStatus()) ||
                FreeAgentAuction.Status.DENIED.equals(freeAgentAuction.getStatus()))) {
            throw new ActiveAuctionAlreadyExistsException();
        }

        Player player = playerFacade.getPlayer(playerId);
        if(player == null) {
            throw new PlayerNotFoundException("Could not find player with playerId=" + playerId);
        }

        LOG.debug("Requesting free agent auction for player:" + player);

        freeAgentAuction = new FreeAgentAuction();
        freeAgentAuction.setStatus(FreeAgentAuction.Status.REQUESTED);
        freeAgentAuction.setPlayer(player);
        freeAgentAuction.setRequestingTeam(requestingTeam);

        freeAgentAuction = dao.saveFreeAgentAuction(freeAgentAuction);

        LOG.debug("END: requestFreeAgentAuction [freeAgentAuction=" + freeAgentAuction + "]");
        return freeAgentAuction;
    }

    public FreeAgentAuction approveFreeAgentAuction(int freeAgentAuctionId) throws NotFoundException {
        LOG.debug("BEGIN: approveFreeAgentAuction [freeAgentAuctionId=" + freeAgentAuctionId + "]");

        FreeAgentAuction freeAgentAuction = dao.getFreeAgentAuction(freeAgentAuctionId);
        if(freeAgentAuction == null) {
            throw new NotFoundException(FreeAgentAuction.class, freeAgentAuctionId);
        }

        freeAgentAuction.setStatus(FreeAgentAuction.Status.ACTIVE);
        freeAgentAuction.setDeadline(LocalDateTime.now().plusDays(AUCTION_DAYS_LENGTH));

        freeAgentAuction = dao.saveFreeAgentAuction(freeAgentAuction);

        LOG.debug("END: approveFreeAgentAuction [freeAgentAuction=" + freeAgentAuction + "]");
        return freeAgentAuction;
    }

    public FreeAgentAuction denyFreeAgentAuction(int freeAgentAuctionId) throws NotFoundException {
        LOG.debug("BEGIN: denyFreeAgentAuction [freeAgentAuctionId=" + freeAgentAuctionId + "]");

        FreeAgentAuction freeAgentAuction = dao.getFreeAgentAuction(freeAgentAuctionId);
        if(freeAgentAuction == null) {
            throw new NotFoundException(FreeAgentAuction.class, freeAgentAuctionId);
        }

        freeAgentAuction.setStatus(FreeAgentAuction.Status.DENIED);

        freeAgentAuction = dao.saveFreeAgentAuction(freeAgentAuction);

        LOG.debug("END: denyFreeAgentAuction [freeAgentAuction=" + freeAgentAuction + "]");
        return freeAgentAuction;
    }

    public FreeAgentAuction cancelFreeAgentAuction(int freeAgentAuctionId) throws NotFoundException {
        LOG.debug("BEGIN: cancelFreeAgentAuction [freeAgentAuctionId=" + freeAgentAuctionId + "]");

        FreeAgentAuction freeAgentAuction = dao.getFreeAgentAuction(freeAgentAuctionId);
        if(freeAgentAuction == null) {
            throw new NotFoundException(FreeAgentAuction.class, freeAgentAuctionId);
        }

        freeAgentAuction.setStatus(FreeAgentAuction.Status.CANCELLED);

        freeAgentAuction = dao.saveFreeAgentAuction(freeAgentAuction);

        LOG.debug("END: cancelFreeAgentAuction [freeAgentAuction=" + freeAgentAuction + "]");
        return freeAgentAuction;
    }

    public FreeAgentAuctionBid bidOnFreeAgentAuction(int freeAgentAuctionId, Team biddingTeam, int bidAmount) throws NotEnoughFundsException, NotFoundException {
        LOG.debug("BEGIN: cancelFreeAgentAuction [freeAgentAuctionId=" + freeAgentAuctionId + ", biddingTeam=" + biddingTeam + ", bidAmount=" + bidAmount + "]");

        FreeAgentAuction freeAgentAuction = dao.getFreeAgentAuction(freeAgentAuctionId);
        if(freeAgentAuction == null) {
            throw new NotFoundException(FreeAgentAuction.class, freeAgentAuctionId);
        }

        Money money = moneyFacade.getMoney(biddingTeam.getTeamId(), SEASON, Money.MoneyType.FREEAGENTAUCTION);

        if(money == null) {
            LOG.debug("Could not find free agent auction funds for team " + biddingTeam.getTeamId() + ", season " + SEASON);
        }
        if(bidAmount > 0 && (money == null || money.getAmount() < bidAmount)) {
            throw new NotEnoughFundsException();
        }

        LOG.debug("Verified ok bid of " + bidAmount + " for team " + biddingTeam.getTeamId());

        FreeAgentAuctionBid freeAgentAuctionBid = new FreeAgentAuctionBid();
        FreeAgentAuctionBidKey key = new FreeAgentAuctionBidKey();
        key.setTeam(biddingTeam);
        key.setFreeAgentAuction(freeAgentAuction);
        freeAgentAuctionBid.setFreeAgentAuctionBidKey(key);
        freeAgentAuctionBid.setAmount(bidAmount);
        freeAgentAuction.getFreeAgentAuctionBids().remove(freeAgentAuctionBid);
        freeAgentAuction.getFreeAgentAuctionBids().add(freeAgentAuctionBid);

        LOG.debug("Creating free agent auction bid: " + freeAgentAuctionBid);

        freeAgentAuction = dao.saveFreeAgentAuction(freeAgentAuction);
        if(freeAgentAuction == null) {
            freeAgentAuctionBid = null;
        }

        LOG.debug("END: bidOnFreeAgentAuction [freeAgentAuctionBid=" + freeAgentAuctionBid + "]");
        return freeAgentAuctionBid;
    }

    /**
     *
     * @param freeAgentAuctionId
     * @return the {@link com.homer.fantasy.Team} that won the auction. If no team won, return null. If there was a tie, throw an exception
     * so that it can be handled offline.
     */
    public Team endFreeAgentAuction(int freeAgentAuctionId) throws BiddingTiedException, NotFoundException, NotEnoughFundsException {
        LOG.debug("BEGIN: endFreeAgentAuction [freeAgentAuctionId=" + freeAgentAuctionId + "]");

        FreeAgentAuction freeAgentAuction = dao.getFreeAgentAuction(freeAgentAuctionId);
        if(freeAgentAuction == null) {
            throw new NotFoundException(FreeAgentAuction.class, freeAgentAuctionId);
        }

        FreeAgentAuctionBid maxBid = new FreeAgentAuctionBid();
        maxBid.setAmount(-1);
        int maxBidCount = 0;
        for(FreeAgentAuctionBid bid : freeAgentAuction.getFreeAgentAuctionBids()) {
            if(bid.getAmount() > maxBid.getAmount()) {
                LOG.debug("New max bid: " + maxBid);
                maxBid = bid;
                maxBidCount = 1;
            } else if (bid.getAmount() == maxBid.getAmount()) {
                maxBidCount++;
                LOG.debug("Found another bid with same amount as max bid, current maxBidCount=" + maxBidCount);
            }
        }

        if(maxBidCount > 1) {
            throw new BiddingTiedException();
        }

        Team winningTeam = maxBid != null ? maxBid.getTeam() : null;

        if(winningTeam != null) {
            LOG.debug("Setting status to complete");
            freeAgentAuction.setStatus(FreeAgentAuction.Status.COMPLETE);
            dao.saveFreeAgentAuction(freeAgentAuction);

            LOG.debug("Taking away funds from winning team");
            if (maxBid.getAmount() > 0) {
                moneyFacade.deductMoney(maxBid.getAmount(), winningTeam.getTeamId(), SEASON, Money.MoneyType.FREEAGENTAUCTION);
            }

            LOG.debug("Moving player to winning team");
            playerFacade.transferPlayer(freeAgentAuction.getPlayer(), null, maxBid.getTeam());
        }

        LOG.debug("END: endFreeAgentAuction [maxBid=" + maxBid + "]");
        return winningTeam;
    }

}
