package com.homer.fantasy.facade;

import com.homer.fantasy.Player;
import com.homer.fantasy.Roster;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.IPlayerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by arigolub on 2/22/15.
 */
public class RosterFacade {

    private static final Logger LOG = LoggerFactory.getLogger(RosterFacade.class);

    private static IPlayerDAO playerDAO;

    public RosterFacade() {
        playerDAO = IPlayerDAO.FACTORY.getInstance();
    }

    public Roster getRoster(Team team, LocalDate date) {
        LOG.debug("BEGIN: getRoster [team=" + team + ", date=" + date + "]");

        List<Player> players = playerDAO.getPlayersOnTeamForDate(team, date);
        Roster roster = new Roster(players);

        LOG.debug("END: getRoster [roster=" + roster + "]");
        return roster;
    }

}
