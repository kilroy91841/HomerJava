package com.homer.fantasy.dao.impl;

import com.homer.fantasy.FreeAgentAuction;
import com.homer.fantasy.Player;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.IFreeAgentAuctionDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/28/15.
 */
public class HibernateFreeAgentAuctionDAO extends HomerDAO implements IFreeAgentAuctionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateFreeAgentAuctionDAO.class);

    @Override
    public FreeAgentAuction saveFreeAgentAuction(FreeAgentAuction freeAgentAuction) {
        LOG.debug("BEGIN: saveFreeAgentAuction [freeAgentAuction=" + freeAgentAuction +"]");
        boolean success = saveOrUpdate(freeAgentAuction);
        if(!success) {
            freeAgentAuction = null;
        }
        LOG.debug("END: saveFreeAgentAuction [freeAgentAuction=" + freeAgentAuction + "]");
        return freeAgentAuction;
    }

    @Override
    public FreeAgentAuction getFreeAgentAuction(int freeAgentAuctionId) {
        LOG.debug("BEGIN: getFreeAgentAuction [freeAgentAuctionId=" + freeAgentAuctionId + "]");

        FreeAgentAuction freeAgentAuction = findUniqueById(freeAgentAuctionId, FreeAgentAuction.class);

        LOG.debug("END: getFreeAgentAuction [freeAgentAuction=" + freeAgentAuction + "]");
        return freeAgentAuction;
    }

    @Override
    public FreeAgentAuction getFreeAgentAuctionByPlayerId(long playerId) {
        LOG.debug("BEGIN: getFreeAgentAuction [playerId=" + playerId + "]");

        FreeAgentAuction freeAgentAuction = null;

        Session session = null;
        try {
            session = openSession();
            freeAgentAuction = (FreeAgentAuction) session.createCriteria(FreeAgentAuction.class)
                    .add(Restrictions.like("player.id", playerId))
                    .add(Restrictions.or(
                            Restrictions.like("status", FreeAgentAuction.Status.ACTIVE),
                            Restrictions.like("status", FreeAgentAuction.Status.REQUESTED)))
                    .uniqueResult();
        } catch(Exception e) {
            LOG.error("Error finding freeAgentAuction for playerId " + playerId, e);
        } finally {
            if(session != null) {
                session.close();
            }
        }

        LOG.debug("END: getFreeAgentAuction [freeAgentAuction=" + freeAgentAuction + "]");
        return freeAgentAuction;
    }

    @Override
    public List<FreeAgentAuction> getFreeAgentAuctionsByStatus(FreeAgentAuction.Status status) {
        LOG.debug("BEGIN: getFreeAgentAuctionsByStatus [status=" + status + "]");

        List<FreeAgentAuction> list = new ArrayList<FreeAgentAuction>();

        Session session = null;
        try {
            session = openSession();
            list = session.createCriteria(FreeAgentAuction.class).add(Restrictions.like("status", status)).list();
        } catch(Exception e) {
            LOG.error("Error finding freeAgentAuctions for status " + status, e);
        } finally {
            if(session != null) {
                session.close();
            }
        }

        LOG.debug("END: getFreeAgentAuctionsByStatus [auctionList=" + list + "]");
        return list;
    }
}
