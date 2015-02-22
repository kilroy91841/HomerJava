package com.homer.fantasy.dao;

import com.homer.fantasy.Vulture;
import com.homer.fantasy.Player;
import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/21/15.
 */
public interface IVultureDAO {

    static final Logger LOG = LoggerFactory.getLogger(IVultureDAO.class);

    public Vulture saveVulture(Vulture vulture);

    public Vulture getVultureById(int id);

    public List<Vulture> getVulturesByPlayer(Player player);

    public List<Vulture> getVulturesByStatus(Vulture.Status status);

    public static class FACTORY {

        private static IVultureDAO instance = null;

        public static IVultureDAO getInstance() {
            if(instance == null) {
                synchronized (IVultureDAO.class) {
                    if(instance == null) {
                        try {
                            instance = Factory.getImplementation(IVultureDAO.class);
                        } catch(Exception e) {
                            LOG.error("Exception getting instance of IVultureDAO", e);
                        }
                    }
                }
            }
            return instance;
        }
    }
}
