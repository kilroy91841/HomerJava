package com.homer.fantasy.facade;

import com.homer.fantasy.dao.IGameDAO;
import com.homer.mlb.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arigolub on 2/15/15.
 */
public class GameFacade {

    private static final Logger LOG = LoggerFactory.getLogger(GameFacade.class);
    private IGameDAO dao;

    public GameFacade() {
        dao = IGameDAO.FACTORY.getInstance();
    }

    public Game createOrUpdateGame(Game game) {
        LOG.debug("BEGIN: createOrUpdateGame [game=" + game + "]");

        Game dbGame = dao.createOrUpdate(game);

        LOG.debug("END: createOrUpdateGame [dbGame=" + dbGame + "]");
        return dbGame;
    }
}
