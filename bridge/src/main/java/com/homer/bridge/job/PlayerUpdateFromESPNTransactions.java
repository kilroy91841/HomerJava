package com.homer.bridge.job;

import com.homer.SportType;
import com.homer.espn.Transaction;
import com.homer.espn.client.ESPNClientREST;
import com.homer.exception.DisallowedTransactionException;
import com.homer.exception.NoDailyPlayerInfoException;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.ITeamDAO;
import com.homer.fantasy.facade.TransactionFacade;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/16/15.
 */
public class PlayerUpdateFromESPNTransactions implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerUpdateFromESPNTransactions.class);

    private static final ITeamDAO teamDao = ITeamDAO.FACTORY.getInstance();
    private static final ESPNClientREST client = new ESPNClientREST();
    private static final TransactionFacade transactionFacade = new TransactionFacade();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static LocalDate theDate;

    public PlayerUpdateFromESPNTransactions withDate(LocalDate date) {
        this.theDate = date;
        return this;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.debug("BEGIN: execute");

        if(theDate == null) {
            LocalDateTime time = LocalDateTime.now().minusHours(5);
            theDate = time.toLocalDate();
        }
        String dateString = theDate.format(dateFormatter);
        LOG.debug("DateString: " + dateString);

        List<Team> teams = teamDao.getTeams(SportType.FANTASY);
        List<Transaction> masterTransactionList = new ArrayList<Transaction>();
        for(Team t: teams) {
            if(t.getTeamId() == Team.FANTASY_FREE_AGENT_TEAM) {
                continue;
            }
            LOG.debug("Getting transactions for teamId " + t.getTeamId());
            List<Transaction> adds = client.getTransactions(t.getTeamId(), Transaction.ADD, dateString, dateString);
            if(adds != null) {
                LOG.debug("Total adds: " + adds.size());
                masterTransactionList.addAll(adds);
            }
            List<Transaction> drops = client.getTransactions(t.getTeamId(), Transaction.DROP, dateString, dateString);
            if(drops != null) {
                LOG.debug("Total drops: " + drops.size());
                masterTransactionList.addAll(drops);
            }
            List<Transaction> trades = client.getTransactions(t.getTeamId(), Transaction.TRADE, dateString, dateString);
            if(trades != null) {
                LOG.debug("Total trades: " + trades.size());
                masterTransactionList.addAll(trades);
            }
        }
        masterTransactionList.sort((t1, t2) -> t1.getTime().compareTo(t2.getTime()));

        LOG.debug("Consuming " + masterTransactionList.size() + " transactions....");
        for(Transaction t: masterTransactionList) {
            try {
                transactionFacade.consumeTransaction(t);
            } catch (NoDailyPlayerInfoException e) {
                LOG.error(e.getMessage(), e);
            } catch (DisallowedTransactionException e) {
                LOG.error(e.getMessage(), e);
            }
        }

        LOG.debug("END: execute");
    }
}
