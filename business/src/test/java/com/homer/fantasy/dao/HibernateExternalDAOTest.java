package com.homer.fantasy.dao;

import com.homer.espn.Transaction;
import com.homer.fantasy.dao.impl.HibernateExternalDAO;
import junit.framework.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Created by arigolub on 2/18/15.
 */
public class HibernateExternalDAOTest {

    public static HibernateExternalDAO dao = new HibernateExternalDAO();
    private static LocalDateTime theTime = LocalDateTime.now();
    private static String nodeText = "MLS added Phil Hughes, Min SP from Free Agency to Bench";

    @Test
    public void saveAndFind() {
        String playerName = "Phil Hughes";
        int teamId = 1;
        LocalDateTime time = LocalDateTime.now();
        Transaction.Type moveType = Transaction.ADD;
        Transaction transaction = new Transaction(playerName, teamId, moveType, time, nodeText);
        Transaction dbTransaction = dao.saveTransaction(transaction);
        Assert.assertNotNull(dbTransaction);

        dbTransaction = dao.saveTransaction(transaction);
        Assert.assertNotNull(dbTransaction);
        Assert.assertTrue(dbTransaction.isAlreadySeen());

        dbTransaction = dao.getTransaction(nodeText, theTime);
        Assert.assertNotNull(dbTransaction);
        Assert.assertEquals(playerName, dbTransaction.getPlayerName());
        Assert.assertEquals(theTime.minusNanos(theTime.getNano()), dbTransaction.getTime());
        Assert.assertEquals(nodeText, dbTransaction.getNodeText());
    }
}
