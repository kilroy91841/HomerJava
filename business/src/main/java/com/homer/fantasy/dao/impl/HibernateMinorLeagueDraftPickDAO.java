package com.homer.fantasy.dao.impl;

import com.homer.fantasy.MinorLeagueDraftPick;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.IMinorLeagueDraftPickDAO;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/24/15.
 */
public class HibernateMinorLeagueDraftPickDAO extends HomerDAO implements IMinorLeagueDraftPickDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateMinorLeagueDraftPickDAO.class);

    @Override
    public MinorLeagueDraftPick savePick(MinorLeagueDraftPick minorLeagueDraftPick) {
        LOG.debug("BEGIN: savePick [minorLeagueDraftPick=" + minorLeagueDraftPick + "]");

        MinorLeagueDraftPick existingPick = getDraftPick(minorLeagueDraftPick.getOriginalTeam(), minorLeagueDraftPick.getSeason(), minorLeagueDraftPick.getRound());
        if(existingPick != null) {
            minorLeagueDraftPick.setMinorLeagueDraftPickId(existingPick.getMinorLeagueDraftPickId());
        }
        boolean success = saveOrUpdate(minorLeagueDraftPick);
        if(!success) {
            minorLeagueDraftPick = null;
        }

        LOG.debug("END: savePick [minorLeagueDraftPick=" + minorLeagueDraftPick + "]");
        return minorLeagueDraftPick;
    }

    @Override
    public MinorLeagueDraftPick getDraftPick(Team originalTeam, int season, int round) {
        LOG.debug("BEGIN: getDraftPick [originalTeam=" + originalTeam + ", season=" + season + ", round=" + round + "]");
        MinorLeagueDraftPick example = new MinorLeagueDraftPick();
        example.setOriginalTeam(originalTeam);
        example.setSeason(season);
        example.setRound(round);
        MinorLeagueDraftPick minorLeagueDraftPick = findUniqueByExample(example, MinorLeagueDraftPick.class);
        LOG.debug("END: getDraftPick [minorLeagueDraftPick=" + minorLeagueDraftPick + "]");
        return minorLeagueDraftPick;
    }

    @Override
    public List<MinorLeagueDraftPick> getDraftPicksForTeam(Team owningTeam) {
        LOG.debug("BEGIN: getDraftPicksForTeam [team=" + owningTeam + "]");

        List<MinorLeagueDraftPick> minorLeagueDraftPickList = null;

        Session session = null;
        try {
            session = openSession();
            session.beginTransaction();

            minorLeagueDraftPickList = session.createCriteria(MinorLeagueDraftPick.class)
                    .add(Restrictions.like("owningTeam.teamId", owningTeam.getTeamId()))
                    .list();
        } catch (RuntimeException re) {
            LOG.error("Error getting object", re);
        } finally {
            if(session != null) {
                session.close();
            }
        }

        LOG.debug("END: getDraftPicksForTeam [minorLeagueDraftPicks=" + minorLeagueDraftPickList + "]");
        return minorLeagueDraftPickList;
    }
}
