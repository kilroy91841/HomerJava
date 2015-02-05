package com.homer.fantasy.dao.searcher.player;

import com.homer.fantasy.Player;
import com.homer.fantasy.ThirdPartyPlayerInfo;
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
                    "inner join POSITION position " +
                    "on position.positionId = player.primaryPositionId " +
                    "where player.mlbPlayerId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, example.getThirdPartyPlayerInfoByProvider(ThirdPartyPlayerInfo.MLB).getThirdPartyPlayerId());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                returnPlayer = Player.create(rs, "player");
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        return returnPlayer;
    }

}
