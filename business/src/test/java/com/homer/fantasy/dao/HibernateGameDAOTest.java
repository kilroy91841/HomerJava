//package com.homer.fantasy.dao;
//
//import com.homer.fantasy.Player;
//import com.homer.fantasy.Team;
//import com.homer.fantasy.dao.impl.HibernateGameDAO;
//import com.homer.fantasy.dao.impl.HibernatePlayerDAO;
//import com.homer.mlb.Game;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//
///**
//* Created by arigolub on 2/16/15.
//*/
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class HibernateGameDAOTest {
//
//    private static final IGameDAO dao = new HibernateGameDAO();
//    private static final long gameId = 1L;
//    private static final LocalDate gameDate = LocalDate.now();
//    private static LocalDateTime gameTime = LocalDateTime.now();
//    private static String playerName = LocalDateTime.now().toString();
//    private static long mlbPlayerId = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
//    private static Player player;
//    private static long playerId;
//
//    @BeforeClass
//    public static void setup() {
//        player = new Player();
//        player.setPlayerName(playerName);
////
//        HibernatePlayerDAO playerDAO = new HibernatePlayerDAO();
//        player.setMlbPlayerId(mlbPlayerId);
//        player = playerDAO.createOrSave(player);
//        mlbPlayerId = player.getMlbPlayerId();
//    }
//
//    @Test
//    public void createOrUpdate() {
//        gameTime = gameTime.minusNanos(gameTime.getNano());
//        Game g = new Game();
//        g.setGameId(mlbPlayerId);
//        g.setGameDate(gameDate);
//        g.setAmPm("am");
//        g.setAwayScore(3);
//        g.setHomeScore(4);
//        g.setAwayTeam(new Team(147));
//        g.setHomeTeam(new Team(147));
//        Player player = new Player();
//        player.setMlbPlayerId(mlbPlayerId);
//        g.setHomeProbablePitcher(player);
//        g.setAwayProbablePitcher(player);
//        g.setGamedayUrl("this is a verrrry long url");
//        g.setGameTime(gameTime);
//        g.setInning("4");
//        g = dao.createOrUpdate(g);
//        Assert.assertNotNull(g);
//    }
////
////    @Test
////    public void getGame() {
////        Game example = new Game();
////        example.setGameId(gameId);
////        Game dbGame = dao.getGame(example);
////        Assert.assertNotNull(dbGame);
////        Assert.assertEquals(147, (long)dbGame.getAwayTeam().getTeamId());
////        Assert.assertEquals(147, (long)dbGame.getHomeTeam().getTeamId());
////        Assert.assertEquals("this is a verrrry long url", dbGame.getGamedayUrl());
////        Assert.assertNotNull(dbGame.getAwayProbablePitcher());
////        Assert.assertNotNull(dbGame.getHomeProbablePitcher());
////    }
//}
