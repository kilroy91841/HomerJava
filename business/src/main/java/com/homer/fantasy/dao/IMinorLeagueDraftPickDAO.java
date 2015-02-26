package com.homer.fantasy.dao;

import com.homer.fantasy.MinorLeagueDraftPick;
import com.homer.fantasy.Team;
import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/24/15.
 */
public interface IMinorLeagueDraftPickDAO {

    static final Logger LOG = LoggerFactory.getLogger(IMinorLeagueDraftPickDAO.class);

    public MinorLeagueDraftPick savePick(MinorLeagueDraftPick minorLeagueDraftPick);

    public MinorLeagueDraftPick getDraftPick(Team originalTeam, int season, int round);

    public List<MinorLeagueDraftPick> getDraftPicksForTeam(Team owningTeam);

    public static class FACTORY {

        private static IMinorLeagueDraftPickDAO instance = null;

        public static IMinorLeagueDraftPickDAO getInstance() {
            if(instance == null) {
                synchronized (IMinorLeagueDraftPickDAO.class) {
                    if(instance == null) {
                        try {
                            instance = Factory.getImplementation(IMinorLeagueDraftPickDAO.class);
                        } catch(Exception e) {
                            LOG.error("Exception getting instance of IMinorLeagueDraftPickDAO", e);
                        }
                    }
                }
            }
            return instance;
        }
    }
}
