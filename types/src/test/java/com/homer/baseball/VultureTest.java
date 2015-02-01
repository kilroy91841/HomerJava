package com.homer.baseball;

import com.homer.baseball.factory.TestObjectFactory;
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
public class VultureTest {

    @Test
    public void testDo() throws Exception {
        Vulture vulture = TestObjectFactory.getVulture();

        DAO dao = new DAO();
        Vulture dbVulture = dao.get();
        Assert.assertEquals(vulture, dbVulture);
    }

    public class DAO extends MySQLDAO {

        public Vulture get() throws Exception {
            Vulture vulture = null;
            Connection connection = getConnection();
            try {
                String sql = "select * from VULTURE vulture, TEAM vulturingTeam, TEAM offendingTeam, PLAYER player " +
                "where vulture.vulturingTeamId = vulturingTeam.teamId " +
                "and vulture.offendingTeamId = offendingTeam.teamId " +
                "and player.playerId = vulture.playerId ";

                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();

                while(rs.next()) {
                    Team vulturingTeam = TypesFactory.createTeam(rs, "vulturingTeam");
                    Team offendingTeam = TypesFactory.createTeam(rs, "offendingTeam");
                    Player player = TypesFactory.createPlayer(rs, "player");
                    vulture = new Vulture(
                        vulturingTeam,
                        offendingTeam,
                        player,
                        rs.getTimestamp("vulture.deadline"),
                        Vulture.VultureStatus.get(rs.getString("vulture.vultureStatus"))
                    );
                }

                closeAll(rs, statement, connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return vulture;
        }
    }
}
