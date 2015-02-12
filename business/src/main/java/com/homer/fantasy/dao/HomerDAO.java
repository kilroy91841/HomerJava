package com.homer.fantasy.dao;

import com.homer.PlayerStatus;
import com.homer.SportType;
import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.Player;
import com.homer.fantasy.PlayerHistory;
import com.homer.fantasy.Team;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 2/12/15.
 */
public class HomerDAO {

    private static SessionFactory sessionFactory;

    public HomerDAO() {
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public void saveOrUpdate(Object o) {
        Session session = openSession();
        session.beginTransaction();
        session.saveOrUpdate(o);
        session.getTransaction().commit();
        session.close();
    }

    public void createPlayer(Player player) {
        DailyPlayerInfo info = new DailyPlayerInfo();
        info.setPlayer(player);
        info.setDate(new Date());
        info.setMlbStatus(PlayerStatus.ACTIVE);
        player.getDailyPlayerInfoList().add(info);

        PlayerHistory history = new PlayerHistory();
        history.setPlayer(player);
        history.setSeason(2015);
        player.getPlayerHistoryList().add(history);

        Session session = openSession();
        session.beginTransaction();
        session.saveOrUpdate(player);
        session.getTransaction().commit();
        session.close();
    }

    public List<Team> getTeams(SportType sportType) {
        Session session = openSession();
        List<Team> teams = session.createCriteria(Team.class).add(Restrictions.like("teamType", sportType)).list();
        session.close();
        return teams;
    }

    public <T> T findByExample(Object o, Class<T> clazz) {
        Session session = openSession();
        T result = (T) session.createCriteria(clazz).add(Example.create(o)).uniqueResult();
        session.close();
        return result;
    }

    public Player findPlayerByName(String name) {
        Player player = null;
        Session session = null;
        try {
            session = openSession();
            player = (Player) session.createCriteria(Player.class).add(Restrictions.like("playerName", name)).uniqueResult();
        } catch(Exception e) {

        } finally {
            if(session != null) {
                session.close();
            }
        }
        return player;
    }
}
