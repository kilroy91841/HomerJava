package com.homer.fantasy.dao.impl;

import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.IStatsDAO;
import com.homer.mlb.Stats;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
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
        Session session = null;
        List<Stats> stats = null;
        try {
            session = openSession();
            stats = session.createCriteria(Stats.class)
                    .add(Restrictions.like("game.gameId", example.getGame().getGameId()))
                    .add(Restrictions.like("dailyPlayerInfo.dailyPlayerInfoId", example.getDailyPlayerInfo().getDailyPlayerInfoId()))
                    .createCriteria("dailyPlayerInfo.player").add(Restrictions.like("mlbPlayerId", example.getDailyPlayerInfo().getPlayer().getMlbPlayerId()))
                    .list();
        } catch(Exception e) {
            LOG.error("Exception finding teams", e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        LOG.debug("END: getStats [stats=" + stats + "]");
        return stats;
    }
}
