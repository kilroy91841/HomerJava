package com.homer.fantasy.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.ServiceRegistry;
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
        Configuration configuration = new Configuration().configure(HIBERNATE_CONFIG);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public Session openSession() {
        return sessionFactory.openSession();
//        Configuration configuration = new Configuration().configure(HIBERNATE_CONFIG);
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
//                configuration.getProperties()).build();
//        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//        return sessionFactory.openSession();
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
            session.getTransaction().commit();
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
            session.getTransaction().commit();
        } catch (RuntimeException re) {
            LOG.error("Error getting object", re);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return retList;
    }

}
