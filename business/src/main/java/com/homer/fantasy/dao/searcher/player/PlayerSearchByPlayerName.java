package com.homer.fantasy.dao.searcher.player;

import com.homer.PlayerStatus;
import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.parser.PlayerParser;
import com.homer.fantasy.dao.searcher.DataSearchMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by arigolub on 2/2/15.
 */
public class PlayerSearchByPlayerName implements DataSearchMethod<Player> {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerSearchByPlayerName.class);

    @Override
    public boolean searchAllowed(Player example) {
        if(example.getPlayerName() == null || example.getPlayerName().equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public Player find(Player example, Connection connection) {
        Player returnPlayer = null;

        try {

            String sql = "select * from PLAYER player " +
                    "left join PLAYERTOTEAM playerToTeam " +
                    "on player.playerId = playerToTeam.playerId " +
                    "left join TEAM fantasyTeam " +
                    "on playerToTeam.fantasyTeamId = fantasyTeam.teamId " +
                    "left join TEAM mlbTeam " +
                    "on playerToTeam.mlbTeamId = mlbTeam.teamId " +
                    "where player.playerName = ? " +
                    "order by playerToTeam.gameDate desc";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, example.getPlayerName());
            ResultSet rs = statement.executeQuery();

            returnPlayer = PlayerParser.create(rs);

            rs.close();
            statement.close();

        } catch (SQLException e) {
            LOG.error("Error talking to database", e);
        } catch (Exception e) {
            LOG.error("Error", e);
        }

        return returnPlayer;
    }

}
