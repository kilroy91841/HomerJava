package com.homer.fantasy;

import com.homer.dao.response.PlayerResponse;
import com.homer.fantasy.dao.HomerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public boolean createOrUpdatePlayer(com.homer.mlb.Player mlbPlayer) {
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
        } else {
            LOG.info("found " + dbPlayer +", updating");
            success = dao.updatePlayer(dbPlayer, examplePlayer);
        }
        return success;
    }

    public boolean createOrUpdatePlayer(com.homer.fantasy.Player fantasyPlayer) {
        boolean success;

        Player dbPlayer = dao.findByExample(fantasyPlayer);
        if(dbPlayer == null) {
            LOG.info("No player found, creating new player");
            success = dao.createPlayer(fantasyPlayer);
        } else {
            LOG.info("Found " + dbPlayer +", updating");
            success = dao.updatePlayer(dbPlayer, fantasyPlayer);
        }
        return success;
    }
}
