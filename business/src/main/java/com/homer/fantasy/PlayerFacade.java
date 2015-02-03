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

    public PlayerResponse createPlayer(com.homer.mlb.Player mlbPlayer) {
        PlayerResponse createResponse = new PlayerResponse();

        Player examplePlayer = new Player();
        examplePlayer.setPlayerName(mlbPlayer.getName_display_first_last());
        ThirdPartyPlayerInfo info = new ThirdPartyPlayerInfo(examplePlayer, mlbPlayer.getPlayer_id(), ThirdPartyPlayerInfo.MLB);
        examplePlayer.getThirdPartyPlayerInfoList().add(info);

        HomerDAO dao = new HomerDAO();

        PlayerResponse findByExampleResponse = dao.findByExample(examplePlayer);
        if(PlayerResponse.DATA_NOT_FOUND == findByExampleResponse.getStatus()) {
            LOG.info("No player found, creating new player");
            createResponse = dao.createPlayer(mlbPlayer);
        } else if(PlayerResponse.SUCCESS == findByExampleResponse.getStatus()) {
            LOG.info("Found player: {}", findByExampleResponse.getPlayer());
            createResponse.setStatus(PlayerResponse.DATA_ALREADY_EXISTS);
        } else {
            LOG.error("Something went wrong trying to find player: " + findByExampleResponse.getErrorMesage(),  findByExampleResponse.getException());
        }
        return createResponse;
    }
}
