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
 * Created by arigolub on 2/1/15.
 */
public class FreeAgentAuctionTest {

    @Test
    public void testDo() throws Exception {
        FreeAgentAuction freeAgentAuction = TestObjectFactory.getFreeAgentAuction();

        DAO dao = new DAO();
        FreeAgentAuction dbFreeAgentAuction = dao.get();
        Assert.assertEquals(freeAgentAuction, dbFreeAgentAuction);
    }

    public class DAO extends MySQLDAO {
        public FreeAgentAuction get() throws Exception {
            FreeAgentAuction freeAgentAuction = null;
            Connection connection = getConnection();
            try {
                String sql = "select * from FREEAGENTAUCTION freeAgentAuction, TEAM team, PLAYER player " +
                "where freeAgentAuction.requestingTeamId = team.teamId " +
                "and freeAgentAuction.playerId = player.playerId ";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();

                while(rs.next()) {
                    Team requestingTeam = TypesFactory.createTeam(rs, "team");
                    Player player = TypesFactory.createPlayer(rs, "player");
                    freeAgentAuction = new FreeAgentAuction(
                            requestingTeam,
                            player,
                            rs.getTimestamp("freeAgentAuction.createdDate"),
                            rs.getTimestamp("freeAgentAuction.modifiedDate"),
                            rs.getTimestamp("freeAgentAuction.deadline"),
                            FreeAgentAuction.Status.get(rs.getString("freeAgentAuction.status"))
                    );
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
            return freeAgentAuction;
        }
    }
}
