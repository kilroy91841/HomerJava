package com.homer.fantasy.dao.searcher.player;

import com.homer.PlayerStatus;
import com.homer.fantasy.*;
import com.homer.fantasy.dao.searcher.DataSearchMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by arigolub on 2/2/15.
 */
public class PlayerSearchByMLBPlayerId implements DataSearchMethod<Player> {

    @Override
    public boolean searchAllowed(Player example) {
        ThirdPartyPlayerInfo info = example.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB);
        if(info == null || info.getThirdPartyPlayerId() == null) {
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
                    "where player.mlbPlayerId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, example.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId());
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
