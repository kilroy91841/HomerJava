//package com.homer.fantasy.dao;
//
//import com.homer.fantasy.DailyPlayerInfo;
//import com.homer.fantasy.Player;
//import com.homer.fantasy.Team;
//import com.homer.fantasy.dao.impl.HibernateGameDAO;
//import com.homer.fantasy.dao.impl.HibernatePlayerDAO;
//import com.homer.fantasy.dao.impl.HibernateStatsDAO;
//import com.homer.fantasy.facade.PlayerFacade;
//import com.homer.mlb.Game;
//import com.homer.mlb.Stats;
//import junit.framework.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.util.List;
//
///**
// * Created by arigolub on 2/16/15.
// */
//public class HibernateStatsDAOTest {
//
//    private static final IStatsDAO dao = new HibernateStatsDAO();
//    private static final String playerName = LocalDateTime.now().toString();
//    private static final Long mlbPlayerId = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
//    private static Player player;
//
//    @BeforeClass
//    public static void setup() {
//        PlayerFacade facade = new PlayerFacade();
//        player = new Player();
//        player.setPlayerName(playerName);
//        player.setMlbPlayerId(mlbPlayerId);
//        DailyPlayerInfo dpi = new DailyPlayerInfo();
//        dpi.setPlayer(player);
//        dpi.setDate(LocalDate.now());
//        player = facade.createOrUpdatePlayer(player);
//    }
//
//    @Test
//    public void createOrUpdate() {
//        Game game = new Game();
//        game.setGameId(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
//        game.setHomeTeam(new Team(147));
//        game.setAwayTeam(new Team(147));
//        IGameDAO gameDao = new HibernateGameDAO();
//        gameDao.createOrUpdate(game);
//
//        Stats s = new Stats();
//        s.setGame(game);
//        s.setDailyPlayerInfo(player.getDailyPlayerInfoList().get(0));
//        s.setAb(3);
//        s.setAvg(.032);
//
//
//        Stats dbStats = dao.createOrSave(s);
//        Assert.assertNotNull(dbStats);
//        Assert.assertNotNull(dbStats.getStatsId());
//        Assert.assertEquals(3, (int)dbStats.getAb());
//        Assert.assertEquals(.032, dbStats.getAvg());
//    }
//
//    @Test
//    public void getStats() {
//        Game game = new Game();
//        game.setGameId(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
//        game.setHomeTeam(new Team(147));
//        game.setAwayTeam(new Team(147));
//        IGameDAO gameDao = new HibernateGameDAO();
//        gameDao.createOrUpdate(game);
//
//        game = new Game();
//        game.setGameId(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(3)));
//        game.setHomeTeam(new Team(147));
//        game.setAwayTeam(new Team(147));
//        gameDao.createOrUpdate(game);
//
//        Stats s = new Stats();
//        s.setGame(game);
//        Player player = new Player(2L);
//        player.setMlbPlayerId(HibernatePlayerDAOTest.mlbPlayerId);
//        DailyPlayerInfo dpi = new DailyPlayerInfo();
//        dpi.setDailyPlayerInfoId(1L);
//        dpi.setPlayer(player);
//        s.setDailyPlayerInfo(dpi);
//        s.setAb(5);
//        dao.createOrSave(s);
//
//        Stats example = new Stats();
//        player = new Player(1L);
//        player.setMlbPlayerId(HibernatePlayerDAOTest.mlbPlayerId);
//        dpi = new DailyPlayerInfo();
//        dpi.setDailyPlayerInfoId(2L);
//        dpi.setPlayer(player);
//        example.setDailyPlayerInfo(dpi);
//        example.setGame(game);
//        List<Stats> statsList = dao.getStats(example);
//        Assert.assertNotNull(statsList);
//
//    }
//}
