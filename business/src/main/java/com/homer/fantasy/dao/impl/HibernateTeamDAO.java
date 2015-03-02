package com.homer.fantasy.dao.impl;

import com.homer.SportType;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.ITeamDAO;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 3/1/15.
 */
public class HibernateTeamDAO extends HomerDAO implements ITeamDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateTeamDAO.class);

    @Override
    public List<Team> getTeams(SportType sportType) {
        LOG.debug("BEGIN: getTeams [sportType=" + sportType + "]");
        Session session = null;
        List<Team> teams = null;
        try {
            session = openSession();
            teams = session.createCriteria(Team.class)
                    .add(Restrictions.like("teamType", sportType))
                    .add(Restrictions.ne("teamId", 0))
                    .list();
        } catch(Exception e) {
            LOG.error("Exception finding teams", e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        LOG.debug("END: getTeams [teams=" + teams + "]");
        return teams;
    }

    @Override
    public Team getTeam(int teamId) {
        LOG.debug("BEGIN: getTeam [teamId=" + teamId + "]");

        Team team = findUniqueById(teamId, Team.class);

        LOG.debug("END: getTeam [teamId=" + teamId + "]");
        return team;
    }
}
