package com.homer.fantasy.dao.creator;

import com.homer.dao.MySQLDAO;
import com.homer.dao.TypesFactory;
import com.homer.dao.response.PlayerResponse;
import com.homer.fantasy.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by arigolub on 2/2/15.
 */
public class PlayerCreator extends MySQLDAO {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerCreator.class);

    public PlayerResponse create(com.homer.mlb.Player mlbPlayer) {
        PlayerResponse response = new PlayerResponse();

        Connection connection = getConnection();
        try {

            String sql = "insert into PLAYER (playerName, primaryPositionId, mlbPlayerId)" +
                    "values (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, mlbPlayer.getName_display_first_last());
            statement.setInt(2, mlbPlayer.getPrimary_position());
            statement.setLong(3, mlbPlayer.getPlayer_id());

            int rowCount = statement.executeUpdate();
            if(rowCount > 0) {
                response.setStatus(PlayerResponse.SUCCESS);
            } else {
                response.setStatus(PlayerResponse.ERROR);
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            String errorMessage = "Something went wrong talking to the database";
            LOG.error(errorMessage, e);
            response.setErrorMesage(errorMessage);
            response.setException(e);
            response.setStatus(PlayerResponse.RUNTIME_EXCEPTION);
        }
        return response;
    }
}
