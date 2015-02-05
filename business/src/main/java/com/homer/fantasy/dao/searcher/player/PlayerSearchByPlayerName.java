package com.homer.fantasy.dao.searcher.player;

import com.homer.PlayerStatus;
import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.searcher.DataSearchMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by arigolub on 2/2/15.
 */
public class PlayerSearchByPlayerName implements DataSearchMethod<Player> {

    @Override
    public boolean searchAllowed(Player example) {
        if(example.getPlayerName().equals(null) || example.getPlayerName().equals("")) {
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
                    "where player.playerName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, example.getPlayerName());
            ResultSet rs = statement.executeQuery();

            if(rs.first()) {
                returnPlayer = Player.create(rs, "player");

                rs.beforeFirst();
                while(rs.next()) {
                    Team fantasyTeam = Team.create(rs, "fantasyTeam");
                    Team mlbTeam = Team.create(rs, "mlbTeam");
                    DailyPlayerInfo info = new DailyPlayerInfo(fantasyTeam, mlbTeam,
                            rs.getDate("playerToTeam.gameDate"),
                            Position.get(rs.getInt("playerToTeam.fantasyPositionId")),
                            PlayerStatus.get(rs.getString("playerToTeam.fantasyPlayerStatusCode")),
                            PlayerStatus.get(rs.getString("playerToTeam.mlbPlayerStatusCode")),
                            null
                    );
                    returnPlayer.addDailyPlayerInfoList(info);
                }
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnPlayer;
    }

}
