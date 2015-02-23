package com.homer.fantasy.dao.impl;

import com.homer.fantasy.Team;
import com.homer.fantasy.Trade;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.ITradeDAO;
import org.hibernate.Session;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/22/15.
 */
public class HibernateTradeDAO extends HomerDAO implements ITradeDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateTradeDAO.class);

    @Override
    public Trade saveTrade(Trade trade) {
        LOG.debug("BEGIN: saveTrade [trade=" + trade + "]");
        boolean success = saveOrUpdate(trade);
        if(!success) {
            trade = null;
        }
        LOG.debug("END: saveTrade [trade=" + trade + "]");
        return trade;
    }

    @Override
    public Trade getTradeById(int tradeId) {
        LOG.debug("BEGIN: getTradeById [tradeId=" + tradeId + "]");
        Trade trade = findUniqueById(tradeId, Trade.class);
        LOG.debug("END: getTradeById [trarde=" + trade + "]");
        return trade;
    }

    @Override
    public List<Trade> getTradesForTeam(Team team) {
        LOG.debug("BEGIN: getTradesForTeam [team=" + team + "]");
        List<Trade> trades = new ArrayList<Trade>();
        Session session = null;
        try {
            session = openSession();
            LogicalExpression restrictions = Restrictions.or(
                    Restrictions.like("proposedToTeam.id", team.getTeamId()),
                    Restrictions.like("proposingTeam.id", team.getTeamId())
            );
            LOG.debug("Restrictions: " + restrictions);
            trades = session.createCriteria(Trade.class).add(restrictions).list();
        } catch(Exception e) {
            LOG.error("Error finding vultures for team " + team, e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        LOG.debug("END: getTradesForTeam [trades=" + trades + "]");
        return trades;
    }
}
