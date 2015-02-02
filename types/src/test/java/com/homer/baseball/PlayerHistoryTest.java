package com.homer.baseball;

import com.homer.SportType;
import com.homer.dao.BaseballDAO;
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
public class PlayerHistoryTest {

    @Test
    public void testDo() {
        Team team = new Team();
        team.setTeamId(1);
        team.setTeamName("Mark Loretta\'s Scars");
        team.setTeamType(SportType.FANTASY);
        team.setTeamCode("MLS");
        PlayerHistory history = new PlayerHistory(2012, 0, 0, false, team, null);

        DAO dao = new DAO();
        PlayerHistory dbHistory = dao.get();
        Assert.assertEquals(history, dbHistory);
    }

    private class DAO extends MySQLDAO {

        public PlayerHistory get() {
            PlayerHistory playerHistory = null;
            Connection connection = getConnection();
            try {

                

                String sql = "select * from PLAYER player, PLAYERHISTORY history " +
                    "left join TEAM draftTeam " +
                    "on draftTeam.teamId = history.draftTeamId " +
                    "left join TEAM keeperTeam " +
                    "on keeperTeam.teamId = history.keeperTeamId " +
                    "where player.playerId = history.playerId " +
                    "and player.playerId = 1 " +
                    "and history.season = 2012";
                PreparedStatement statement = connection.prepareStatement(sql);
                System.out.println(statement.toString());
                ResultSet rs = statement.executeQuery();

                while(rs.next()) {
                    playerHistory = TypesFactory.createPlayerHistory(rs, "history");
                }

                rs.close();
                statement.close();
                connection.close();

            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
            }
            return playerHistory;
        }
    }
}
