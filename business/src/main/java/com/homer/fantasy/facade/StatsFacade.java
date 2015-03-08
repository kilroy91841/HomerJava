package com.homer.fantasy.facade;

import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.Player;
import com.homer.fantasy.dao.IStatsDAO;
import com.homer.mlb.Game;
import com.homer.mlb.Stats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by arigolub on 2/15/15.
 */
public class StatsFacade {

    private static final Logger LOG = LoggerFactory.getLogger(StatsFacade.class);
    private static final PlayerFacade playerFacade = new PlayerFacade();
    private static IStatsDAO dao;

    public StatsFacade() {
        dao = IStatsDAO.FACTORY.getInstance();
    }

    public Stats createOrUpdateStats(Stats stats) {
        LOG.debug("BEGIN: createOrUpdateStats [stats=" + stats + "]");
        Player player = playerFacade.getPlayerFromMLBPlayerId(stats.getDailyPlayerInfo().getPlayer().getMlbPlayerId());
        LOG.debug("Finding stats for [game=" + stats.getGame() +
                "], [player=" + stats.getDailyPlayerInfo().getPlayer() + "]");
        DailyPlayerInfo dailyPlayerInfo = player.getDailyPlayerInfoList().stream()
                .filter(dpi -> dpi.getDate().equals(stats.getGame().getGameDate()))
                .collect(Collectors.toList())
                .get(0);
        List<Stats> statsList = dailyPlayerInfo.getStatsList().stream()
                .filter(stat -> stat.getGame().equals(stats.getGame()))
                .collect(Collectors.toList());
        Stats returnStats = null;
        if(statsList.size() == 1) {
            LOG.debug("Exactly one stats found, updating with new values");
            Stats dbStats = statsList.get(0);
            dbStats.copyNewStats(stats);
            returnStats = dao.createOrSave(dbStats);
        } else if(statsList.size() == 0) {
            LOG.debug("No stats found, creating");
            stats.setDailyPlayerInfo(dailyPlayerInfo);
            GameFacade gameFacade = new GameFacade();
            Game game = gameFacade.getGame(stats.getGame().getGameId());
            stats.setGame(game);
            returnStats = dao.createOrSave(stats);
        } else {
            LOG.debug("How are there multiple stats for [game=" + stats.getGame() +
                    "], [player=" + player + "], " + "not updating any stats");

        }
        LOG.debug("END: createOrUpdateStats [returnStats=" + returnStats + "]");
        return returnStats;
    }
}
