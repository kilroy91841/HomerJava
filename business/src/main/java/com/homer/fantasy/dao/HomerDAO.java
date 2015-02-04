package com.homer.fantasy.dao;

import com.homer.dao.response.PlayerResponse;
import com.homer.exception.NoDataSearchMethodsProvidedException;
import com.homer.fantasy.Player;
import com.homer.fantasy.ThirdPartyPlayerInfo;
import com.homer.fantasy.dao.creator.PlayerCreator;
import com.homer.fantasy.dao.searcher.Searcher;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByMLBPlayerId;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByPlayerId;
import com.homer.fantasy.dao.searcher.player.PlayerSearchByPlayerName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by arigolub on 2/2/15.
 */
public class HomerDAO extends MySQLDAO {

    private static final Logger LOG = LoggerFactory.getLogger(HomerDAO.class);

    public Player findByExample(Player example) {
        Searcher<Player> searcher = new Searcher<Player>().findExample(example)
                .addSearcher(new PlayerSearchByPlayerId())
                .addSearcher(new PlayerSearchByMLBPlayerId())
                .addSearcher(new PlayerSearchByPlayerName());

        Connection connection = getConnection();
        Player player = null;
        try {
            player = searcher.search(connection);
        } catch (NoDataSearchMethodsProvidedException e) {
            LOG.error("No search methods provided", e);
        } finally {
            closeConnection(connection);
        }

        return player;
    }

    public boolean createPlayer(com.homer.mlb.Player mlbPlayer) {
        LOG.info("Creating player {}", mlbPlayer);
        return createPlayer(
                mlbPlayer.getName_display_first_last(),
                mlbPlayer.getPrimary_position(),
                mlbPlayer.getPlayer_id());
    }

    public boolean createPlayer(com.homer.fantasy.Player fantasyPlayer) {
        Long mlbPlayerId = null;
        if(fantasyPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB) != null) {
            mlbPlayerId =
                    fantasyPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId();
        }
        LOG.info("Creating player {}", fantasyPlayer);
        return createPlayer(
                fantasyPlayer.getPlayerName(),
                fantasyPlayer.getPrimaryPosition().getPositionId(),
                mlbPlayerId);
    }

    private boolean createPlayer(String playerName, int primaryPositionId, Long mlbPlayerId) {
        Connection connection = getConnection();
        PlayerCreator playerCreator = new PlayerCreator(connection);
        boolean success = playerCreator.create(
                playerName,
                primaryPositionId,
                mlbPlayerId);
        closeConnection(connection);
        return success;
    }

    public boolean updatePlayer(Player existingPlayer, Player newPlayer) {
        boolean success = false;
        newPlayer.setPlayerId(existingPlayer.getPlayerId());

        LOG.info("Updating player. Old : {}, New : {}", existingPlayer, newPlayer);

        Connection connection = getConnection();
        try {
            String sql = "update PLAYER " +
                    "set playerName=?, primaryPositionId=?, mlbPlayerId=? " +
                    "where playerId=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newPlayer.getPlayerName());
            statement.setInt(2, existingPlayer.getPrimaryPosition().getPositionId());
            Long mlbPlayerId = null;
            if(newPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB) != null) {
                mlbPlayerId =
                        newPlayer.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId();
            }
            if(mlbPlayerId != null) {
                statement.setLong(3, mlbPlayerId);
            } else {
                statement.setNull(3, Types.BIGINT);
            }
            statement.setLong(4, newPlayer.getPlayerId());

            int rowCount = statement.executeUpdate();
            if(rowCount > 0) {
                success = true;
            }

            statement.close();

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
            success = false;
        }

        closeConnection(connection);

        return success;
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        }catch(SQLException e) {
            LOG.error("SQL Exception closing connection", e);
        }
    }
}
