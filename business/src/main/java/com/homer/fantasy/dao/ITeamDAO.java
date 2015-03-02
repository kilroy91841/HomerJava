package com.homer.fantasy.dao;

import com.homer.SportType;
import com.homer.fantasy.Team;
import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 3/1/15.
 */
public interface ITeamDAO {

    static final Logger LOG = LoggerFactory.getLogger(ITeamDAO.class);

    public List<Team> getTeams(SportType sportType);

    public Team getTeam(int teamId);

    public static class FACTORY {

        private static ITeamDAO instance = null;

        public static ITeamDAO getInstance() {
            if(instance == null) {
                synchronized (ITeamDAO.class) {
                    if(instance == null) {
                        try {
                            instance = Factory.getImplementation(ITeamDAO.class);
                        } catch(Exception e) {
                            LOG.error("Exception getting instance of ITeamDAO", e);
                        }
                    }
                }
            }
            return instance;
        }
    }
}
