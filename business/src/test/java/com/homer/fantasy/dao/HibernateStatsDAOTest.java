package com.homer.fantasy.dao;

import com.homer.fantasy.Player;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.impl.HibernateGameDAO;
import com.homer.fantasy.dao.impl.HibernatePlayerDAO;
import com.homer.fantasy.dao.impl.HibernateStatsDAO;
import com.homer.mlb.Game;
import com.homer.mlb.Stats;
import junit.framework.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Created by arigolub on 2/16/15.
 */
public class HibernateStatsDAOTest {

    private static final IStatsDAO dao = new HibernateStatsDAO();

    @Test
    public void createOrUpdate() {
        Game game = new Game();
        game.setGameId(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        game.setHomeTeam(new Team(147));
        game.setAwayTeam(new Team(147));
        IGameDAO gameDao = new HibernateGameDAO();
        gameDao.createOrUpdate(game);

        Stats s = new Stats();
        s.setGame(game);
        s.setPlayer(new Player(1L));
        s.setMlbPlayerId(HibernatePlayerDAOTest.mlbPlayerId);
        s.setAb(3);
        s.setAvg(.032);

        Stats dbStats = dao.createOrSave(s);
        Assert.assertNotNull(dbStats);
        Assert.assertNotNull(dbStats.getStatsId());
        Assert.assertEquals(3, (int)dbStats.getAb());
        Assert.assertEquals(.032, dbStats.getAvg());
    }

    @Test
    public void getStats() {
        Game game = new Game();
        game.setGameId(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        game.setHomeTeam(new Team(147));
        game.setAwayTeam(new Team(147));
        IGameDAO gameDao = new HibernateGameDAO();
        gameDao.createOrUpdate(game);

        game = new Game();
        game.setGameId(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(3)));
        game.setHomeTeam(new Team(147));
        game.setAwayTeam(new Team(147));
        gameDao.createOrUpdate(game);

        Stats s = new Stats();
        s.setGame(game);
        s.setPlayer(new Player(2L));
        s.setMlbPlayerId(HibernatePlayerDAOTest.mlbPlayerId);
        s.setAb(5);
        dao.createOrSave(s);

        Stats example = new Stats();
        example.setPlayer(new Player(1L));
        example.setMlbPlayerId(HibernatePlayerDAOTest.mlbPlayerId);
        example.setGame(game);
        List<Stats> statsList = dao.getStats(example);
        Assert.assertNotNull(statsList);

    }
}
