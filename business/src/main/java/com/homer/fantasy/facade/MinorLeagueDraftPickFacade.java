package com.homer.fantasy.facade;

import com.homer.fantasy.MinorLeagueDraftPick;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.IMinorLeagueDraftPickDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/24/15.
 */
public class MinorLeagueDraftPickFacade {

    private static final Logger LOG = LoggerFactory.getLogger(MinorLeagueDraftPickFacade.class);
    private static IMinorLeagueDraftPickDAO dao;

    public MinorLeagueDraftPickFacade() {
        dao = IMinorLeagueDraftPickDAO.FACTORY.getInstance();
    }

    public List<MinorLeagueDraftPick> getMinorLeagueDraftPicksForTeam(Team owningTeam) {
        LOG.debug("BEGIN: getMinorLeagueDraftPicksForTeam");

        List<MinorLeagueDraftPick> picks = dao.getDraftPicksForTeam(owningTeam);

        LOG.debug("END: getMinorLeagueDraftPicksForTeam [picks=" + picks + "]");
        return picks;
    }

    public MinorLeagueDraftPick transferMinorLeagueDraftPick(MinorLeagueDraftPick minorLeagueDraftPick, Team newOwningTeam) {
        LOG.debug("BEGIN: transferMinorLeagueDraftPick [minorLeagueDraftPick=" + minorLeagueDraftPick + ", newOwningTeam=" + newOwningTeam + "]");

        MinorLeagueDraftPick dbPick = dao.getDraftPick(minorLeagueDraftPick.getOriginalTeam(), minorLeagueDraftPick.getSeason(), minorLeagueDraftPick.getRound());
        if(dbPick == null) {
            LOG.error("No pick found matching: " + minorLeagueDraftPick);
            return null;
        }
        LOG.debug("Setting owning team of pick " + minorLeagueDraftPick + " to team " + newOwningTeam);
        dbPick.setOwningTeam(newOwningTeam);
        minorLeagueDraftPick = dao.savePick(dbPick);

        LOG.debug("END: transferMinorLeagueDraftPick [minorLeagueDraftPick=" + minorLeagueDraftPick + "]");
        return minorLeagueDraftPick;
    }
}
