package com.homer.fantasy.dao.creator;

import com.homer.fantasy.dao.MySQLDAO;
import com.homer.dao.response.PlayerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by arigolub on 2/2/15.
 */
public class PlayerCreator {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerCreator.class);

    private Connection connection;

    public PlayerCreator(Connection connection) {
        this.connection = connection;
    }

    public boolean create(String playerName, int primaryPositionId, Long mlbPlayerId) {
        boolean success = false;

        try {
            String sql = "insert into PLAYER (playerName, primaryPositionId, mlbPlayerId)" +
                    "values (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, playerName);
            statement.setInt(2, primaryPositionId);
            if(mlbPlayerId != null) {
                statement.setLong(3, mlbPlayerId);
            } else {
                statement.setNull(3, Types.BIGINT);
            }

            int rowCount = statement.executeUpdate();
            if(rowCount > 0) {
                success = true;
            }

            statement.close();

        } catch (SQLException e) {
            LOG.error("Something went wrong talking to the database", e);
            success = false;
        }

        return success;
    }
}
