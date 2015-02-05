package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.Team;
import com.homer.fantasy.ThirdPartyPlayerInfo;
import com.homer.fantasy.dao.HomerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by golub on 2/5/15.
 */
public class TransactionFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionFacade.class);

    private HomerDAO dao;

    public TransactionFacade() {
        dao = new HomerDAO();
    }

    public boolean addFreeAgent(long mlbPlayerId, int newFantasyTeam, Position fantasyPosition) throws Exception {
        boolean success = false;

        Player example = new Player();
        example.addThirdPartyPlayerInfo(new ThirdPartyPlayerInfo(mlbPlayerId, ThirdPartyPlayerInfo.MLB));

        Player player = dao.findByExample(example);
        if(player != null) {
            Team currentTeam = player.getCurrentFantasyTeam();
            if(currentTeam == null) {
                dao.updateDailyFantasyProperties(player, newFantasyTeam, PlayerStatus.ACTIVE, fantasyPosition);
            } else {
                throw new Exception("Player is not a free agent");
            }
        } else {
            LOG.error("Could not find player with MLB player id " + mlbPlayerId);
            success = false;
        }

        return success;
    }
}
