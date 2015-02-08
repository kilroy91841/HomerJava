package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
import com.homer.fantasy.*;
import com.homer.fantasy.dao.HomerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by golub on 2/5/15.
 */
public class TransactionFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionFacade.class);

    private HomerDAO dao;

    public TransactionFacade() {
        dao = new HomerDAO();
    }

    public boolean addFreeAgent(Player example, int newFantasyTeam, Position fantasyPosition) throws Exception {
        LOG.debug("Adding " + example + " to team " + newFantasyTeam);
        boolean success = false;

        Player player = dao.findByExample(example);
        if(player != null) {
            Team currentTeam = player.getCurrentFantasyTeam();
            if(currentTeam == null) {
                success = dao.updateDailyFantasyProperties(player, newFantasyTeam, PlayerStatus.ACTIVE, fantasyPosition);
            } else {
                throw new Exception("Player is not a free agent");
            }
        } else {
            throw new Exception("Could not find player matching " + example);
        }

        return success;
    }

    public boolean dropPlayer(Player example) throws Exception {
        LOG.debug("Dropping " + example);
        boolean success = false;

        Player player = dao.findByExample(example);
        if(player != null) {
            Team currentTeam = player.getCurrentFantasyTeam();
            if(currentTeam != null) {
                success = dao.updateDailyFantasyProperties(player, null, PlayerStatus.INACTIVE, null);
            } else {
                throw new Exception("Player is a free agent, can't drop");
            }
        } else {
            LOG.error("Could not find player matching " + example);
            success = false;
        }

        return success;
    }

    public boolean promoteToMajors(Player example) {
        LOG.debug("Promoting player to majors");
        boolean success = false;

        Player player = dao.findByExample(example);
        if(player != null) {
            try {
                List<PlayerHistory> playerHistoryList = dao.getPlayerHistory(player);
                if(playerHistoryList != null && playerHistoryList.size() > 0) {
                    PlayerHistory history = playerHistoryList.get(0);
                    history.setMinorLeaguer(false);
                    success = dao.updatePlayerHistory(player, history);
                } else {
                    LOG.error("No player history found");
                }
            } catch (Exception e) {
                LOG.error("Error retrieving player history", e);
            }
        } else {
            LOG.error("Could not find player matching " + example);
        }
        return success;
    }
}
