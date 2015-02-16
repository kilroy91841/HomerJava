package com.homer.fantasy.dao;

import com.homer.mlb.Stats;
import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/16/15.
 */
public interface IStatsDAO {

    static final Logger LOG = LoggerFactory.getLogger(IStatsDAO.class);

    public Stats createOrSave(Stats stats);

    public List<Stats> getStats(Stats example);

    public static class FACTORY {

        public static IStatsDAO getInstance() {
            IStatsDAO dao = null;
            try {
                dao = Factory.getImplementation(IStatsDAO.class);
            } catch(Exception e) {
                LOG.error("Exception getting instance of IStatsDAO", e);
            }
            return dao;
        }
    }
}
