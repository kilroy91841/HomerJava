package com.homer.fantasy.dao;

import com.homer.espn.Transaction;
import com.homer.fantasy.dao.impl.HibernateExternalDAO;
import junit.framework.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by arigolub on 2/18/15.
 */
public class HibernateExternalDAOTest {

    public static HibernateExternalDAO dao = new HibernateExternalDAO();
    private static LocalDateTime theTime = LocalDateTime.now();
    private static final String nodeText = "MLS added Phil Hughes, Min SP from Free Agency to Bench";
    private static final String PLAYER_NAME = "Phil Hughes";

    @Test
    public void saveAndFind() {
        int teamId = 1;
        Transaction.Type moveType = Transaction.ADD;
        Transaction transaction = new Transaction(PLAYER_NAME, teamId, moveType, theTime, nodeText);
        Transaction dbTransaction = dao.saveTransaction(transaction);
        Assert.assertNotNull(dbTransaction);

        dbTransaction = dao.saveTransaction(transaction);
        Assert.assertNotNull(dbTransaction);
        Assert.assertTrue(dbTransaction.isAlreadySeen());

        dbTransaction = dao.getTransaction(nodeText, theTime);
        Assert.assertNotNull(dbTransaction);
        Assert.assertEquals(PLAYER_NAME, dbTransaction.getPlayerName());
        Assert.assertEquals(theTime.minusNanos(theTime.getNano()), dbTransaction.getTime());
        Assert.assertEquals(nodeText, dbTransaction.getNodeText());
    }

    @Test
    public void getPlayerTransactions() {
        List<Transaction> transactions = dao.getPlayerTransactions(PLAYER_NAME);
        Assert.assertNotNull(transactions);

        transactions = dao.getPlayerTransactions("ARI");
        Assert.assertEquals(0, transactions.size());
    }
}
