package com.homer;

import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;

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
        Player p = new Player("Ari Golub");
        p.setPrimaryPosition(Position.CENTERFIELD);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(p);
        session.getTransaction().commit();
        session.close();
    }
}
