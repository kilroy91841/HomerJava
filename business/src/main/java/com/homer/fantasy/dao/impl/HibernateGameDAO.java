package com.homer.fantasy.dao.impl;

import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.IGameDAO;
import com.homer.mlb.Game;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arigolub on 2/16/15.
 */
public class HibernateGameDAO extends HomerDAO implements IGameDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateGameDAO.class);

    @Override
    public Game createOrUpdate(Game game) {
        LOG.debug("BEGIN: createOrUpdateGame [game=" + game + "]");
        boolean success = saveOrUpdate(game);
        if(!success) {
            game = null;
        }
        LOG.debug("END: createOrUpdateGame [game=" + game + "]");
        return game;
    }


    @Override
    public Game getGame(Game example) {
        LOG.debug("BEGIN: getGame [example=" + example + "]");
        Game dbGame;
        if(example.getGameId() != null) {
            dbGame = findUniqueById(example.getGameId(), Game.class);
        } else {
            dbGame = findUniqueByExample(example, Game.class);
        }
        LOG.debug("END: getGame [result=" + dbGame + "]");
        return dbGame;
    }
}
