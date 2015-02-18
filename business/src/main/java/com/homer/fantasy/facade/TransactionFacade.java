package com.homer.fantasy.facade;

import com.homer.espn.Transaction;
import com.homer.fantasy.dao.IExternalDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arigolub on 2/18/15.
 */
public class TransactionFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionFacade.class);
    private static IExternalDAO dao;

    public TransactionFacade() { dao = IExternalDAO.FACTORY.getInstance(); }

    public boolean transactionAlreadySeen(Transaction transaction) {
        LOG.debug("BEGIN: transactionAlreadySeen");
        boolean alreadySeen = false;
        Transaction example =  new Transaction(transaction.getNodeText(), null);
        return alreadySeen;
    }
}
