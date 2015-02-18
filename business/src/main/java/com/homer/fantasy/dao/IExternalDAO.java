package com.homer.fantasy.dao;

import com.homer.espn.Transaction;
import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Created by arigolub on 2/18/15.
 */
public interface IExternalDAO {
    static final Logger LOG = LoggerFactory.getLogger(IExternalDAO.class);

    public Transaction getTransaction(String nodeText, LocalDateTime time);

    public Transaction saveTransaction(Transaction transaction);

    public static class FACTORY {

        private static IExternalDAO instance = null;

        public static IExternalDAO getInstance() {
            if(instance == null) {
                synchronized (IExternalDAO.class) {
                    if(instance == null) {
                        try {
                            instance = Factory.getImplementation(IExternalDAO.class);
                        } catch(Exception e) {
                            LOG.error("Exception getting instance of IExternalDAO", e);
                        }
                    }
                }
            }
            return instance;
        }
    }

}
