package com.homer.fantasy.dao.creator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by arigolub on 2/3/15.
 */
public class DailyPlayerCreator {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerCreator.class);
    private Connection connection;

    public DailyPlayerCreator(Connection connection) {
        this.connection = connection;
    }

    public boolean createOrUpdate(Long playerId, Date gameDate, Integer fantasyTeamId, Integer mlbTeamId,
                                 Integer fantasyPlayerStatusId, Integer mlbPlayerStatusId, Integer fantasyPositionId) {
        boolean success = false;
        try {

            String sql = "insert into PLAYERTOTEAM " +
                    "(playerId, gameDate, fantasyTeamId, mlbTeamId, fantasyPlayerStatusId, mlbPlayerStatusId, fantasyPositionId) " +
                    "values (?, ?, ?, ?, ?, ?, ?) " +
                    "on duplicate key update " +
                    "fantasyTeamId=values(fantasyTeamId), " +
                    "mlbTeamId=values(mlbTeamId), " +
                    "fantasyPlayerStatusId=values(fantasyPlayerStatusId), " +
                    "mlbPlayerStatusId=values(mlbPlayerStatusId), " +
                    "fantasyPositionId=values(fantasyPositionId)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, playerId);
            statement.setDate(2, new java.sql.Date(gameDate.getTime()));
            statement.setInt(3, fantasyTeamId);
            statement.setInt(4, mlbTeamId);
            statement.setInt(5, fantasyPlayerStatusId);
            statement.setInt(6, mlbPlayerStatusId);
            statement.setInt(7, fantasyPositionId);

            int rowCount = statement.executeUpdate();
            if(rowCount > 0) {
                success = true;
            }

            statement.close();

        } catch (SQLException e) {
            String errorMessage = "Something went wrong talking to the database";
            LOG.error(errorMessage, e);
        }

        return success;
    }
}
