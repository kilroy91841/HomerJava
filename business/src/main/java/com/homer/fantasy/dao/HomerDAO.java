package com.homer.fantasy.dao;

import com.homer.PlayerStatus;
import com.homer.SportType;
import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.Player;
import com.homer.fantasy.PlayerHistory;
import com.homer.fantasy.Team;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 2/12/15.
 */
public class HomerDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HomerDAO.class);

    private static SessionFactory sessionFactory;
    private static final String HIBERNATE_CONFIG = "hibernate_" + System.getProperty("env") + ".cfg.xml";

    public HomerDAO() {
        sessionFactory = new Configuration()
                .configure(HIBERNATE_CONFIG) // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    protected boolean saveOrUpdate(Object o) {
        LOG.debug("BEGIN: saveOrUpdate [object=" + o + "]");
        boolean success = false;
        Session session = null;
        try {
            session = openSession();
            session.beginTransaction();
            session.saveOrUpdate(o);
            session.getTransaction().commit();
            session.flush();
            LOG.debug("Save successful!");
            success = true;
        } catch(Exception e) {
            LOG.error("Exception saving object", e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        LOG.debug("END: saveOrUpdate [object=" + o + "]");
        return success;
    }

    protected boolean saveNoUpdate(Object o) {
        LOG.debug("BEGIN: saveNoUpdate [object=" + o + "]");
        boolean success = false;
        Session session = null;
        try {
            session = openSession();
            session.beginTransaction();
            session.save(o);
            session.getTransaction().commit();
            session.flush();
            LOG.debug("Save successful!");
            success = true;
        } catch(ConstraintViolationException e) {
            LOG.error("Constraint violated!", e);
            throw e;
        } catch(Exception e) {
            LOG.error("Exception saving object", e);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        LOG.debug("END: saveNoUpdate [object=" + o + "]");
        return success;
    }

    public <T> T findUniqueByExample(Object obj, Class<T> clazz) {
        T retVal = null;
        Session session = null;
        try {
            session = openSession();
            session.beginTransaction();
            Example example = Example.create(obj);
            retVal = (T) session.createCriteria(clazz).add(example).uniqueResult();
        } catch (RuntimeException re) {
            LOG.error("Error getting object, [clazz=" + clazz + ", obj=" + obj + "]", re);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return retVal;
    }

    public <T> T findUniqueById(Object obj, Class<T> clazz) {
        T retVal = null;
        Session session = null;
        try {
            session = openSession();
            session.beginTransaction();
            retVal = (T) session.get(clazz, (Serializable) obj);
        } catch (RuntimeException re) {
            LOG.error("Error getting object, [clazz=" + clazz + ", obj=" + obj + "]", re);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return retVal;
    }

    public <T> List<T> findListByExample(Object obj, Class<T> clazz) {
        List<T> retList = null;
        Session session = null;
        try {
            session = openSession();
            session.beginTransaction();
            Example example = Example.create(obj);
            retList = session.createCriteria(clazz).add(example).list();
        } catch (RuntimeException re) {
            LOG.error("Error getting object", re);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return retList;
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

}
