package com.homer.baseball;

import com.homer.dao.MySQLDAO;
import com.homer.dao.TypesFactory;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 1/31/15.
 */
public class ThirdPartyPlayerInfoTest {

    @Test
    public void testDo() {
        Player player = new Player();
        player.setPlayerId(1);
        player.setPlayerName("Mike Trout");
        player.setPrimaryPosition(Position.CENTERFIELD);
        ThirdPartyPlayerInfo thirdPartyPlayerInfo = new ThirdPartyPlayerInfo(player, 545361, ThirdPartyPlayerInfo.MLB);
        List<ThirdPartyPlayerInfo> thirdPartyPlayerInfoList = new ArrayList<ThirdPartyPlayerInfo>();
        thirdPartyPlayerInfoList.add(thirdPartyPlayerInfo);
        player.setThirdPartyPlayerInfoList(thirdPartyPlayerInfoList);

        DAO dao = new DAO();
        Player dbPlayer = dao.get();
        Assert.assertEquals(player, dbPlayer);
        Assert.assertEquals(player.getThirdPartyPlayerInfoList().get(0), dbPlayer.getThirdPartyPlayerInfoList().get(0));
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
