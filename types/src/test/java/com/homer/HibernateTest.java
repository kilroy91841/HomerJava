package com.homer;

import com.homer.fantasy.*;
import junit.framework.Assert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.FetchMode;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by arigolub on 2/10/15.
 */
public class HibernateTest {

    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void beforeClass() {
        // A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
    }

    @Test
    public void save() {
        Player p = new Player("Mike Trout1");
        p.setPrimaryPosition(Position.CENTERFIELD);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(p);
        session.getTransaction().commit();
        session.close();

        Team t = new Team();
        t.setTeamId(300);
        t.setTeamName("ari golub");
        t.setTeamCode("code");
        t.setTeamType(SportType.FANTASY);

        session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(t);
        session.getTransaction().commit();

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<DailyPlayerInfo> positions = session.createCriteria(DailyPlayerInfo.class).list();
        System.out.println(positions);

//        session = sessionFactory.openSession();
//        session.beginTransaction();
//        Player player = (Player)session.createCriteria(Player.class).add(Restrictions.like("playerName", "Ari Golub")).uniqueResult();
//        System.out.println(player);
//        System.out.println(player.getDailyPlayerInfoList().size());
//        System.out.println(player.getDailyPlayerInfoList().get(0));
//        System.out.println(player.getPlayerHistoryList().size());
//        System.out.println(player.getPlayerHistoryList().get(0));
//
//        session = sessionFactory.openSession();
//        session.beginTransaction();
//        List<MinorLeagueDraftPick> minorLeagueDraftPicks = session.createCriteria(MinorLeagueDraftPick.class).list();
//        System.out.println(minorLeagueDraftPicks);
//
//        session = sessionFactory.openSession();
//        session.beginTransaction();
//        List<FreeAgentAuction> freeAgentAuctions = session.createCriteria(FreeAgentAuction.class).list();
//        System.out.println(freeAgentAuctions);
//
//        session = sessionFactory.openSession();
//        session.beginTransaction();
//        List<Money> moneys = session.createCriteria(Money.class).list();
//        System.out.println(moneys);
//
//        session = sessionFactory.openSession();
//        session.beginTransaction();
//        List<Vulture> vultures = session.createCriteria(Vulture.class).list();
//        System.out.println(vultures);

        session = sessionFactory.openSession();
        session.beginTransaction();
        List<Trade> trades = session.createCriteria(Trade.class).list();
        System.out.println(trades);

        session.close();
    }
}
