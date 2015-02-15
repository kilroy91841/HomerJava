package com.homer;

import com.homer.fantasy.*;
import com.homer.mlb.Game;
import com.homer.mlb.MLBJSONObject;
import com.homer.mlb.Stats;
import junit.framework.Assert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 2/10/15.
 */
public class HibernateTest {

    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void beforeClass() {
        // A SessionFactory is set up once for an application

        sessionFactory = new Configuration()
                .configure("hibernate_dev.cfg.xml") // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
    }

    @Test
    public void save() throws Exception {
        Game g = new Game();
        g.setGameId(2L);
        Team t = new Team();
        t.setTeamId(147);
        g.setHomeTeam(t);
        g.setAwayTeam(t);
        g.setGameDate(LocalDate.now());
        g.setGameTime(LocalDateTime.now());

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(g);
        session.getTransaction().commit();
        session.flush();
        session.close();

        Player p = new Player();
        p.setPlayerName("Ira Bulog");
        p.setPrimaryPosition(Position.CENTERFIELD);
        DailyPlayerInfo info = new DailyPlayerInfo();
        info.setMlbStatus(PlayerStatus.ACTIVE);
        info.setDate(LocalDate.now());
        info.getDailyPlayerInfoKey().setPlayer(p);
        JSONObject stat = new JSONObject();
        stat.put("game_pk", "2");
        Stats s = new Stats(p, new MLBJSONObject(stat));
        s.setGame(g);
        info.getStatsList().add(s);
        p.getDailyPlayerInfoList().add(info);
        PlayerHistory history = new PlayerHistory();
        history.setPlayer(p);
        history.setSeason(2015);
        p.getPlayerHistoryList().add(history);
        history = new PlayerHistory();
        history.setPlayer(p);
        history.setSeason(2016);
        p.getPlayerHistoryList().add(history);
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(p);
        session.getTransaction().commit();
        session.flush();
        System.out.println(p);
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<Stats> stats = session.createCriteria(Stats.class).list();
        System.out.println(stats);


        t = new Team();
        t.setTeamId(300);
        t.setTeamName("ari golub");
        t.setTeamCode("code");
        t.setTeamType(SportType.FANTASY);

        session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(t);
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<DailyPlayerInfo> positions = session.createCriteria(DailyPlayerInfo.class).list();
        System.out.println(positions.size());
        System.out.println(positions);
        session.close();

//        session = sessionFactory.openSession();
//        session.beginTransaction();
//        Player player = (Player)session.createCriteria(Player.class).add(Restrictions.like("playerName", "Ari Golub")).uniqueResult();
//        System.out.println(player);
//        System.out.println(player.getDailyPlayerInfoList().size());
//        System.out.println(player.getDailyPlayerInfoList().get(0));
//        System.out.println(player.getPlayerHistoryList().size());
//        System.out.println(player.getPlayerHistoryList().get(0));
//        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<MinorLeagueDraftPick> minorLeagueDraftPicks = session.createCriteria(MinorLeagueDraftPick.class).list();
        System.out.println(minorLeagueDraftPicks);
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<FreeAgentAuction> freeAgentAuctions = session.createCriteria(FreeAgentAuction.class).list();
        System.out.println(freeAgentAuctions);
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<Money> moneys = session.createCriteria(Money.class).list();
        System.out.println(moneys);
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<Vulture> vultures = session.createCriteria(Vulture.class).list();
        System.out.println(vultures);
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<Trade> trades = session.createCriteria(Trade.class).list();
        System.out.println(trades);
        session.close();




        session = sessionFactory.openSession();
        List<Game> games = session.createCriteria(Game.class).list();
        Collections.sort(games, (g1, g2) ->
                g1.getGameTime() == null ? 1 :
                        g2.getGameTime() == null ? -1 :
                                g1.getGameTime().compareTo(g2.getGameTime()))
        ;
        for(Game game : games) {
            System.out.println(game);
        }

        p = (Player)session.createCriteria(Player.class).add(Restrictions.like("playerId",p.getPlayerId())).uniqueResult();
        Assert.assertNotNull(p);
        Assert.assertEquals(2, p.getPlayerHistoryList().size());
        Assert.assertEquals(1, p.getDailyPlayerInfoList().size());
        session.close();
    }
}
