package com.homer.fantasy.dao;

import com.homer.fantasy.Player;
import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arigolub on 2/15/15.
 */
public interface IPlayerDAO {

    static final Logger LOG = LoggerFactory.getLogger(IPlayerDAO.class);

    public Player createOrSave(Player player);

    public Player getPlayer(Player example);

    public static class FACTORY {

        public static IPlayerDAO getInstance() {
            IPlayerDAO dao = null;
            try {
                dao = Factory.getImplementation(IPlayerDAO.class);
            } catch(Exception e) {
                LOG.error("Exception getting instance of IPlayerDAO", e);
            }
            return dao;
        }
    }
}
