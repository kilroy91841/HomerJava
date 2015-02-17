package com.homer.fantasy.facade;

import com.homer.fantasy.Player;
import com.homer.fantasy.dao.IStatsDAO;
import com.homer.mlb.Game;
import com.homer.mlb.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by arigolub on 2/15/15.
 */
public class StatsFacade {

    private static final Logger LOG = LoggerFactory.getLogger(StatsFacade.class);
    private static IStatsDAO dao;

    public StatsFacade() {
        dao = IStatsDAO.FACTORY.getInstance();
    }

    public Stats createOrUpdateStats(Stats stats) {
        LOG.debug("BEGIN: createOrUpdateStats [stats=" + stats + "]");
        Stats example = new Stats();
        example.setPlayer(stats.getPlayer());
        example.setMlbPlayerId(stats.getMlbPlayerId());
        example.setGame(stats.getGame());
        LOG.debug("Finding stats for [game=" + stats.getGame() +
                "], [player=" + stats.getPlayer() + "]");
        List<Stats> statsList = dao.getStats(example);
        Stats returnStats = null;
        if(statsList.size() == 1) {
            LOG.debug("Exactly one stats found, updating with new values");
            Stats dbStats = statsList.get(0);
            dbStats.copyNewStats(stats);
            returnStats = dao.createOrSave(dbStats);
        } else if(statsList.size() == 0) {
            LOG.debug("No stats found, creating");
            PlayerFacade playerFacade = new PlayerFacade();
            Player player = playerFacade.getPlayerFromMLBPlayerId(stats.getPlayer().getMlbPlayerId());
            stats.setPlayer(player);
            GameFacade gameFacade = new GameFacade();
            Game game = gameFacade.getGame(stats.getGame().getGameId());
            stats.setGame(game);
            returnStats = dao.createOrSave(stats);
        } else {
            LOG.debug("How are there multiple stats for [game=" + stats.getGame() +
                    "], [player=" + stats.getPlayer() + "], " + "not updating any stats");

        }
        LOG.debug("END: createOrUpdateStats [returnStats=" + returnStats + "]");
        return returnStats;
    }
}
