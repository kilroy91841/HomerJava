package com.homer.fantasy.facade;

import com.homer.PlayerStatus;
import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.Team;
import com.homer.fantasy.ThirdPartyPlayerInfo;
import com.homer.fantasy.dao.HomerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by arigolub on 2/2/15.
 */
public class PlayerFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerFacade.class);

    private HomerDAO dao;

    public PlayerFacade() {
        dao = new HomerDAO();
    }

    public Player getPlayer(Player examplePlayer) {
        return dao.findByExample(examplePlayer);
    }

    public Player getPlayer(long playerId) {
        Player example = new Player();
        example.setPlayerId(playerId);
        return getPlayer(example);
    }

    public boolean createOrUpdatePlayer(com.homer.mlb.Player mlbPlayer) throws SQLException {
        boolean success;

        Player examplePlayer = new Player();
        examplePlayer.setPrimaryPosition(Position.get(mlbPlayer.getPrimary_position()));
        examplePlayer.setPlayerName(mlbPlayer.getName_display_first_last());
        examplePlayer.addThirdPartyPlayerInfo(
                new ThirdPartyPlayerInfo(mlbPlayer.getPlayer_id(), ThirdPartyPlayerInfo.MLB));

        Player dbPlayer = dao.findByExample(examplePlayer);
        if(dbPlayer == null) {
            LOG.info("No player found, creating new player");
            success = dao.createPlayer(mlbPlayer);
            dbPlayer = dao.findByExample(examplePlayer);
            //TODO fix the date
            success = dao.createPlayerToTeam(dbPlayer, new Date(), null,
                    mlbPlayer.getTeam_id(), null, mlbPlayer.getStatus_code(), null)
                && success;
            success = dao.createPlayerHistory(dbPlayer, Calendar.getInstance().get(Calendar.YEAR), 0,
                    0, false, null, null, false) && success;
        } else {
            LOG.info("found " + dbPlayer +", updating");
            success = dao.updatePlayer(dbPlayer, examplePlayer);
        }
        return success;
    }

    public boolean createOrUpdatePlayer(com.homer.fantasy.Player fantasyPlayer, Team fantasyTeam) throws SQLException {
        boolean success;

        Player dbPlayer = dao.findByExample(fantasyPlayer);
        if(dbPlayer == null) {
            LOG.info("No player found, creating new player");
            success = dao.createPlayer(fantasyPlayer);
            dbPlayer = dao.findByExample(fantasyPlayer);
            //TODO fix the date
            success = dao.createPlayerToTeam(dbPlayer, new Date(), fantasyTeam != null ? fantasyTeam.getTeamId() : null,
                    null, null, null, dbPlayer.getPrimaryPosition())
                    && success;
            success = dao.createPlayerHistory(dbPlayer, Calendar.getInstance().get(Calendar.YEAR), 0,
                    0, false, null, null, false) && success;
        } else {
            LOG.info("Found " + dbPlayer +", updating");
            success = dao.updatePlayer(dbPlayer, fantasyPlayer);
        }
        return success;
    }

    public boolean createOrUpdatePlayer(com.homer.fantasy.Player fantasyPlayer) throws SQLException {
        return createOrUpdatePlayer(fantasyPlayer, null);
    }
}
