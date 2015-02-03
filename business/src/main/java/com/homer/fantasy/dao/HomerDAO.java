package com.homer.fantasy.dao;

import com.homer.dao.MySQLDAO;
import com.homer.dao.response.PlayerResponse;
import com.homer.exception.NoDataSearchMethodsProvidedException;
import com.homer.fantasy.Player;
import com.homer.fantasy.dao.creator.PlayerCreator;
import com.homer.fantasy.dao.searcher.Searcher;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByMLBPlayerId;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByPlayerId;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByPlayerName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by arigolub on 2/2/15.
 */
public class HomerDAO extends MySQLDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HomerDAO.class);

    public PlayerResponse findByExample(Player example) {
        PlayerResponse response = new PlayerResponse();

        Searcher<Player> searcher = new Searcher<Player>().findExample(example)
                .addSearcher(new PlayerSearchByPlayerId())
                .addSearcher(new PlayerSearchByMLBPlayerId())
                .addSearcher(new PlayerSearchByPlayerName());

        Player player = null;
        Connection connection = null;

        try {
            connection = getConnection();
            player = searcher.search(connection);
            if(player != null) {
                response.setStatus(PlayerResponse.SUCCESS);
                response.setPlayer(player);
            } else {
                response.setStatus(PlayerResponse.DATA_NOT_FOUND);
            }

        } catch (NoDataSearchMethodsProvidedException e) {
            String errorMessage = "No search methods provided";
            LOG.error(errorMessage, e);
            response.setErrorMesage(errorMessage);
            response.setException(e);
            response.setStatus(PlayerResponse.ERROR);
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    String errorMessage = "Could not close connection";
                    LOG.error(errorMessage, e);
                    response.setErrorMesage(errorMessage);
                    response.setException(e);
                    response.setStatus(PlayerResponse.RUNTIME_EXCEPTION);
                }
            }
        }
        return response;
    }

    public PlayerResponse createPlayer(com.homer.mlb.Player mlbPlayer) {
        PlayerCreator playerCreator = new PlayerCreator();
        LOG.info("Creating player {}", mlbPlayer);
        return playerCreator.create(mlbPlayer);
    }
}
