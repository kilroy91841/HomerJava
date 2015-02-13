package com.homer.fantasy.dao;

import com.homer.PlayerStatus;
import com.homer.SportType;
import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.Player;
import com.homer.fantasy.PlayerHistory;
import com.homer.fantasy.Team;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 2/12/15.
 */
public class HomerDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HomerDAO.class);

    private static SessionFactory sessionFactory;

    public HomerDAO() {
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public boolean saveOrUpdate(Object o) {
        boolean success = false;
        LOG.debug("Saving " + o);
        Session session = null;
        try {
            session = openSession();
            session.beginTransaction();
            session.saveOrUpdate(o);
            session.getTransaction().commit();
            LOG.debug("Save successful!");
            success = true;
        } catch(Exception e) {
            LOG.error("Exception saving object", e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return success;
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

        LOG.debug("Creating player: " + player);

        Session session = null;
        try {
            session = openSession();
            session.beginTransaction();
            session.saveOrUpdate(player);
            session.getTransaction().commit();
            LOG.debug("Save succsesful!");
        } catch(Exception e) {
            LOG.error("Exception saving player", e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
    }

    public List<Team> getTeams(SportType sportType) {
        LOG.debug("Getting all teams for type " + sportType);
        Session session = null;
        List<Team> teams = null;
        try {
            session = openSession();
            teams = session.createCriteria(Team.class).add(Restrictions.like("teamType", sportType)).list();
        } catch(Exception e) {
            LOG.error("Exception finding teams", e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return teams;
    }

    public <T> T findByExample(Object o, Class<T> clazz) {
        Session session = null;
        T result = null;
        try {
            session = openSession();
            result = (T) session.createCriteria(clazz).add(Example.create(o)).uniqueResult();
        } catch(Exception e) {
            LOG.error("Error finding example", e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return result;
    }

    public Player findPlayerByName(String name) {
        LOG.debug("Finding player by name: " + name);
        Player player = null;
        Session session = null;
        try {
            session = openSession();
            player = (Player) session.createCriteria(Player.class).add(Restrictions.like("playerName", name)).uniqueResult();
            LOG.debug("Found player: " + player);
        } catch(NonUniqueResultException e) {
            throw e;
        } catch(Exception e) {
            LOG.error("Error finding player " + name, e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return player;
    }

    public Player findPlayerByMLBPlayerId(long playerId) {
        LOG.debug("Finding player by mlb playerid: " + playerId);
        Player player = null;
        Session session = null;
        try {
            session = openSession();
            player = (Player) session.createCriteria(Player.class).add(Restrictions.like("mlbPlayerId", playerId)).uniqueResult();
            LOG.debug("Found player: " + player);
        } catch(Exception e) {
            LOG.error("Error finding player with mlb playerid" + playerId, e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return player;
    }
}
