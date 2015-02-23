package com.homer.fantasy.dao;

import com.homer.fantasy.Team;
import com.homer.fantasy.Trade;
import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/22/15.
 */
public interface ITradeDAO {

    static final Logger LOG = LoggerFactory.getLogger(ITradeDAO.class);

    public Trade saveTrade(Trade trade);

    public Trade getTradeById(int tradeId);

    public List<Trade> getTradesForTeam(Team team);

    public static class FACTORY {

        private static ITradeDAO instance = null;

        public static ITradeDAO getInstance() {
            if(instance == null) {
                synchronized (ITradeDAO.class) {
                    if(instance == null) {
                        try {
                            instance = Factory.getImplementation(ITradeDAO.class);
                        } catch(Exception e) {
                            LOG.error("Exception getting instance of ITradeDAO", e);
                        }
                    }
                }
            }
            return instance;
        }
    }
}
