package com.homer.fantasy.dao;

import com.homer.fantasy.Player;
import com.homer.fantasy.Team;
import com.homer.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by arigolub on 2/15/15.
 */
public interface IPlayerDAO {

    static final Logger LOG = LoggerFactory.getLogger(IPlayerDAO.class);

    public Player createOrSave(Player player);

    public Player getPlayer(Player example);

    public List<Player> getPlayersByYear(int season);

    public List<Player> getPlayersOnTeamForDate(Team team, LocalDate date);

    public static class FACTORY {

        private static IPlayerDAO instance = null;

        public static IPlayerDAO getInstance() {
            if(instance == null) {
                synchronized (IPlayerDAO.class) {
                    if(instance == null) {
                        try {
                            instance = Factory.getImplementation(IPlayerDAO.class);
                        } catch(Exception e) {
                            LOG.error("Exception getting instance of IPlayerDAO", e);
                        }
                    }
                }
            }
            return instance;
        }
    }
}
