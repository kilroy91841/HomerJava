package com.homer.fantasy.types;

import com.homer.fantasy.dao.MySQLDAO;
import com.homer.dao.TypesFactory;
import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.ThirdPartyPlayerInfo;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by arigolub on 1/31/15.
 */
public class ThirdPartyPlayerInfoTest {

    @Test
    public void testDo() {
        Player player = new Player();
        player.setPlayerId(new Long(1));
        player.setPlayerName("Mike Trout");
        player.setPrimaryPosition(Position.CENTERFIELD);
        ThirdPartyPlayerInfo thirdPartyPlayerInfo = new ThirdPartyPlayerInfo(545361, ThirdPartyPlayerInfo.MLB);
        Set<ThirdPartyPlayerInfo> thirdPartyPlayerInfoList = new HashSet<ThirdPartyPlayerInfo>();
        thirdPartyPlayerInfoList.add(thirdPartyPlayerInfo);
        player.setThirdPartyPlayerInfoSet(thirdPartyPlayerInfoList);

        DAO dao = new DAO();
        Player dbPlayer = dao.get();
        Assert.assertEquals(player, dbPlayer);
        //Assert.assertEquals(player.getThirdPartyPlayerInfoSet()., dbPlayer.getThirdPartyPlayerInfoSet().get(0));
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
