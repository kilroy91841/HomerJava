package com.homer.fantasy.dao.impl;

import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.IStatsDAO;
import com.homer.mlb.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/16/15.
 */
public class HibernateStatsDAO extends HomerDAO implements IStatsDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateStatsDAO.class);

    @Override
    public Stats createOrSave(Stats stats) {
        LOG.debug("BEGIN: createOrSave [stats=" + stats + "]");
        boolean success = saveOrUpdate(stats);
        if(!success) {
            stats = null;
        }
        LOG.debug("END: createOrSave [stats=" + stats + "]");
        return stats;
    }

    @Override
    public List<Stats> getStats(Stats example) {
        LOG.debug("BEGIN: getStats [example=" + example + "]");
        List<Stats> statsList = findListByExample(example, Stats.class);
        LOG.debug("END: getStats [stats=" + statsList + "]");
        return statsList;
    }
}
