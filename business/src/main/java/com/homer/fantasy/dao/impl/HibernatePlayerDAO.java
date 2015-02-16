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
}
