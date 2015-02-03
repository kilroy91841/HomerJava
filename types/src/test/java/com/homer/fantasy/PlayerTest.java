package com.homer.fantasy;

import com.homer.fantasy.factory.TestObjectFactory;
import com.homer.dao.MySQLDAO;
import com.homer.dao.TypesFactory;
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
        Player player = TestObjectFactory.getMikeTrout();

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
                    player = TypesFactory.createPlayer(rs, "player");
                }

                closeAll(rs, statement, connection);

            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
            }
            return player;
        }
    }
}
