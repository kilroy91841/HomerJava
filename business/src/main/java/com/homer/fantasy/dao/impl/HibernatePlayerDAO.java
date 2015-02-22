package com.homer.fantasy.dao.impl;

import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.IPlayerDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/15/15.
 */
public class HibernatePlayerDAO extends HomerDAO implements IPlayerDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HibernatePlayerDAO.class);

    @Override
    public Player createOrSave(Player player) {
        LOG.debug("BEGIN: createOrSave [player=" + player +"]");
        boolean success = saveOrUpdate(player);
        if(!success) {
            player = null;
        }
        LOG.debug("END: createOrSave [player=" + player + "]");
        return player;
    }

    @Override
    public Player getPlayer(Player player) {
        LOG.debug("BEGIN: getPlayer [example=" + player + "]");
        Player dbPlayer;
        if(player.getPlayerId() != null) {
            dbPlayer = findUniqueById(player.getPlayerId(), Player.class);
        } else {
            dbPlayer = findUniqueByExample(player, Player.class);
        }
        LOG.debug("END: getPlayer [result=" + dbPlayer + "]");
        return dbPlayer;
    }

    @Override
    public List<Player> getPlayersByYear(int season) {
        LOG.debug("BEGIN: getPlayersByYear [year=" + season + "]");

        List<Player> players = null;
        Session session = null;
        try {
            session = openSession();
            session.beginTransaction();

            Query query = session.createQuery("select p from Player p inner join p.playerHistoryList as playerHistory where playerHistory.playerHistoryKey.season = :season");
            query.setParameter("season", season);
            players = (List<Player>)query.list();
        } catch(Exception e) {
            LOG.error("Exception saving object", e);
        } finally {
            if(session != null) {
                session.close();
            }
        }

        LOG.debug("END: getPlayersByYear");
        return players;
    }
}
