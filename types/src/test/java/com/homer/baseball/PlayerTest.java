package com.homer.baseball;

import com.homer.dao.MySQLDAO;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by arigolub on 1/29/15.
 */
public class PlayerTest {

    @Test
    public void testDo() {
        Player player = new Player();
        player.setPlayerId(1);
        player.setPlayerName("Mike Trout");
        player.setPrimaryPosition(Position.CENTERFIELD);

        DAO dao = new DAO();
        Player dbPlayer = dao.get();
        Assert.assertEquals(player, dbPlayer);
    }

    private class DAO extends MySQLDAO {

        public Player get() {
            Player player = null;
            Connection connection = getConnection();
            try {

                String sql = "select * from PLAYER player " +
                        "where player.playerId = 1 ";

                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();

                while(rs.next()) {
                    player = new Player(rs);
                }

                rs.close();
                statement.close();
                connection.close();

            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
            }
            return player;
        }
    }
}
