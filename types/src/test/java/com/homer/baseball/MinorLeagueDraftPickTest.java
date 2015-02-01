package com.homer.baseball;

import com.homer.SportType;
import com.homer.baseball.factory.TestObjectFactory;
import com.homer.dao.MySQLDAO;
import com.homer.dao.TypesFactory;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

/**
 * Created by arigolub on 1/31/15.
 */
public class MinorLeagueDraftPickTest {

    @Test
    public void testSelectedPick() {
        Team team = new Team(1, "Mark Loretta\'s Scars", SportType.FANTASY, "MLS");
        Player player = new Player(1, "Mike Trout", Position.CENTERFIELD);
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, 2014);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 12);
        MinorLeagueDraftPick minorLeagueDraftPick = new MinorLeagueDraftPick(team, 2014, 1, team, 7, player, cal.getTime(), false);

        DAO dao = new DAO();
        MinorLeagueDraftPick dbMinorLeagueDraftPick = dao.getSelectedPick();
        Assert.assertEquals(minorLeagueDraftPick, dbMinorLeagueDraftPick);
    }

    @Test
    public void testFuturePick() {
        MinorLeagueDraftPick minorLeagueDraftPick = TestObjectFactory.getFutureDraftPick();

        DAO dao = new DAO();
        MinorLeagueDraftPick dbMinorLeagueDraftPick = dao.getFuturePick();
        Assert.assertEquals(minorLeagueDraftPick, dbMinorLeagueDraftPick);
    }

    public class DAO extends MySQLDAO {

        public MinorLeagueDraftPick getSelectedPick() {
            String sql = "select * from TEAM owningTeam, TEAM originalTeam, MINORLEAGUEDRAFTPICK minorLeagueDraftPick " +
                    "left join PLAYER player " +
                    "on player.playerId = minorLeagueDraftPick.playerId " +
                    "where owningTeam.teamId = minorLeagueDraftPick.owningTeamId " +
                    "and originalTeam.teamId = minorLeagueDraftPick.originalTeamId " +
                    "and minorLeagueDraftPick.originalTeamId = 1 " +
                    "and minorLeagueDraftPick.season = 2014 " +
                    "and minorLeagueDraftPick.round = 1 ";
            return get(sql);
        }
        public MinorLeagueDraftPick getFuturePick() {
            String sql = "select * from TEAM owningTeam, TEAM originalTeam, MINORLEAGUEDRAFTPICK minorLeagueDraftPick " +
                    "left join PLAYER player " +
                    "on player.playerId = minorLeagueDraftPick.playerId " +
                    "where owningTeam.teamId = minorLeagueDraftPick.owningTeamId " +
                    "and originalTeam.teamId = minorLeagueDraftPick.originalTeamId " +
                    "and minorLeagueDraftPick.originalTeamId = 1 " +
                    "and minorLeagueDraftPick.season = 2015 " +
                    "and minorLeagueDraftPick.round = 1 ";
            return get(sql);
        }

        private MinorLeagueDraftPick get(String sql) {
            MinorLeagueDraftPick minorLeagueDraftPick = null;
            Connection connection = getConnection();
            try {



                PreparedStatement statement = connection.prepareStatement(sql);
                System.out.println(statement.toString());
                ResultSet rs = statement.executeQuery();

                while (rs.next()) {
                    Team originalTeam = TypesFactory.createTeam(rs, "originalTeam");
                    Team owningTeam = TypesFactory.createTeam(rs, "owningTeam");
                    Player player = TypesFactory.createPlayer(rs, "player");
                    Integer overall = rs.getInt("minorLeagueDraftPick.overall");
                    if(rs.wasNull()) {
                        overall = null;
                    }
                    minorLeagueDraftPick = new MinorLeagueDraftPick(
                            originalTeam,
                            rs.getInt("minorLeagueDraftPick.season"),
                            rs.getInt("minorLeagueDraftPick.round"),
                            owningTeam,
                            overall,
                            player, rs.getTimestamp("minorLeagueDraftPick.deadline"),
                            rs.getBoolean("minorLeagueDraftPick.skipped")
                    );
                }
            } catch(Exception e) {
                e.printStackTrace();
            }

            return minorLeagueDraftPick;
        }

    }
}
