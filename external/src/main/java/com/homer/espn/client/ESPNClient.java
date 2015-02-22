package com.homer.espn.client;

import com.homer.espn.Player;
import com.homer.espn.Transaction;
import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/16/15.
 */
public interface ESPNClient {

    static final Logger LOG = LoggerFactory.getLogger(ESPNClient.class);

    public List<Player> getRosterPage();

    public List<Transaction> getTransactions(int teamId, Transaction.Type tranType, String startDate, String endDate);

    public static class FACTORY {

        private static ESPNClient instance = null;

        public static ESPNClient getInstance() {
            if(instance == null) {
                synchronized (ESPNClient.class) {
                    if(instance == null) {
                        try {
                            instance = Factory.getImplementation(ESPNClient.class);
                        } catch(Exception e) {
                            LOG.error("Exception getting instance of ESPNClient", e);
                        }
                    }
                }
            }
            return instance;
        }
    }

}
