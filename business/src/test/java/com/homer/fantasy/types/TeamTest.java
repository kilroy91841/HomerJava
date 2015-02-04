package com.homer.fantasy.types;

import com.homer.SportType;
import com.homer.fantasy.dao.MySQLDAO;
import com.homer.dao.TypesFactory;
import com.homer.fantasy.Team;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by arigolub on 1/29/15.
 */
public class TeamTest {

    @Test
    public void testDo() {
        Team team = new Team();
        team.setTeamId(1);
        team.setTeamName("Mark Loretta\'s Scars");
        team.setTeamType(SportType.FANTASY);
        team.setTeamCode("MLS");

        DAO dao = new DAO();
        Team dbTeam = dao.get();

        Assert.assertEquals(team, dbTeam);
    }

    private class DAO extends MySQLDAO {

        public Team get() {
            Team team = null;
            Connection connection = getConnection();
            try {

                String sql = "select * from TEAM team " +
                        "where team.teamId = 1 ";

                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();

                while(rs.next()) {
                    team = TypesFactory.createTeam(rs, "team");
                }

                rs.close();
                statement.close();
                connection.close();

            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
            }
            return team;
        }
    }
}
