package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
import com.homer.espn.Transaction;
import com.homer.exception.DisallowedTransactionException;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.fantasy.Player;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.IExternalDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Created by arigolub on 2/18/15.
 */
public class TransactionFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionFacade.class);
    private static IExternalDAO dao;
    private PlayerFacade playerFacade;
    private static final Team FREE_AGENT_TEAM = new Team(Team.FANTASY_FREE_AGENT_TEAM);

    public TransactionFacade() {
        dao = IExternalDAO.FACTORY.getInstance();
        playerFacade = new PlayerFacade();
    }

    public boolean consumeTransaction(Transaction transaction) throws NoDailyPlayerInfoException, DisallowedTransactionException {
        LOG.debug("BEGIN: consumeTransaction [transaction=" + transaction + "]");
        boolean success = false;

        //Confirm transaction has all necessary attributes
        if(transaction == null || transaction.getPlayerName() == null || transaction.getTeamId() == null ||
                transaction.getNodeText() == null || transaction.getTime() == null) {
            throw new DisallowedTransactionException("No transaction and/or player name and/or team provided, cannot continue-- transaction:" + transaction);
        }

        //Confirm this transaction hasn't already been processed
        Transaction existingTransaction = dao.getTransaction(transaction.getNodeText(), transaction.getTime());
        if(existingTransaction != null) {
            LOG.debug("Already consumed this transaction, skipping");
            return true;
        }

        //Confirm a player with the specified name exists
        //TODO find a better solution than searching by name, since name isn't inherently unique
        Player player = playerFacade.getPlayerByName(transaction.getPlayerName());
        if(player == null) {
            throw new DisallowedTransactionException("No player with name " + transaction.getPlayerName() + " found, cannot continue-- transaction:" + transaction);
        }

        //Do the transaction
        Team actingTeam = new Team(transaction.getTeamId());
        if(Transaction.ADD.equals(transaction.getMove())) {
            success = consumeAdd(player, actingTeam, transaction);
        } else if(Transaction.DROP.equals(transaction.getMove())) {
            success = consumeDrop(player, actingTeam, transaction);
        } else if(Transaction.TRADE.equals(transaction.getMove())) {
            success = consumeTrade(player, actingTeam, transaction);
        } else {
            LOG.error("Unknown/non-existent transaction type: " + transaction.getMove());
            success = false;
        }

        //Save the transaction
        if(success) {
            LOG.debug("Transaction was successful, saving transaction");
            dao.saveTransaction(transaction);
        }

        LOG.debug("END: consumeTransaction [success=" + success + "]");
        return success;
    }

    private boolean consumeAdd(Player player, Team actingTeam, Transaction transaction) throws NoDailyPlayerInfoException, DisallowedTransactionException {
        boolean success = false;
        LOG.debug("Adding player to new team");

        //Confirm current player is a free agent OR is a minor leaguer of that team OR on the suspended list of that team
        Team currentTeam = player.getCurrentFantasyTeam();
        if(currentTeam != null && (Team.FANTASY_FREE_AGENT_TEAM != currentTeam.getTeamId() &&
            actingTeam.getTeamId() != currentTeam.getTeamId())) {
            throw new DisallowedTransactionException("Attempted to add a player who was not a free agent-- transaction: " + transaction);
        }

        //Determine if we need to reset contract year. If the adding team was the last team that dropped the player, and the drop occurred within 24 hours,
        //do not reset the contract year. Otherwise, DO reset the contract year.
        List<Transaction> transactions = dao.getPlayerTransactions(player.getPlayerName());
        if(transactions != null && transactions.size() > 0) {
            Transaction latestTransaction = transactions.get(0);
            if(latestTransaction.getTeamId() == actingTeam.getTeamId() && Transaction.DROP.equals(latestTransaction.getMove())) {
                long hours = Duration.between(latestTransaction.getTime(), transaction.getTime()).toHours();
                if(hours >= 24) {
                    player.getPlayerHistoryList().get(0).setKeeperSeason(0);
                }
            }
        }

        if(PlayerStatus.ACTIVE.equals(player.getMostRecentFantasyStatus())) {
            throw new DisallowedTransactionException("Attempted to add a player who was already active-- transaction: " + transaction);
        }

        //Change player status to ACTIVE
        player.getDailyPlayerInfoList().get(0).setFantasyStatus(PlayerStatus.ACTIVE);

        //Change player's rookie status if necessary
        if(player.getPlayerHistoryList().get(0).hasRookieStatus()) {
            player.getPlayerHistoryList().get(0).setRookieStatus(false);
        }

        //Transfer player if the player was a free agent. Otherwise, just update.
        if(Team.FANTASY_FREE_AGENT_TEAM == currentTeam.getTeamId()) {
            player = playerFacade.transferPlayer(player, FREE_AGENT_TEAM, actingTeam);
        } else {
            player = playerFacade.createOrUpdatePlayer(player);
        }

        if(player != null) {
            success = true;
        }
        LOG.debug("Done adding player");
        return success;
    }

    private boolean consumeDrop(Player player, Team actingTeam, Transaction transaction) throws NoDailyPlayerInfoException, DisallowedTransactionException {
        LOG.debug("Dropping player from old team");
        boolean success = false;

        //Confirm player was on a team
        Team oldTeam = player.getCurrentFantasyTeam();
        if(oldTeam == null || oldTeam.getTeamId() == null) {
            throw new DisallowedTransactionException("Attempted to drop a player that was not on a team-- transaction: " + transaction);
        }

        //Confirm player is not already a free agent
        if(FREE_AGENT_TEAM.getTeamId() == oldTeam.getTeamId()) {
            throw new DisallowedTransactionException("Attempted to drop a player who was already a free agent-- transaction: " + transaction);
        }

        //Confirm player is on team that is doing the drop
        if(actingTeam.getTeamId() != oldTeam.getTeamId()) {
            throw new DisallowedTransactionException("A team that did not own a player attempted to drop that player-- transaction: " + transaction);
        }

        PlayerStatus status = player.getMostRecentFantasyStatus();
        if (PlayerStatus.SUSPENDED.equals(status) || PlayerStatus.MINORS.equals(status)) {
            //Player was marked as being moved to a separate list, so this drop is not a drop from the team.
            //No need to do anything, status was already handled.
            LOG.debug("Not dropping the player, they are on a different list already");
        } else {
            //Player was not demoted or suspended, so this is a real drop
            //Transfer player, remove fantasy position
            LOG.debug("Dropping player");
            player.getDailyPlayerInfoList().get(0).setFantasyStatus(PlayerStatus.FREEAGENT);
            player.getDailyPlayerInfoList().get(0).setFantasyPosition(null);
            player = playerFacade.transferPlayer(player, oldTeam, FREE_AGENT_TEAM);
        }

        if(player != null) {
            success = true;
        }

        LOG.debug("Done dropping player");
        return success;
    }

    private boolean consumeTrade(Player player, Team actingTeam, Transaction transaction) throws NoDailyPlayerInfoException, DisallowedTransactionException {
        LOG.debug("Trading player");
        boolean success = false;

        //Confirm player was on a team
        Team oldTeam = player.getCurrentFantasyTeam();
        if(oldTeam == null || oldTeam.getTeamId() == null) {
            throw new DisallowedTransactionException("Attempted to trade a player that was not on a team-- transaction: " + transaction);
        }

        //Confirm player is not already on new team
        if(actingTeam.getTeamId() == oldTeam.getTeamId()) {
            throw new DisallowedTransactionException("Attempted to trade a player that was already on that team-- transaction: " + transaction);
        }

        //Transfer player
        player = playerFacade.transferPlayer(player, oldTeam, actingTeam);
        if(player != null) {
            success = true;
        }
        LOG.debug("Done trading player");
        return success;
    }
}
