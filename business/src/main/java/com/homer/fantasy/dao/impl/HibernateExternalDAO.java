package com.homer.fantasy.dao.impl;

import com.homer.espn.Transaction;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.IExternalDAO;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by arigolub on 2/18/15.
 */
public class HibernateExternalDAO extends HomerDAO implements IExternalDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateExternalDAO.class);
    @Override
    public Transaction getTransaction(String nodeText, LocalDateTime time) {
        LOG.debug("BEGIN: getTransaction [nodeText=" + nodeText + "]");
        Session session = null;
        Transaction dbTransaction = null;
        try {
            session = openSession();
            dbTransaction = (Transaction) session.createCriteria(Transaction.class)
                    .add(Restrictions.like("nodeText", nodeText))
                    .add(Restrictions.like("time", time))
                    .uniqueResult();
        } catch(Exception e) {
            LOG.error("Excepting finding transaction", e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        LOG.debug("END: getTransaction [transaction=" + dbTransaction + "]");
        return dbTransaction;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        LOG.debug("BEGIN: saveTransaction [transaction=" + transaction + "]");
        boolean success = false;
        try {
            success = saveNoUpdate(transaction);
            if(!success) {
                transaction = null;
            }
        } catch (ConstraintViolationException e) {
            LOG.debug("A previous transaction matching the given transaction was already saved, skipping");
            if(transaction != null) {
                transaction.setAlreadySeen(true);
            }
        }

        LOG.debug("END: saveTransaction [transaction=" + transaction + "]");
        return transaction;
    }

    @Override
    public List<Transaction> getPlayerTransactions(String playerName) {
        LOG.debug("BEGIN: getPlayerTransactions [playerName=" + playerName + "]");
        Transaction example = new Transaction();
        example.setPlayerName(playerName);
        List<Transaction> transactions = findListByExample(example, Transaction.class);
        transactions.sort((t1, t2) -> t2.getTime().compareTo(t1.getTime()));
        LOG.debug("END: getPlayerTransactions [transactions=" + transactions + "]");
        return transactions;
    }
}
