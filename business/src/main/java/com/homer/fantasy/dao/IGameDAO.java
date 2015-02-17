package com.homer.fantasy.dao;

import com.homer.fantasy.Player;
import com.homer.mlb.Game;
import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arigolub on 2/15/15.
 */
public interface IGameDAO {
    static final Logger LOG = LoggerFactory.getLogger(IGameDAO.class);

    public Game getGame(Game example);

    public Game createOrUpdate(Game game);

    public static class FACTORY {

        private static IGameDAO instance = null;

        public static IGameDAO getInstance() {
            if(instance == null) {
                synchronized (IGameDAO.class) {
                    if(instance == null) {
                        try {
                            instance = Factory.getImplementation(IGameDAO.class);
                        } catch(Exception e) {
                            LOG.error("Exception getting instance of IGameDAO", e);
                        }
                    }
                }
            }
            return instance;
        }
    }
}
