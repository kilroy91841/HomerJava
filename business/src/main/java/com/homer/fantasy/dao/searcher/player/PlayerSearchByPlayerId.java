package com.homer.fantasy.dao.searcher.player;

import com.homer.fantasy.Player;
import com.homer.fantasy.ThirdPartyPlayerInfo;
import com.homer.dao.MySQLDAO;
import com.homer.dao.TypesFactory;
import com.homer.fantasy.dao.searcher.DataSearchMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by arigolub on 2/2/15.
 */
public class PlayerSearchByPlayerId extends MySQLDAO implements DataSearchMethod<Player> {


    @Override
    public boolean searchAllowed(Player example) {
        if(example.getPlayerId() == null) {
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
                    "where player.playerId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, example.getPlayerId());
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                returnPlayer = TypesFactory.createPlayer(rs, "player");
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
