package com.homer.fantasy.facade;

import com.homer.mlb.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arigolub on 2/15/15.
 */
public class GameFacade {

    private static final Logger LOG = LoggerFactory.getLogger(GameFacade.class);

    public boolean createOrUpdateGame(Game game) {
        LOG.debug("BEGIN: createOrUpdateGame [game=" + game + "]");
        boolean retVal = false;



        LOG.debug("END: createOrUpdateGame [retVal=" + retVal + "]");
        return retVal;
    }
}
