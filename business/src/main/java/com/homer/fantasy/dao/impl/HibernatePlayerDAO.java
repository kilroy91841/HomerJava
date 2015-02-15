package com.homer.fantasy.dao.impl;

import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.IPlayerDAO;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by arigolub on 2/15/15.
 */
public class HibernatePlayerDAO extends HomerDAO implements IPlayerDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HibernatePlayerDAO.class);

    @Override
    public Player createOrSave(Player player) {
        LOG.debug("BEGIN: createOrSave [player=" + player +"]");
        Session session = null;
        try {
            session = openSession();
            session.beginTransaction();
            session.saveOrUpdate(player);
            session.getTransaction().commit();
            session.flush();
        } catch (RuntimeException re) {
            LOG.error("Error saving player", re);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        LOG.debug("END: createOrSave [player=" + player + "]");
        return player;
    }

    @Override
    public Player getPlayer(Player player) {
        LOG.debug("BEGIN: getPlayer [example=" + player + "]");
        Player dbPlayer = null;
        Session session = null;
        try {
            session = openSession();
            session.beginTransaction();
            Example example = Example.create(player);
            dbPlayer = (Player) session.createCriteria(Player.class).add(example).uniqueResult();
        } catch (RuntimeException re) {
            LOG.error("Error getting player", re);
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return dbPlayer;
    }
}
