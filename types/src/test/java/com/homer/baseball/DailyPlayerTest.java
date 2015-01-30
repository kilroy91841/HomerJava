package com.homer.baseball;

import com.homer.SportType;
import com.homer.dao.MySQLDAO;
import junit.framework.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by arigolub on 1/29/15.
 */
public class DailyPlayerTest {

    @Test
    public void testDo() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.setTimeZone(TimeZone.getDefault());
        DailyPlayer dp = new DailyPlayer();
        dp.setPrimaryPosition(Position.CENTERFIELD);
        dp.setFantasyPosition(Position.FANTASYOUTFIELD);
        dp.setPlayerName("Mike Trout");
        dp.setPlayerId(1);
        dp.setDate(cal.getTime());
        Team mlbTeam = new Team();
        mlbTeam.setTeamCode("LAA");
        mlbTeam.setTeamType(SportType.MLB);
        mlbTeam.setTeamName("Los Angeles Angels");
        mlbTeam.setTeamId(108);
        Team fantasyTeam = new Team();
        fantasyTeam.setTeamCode("MLS");
        fantasyTeam.setTeamType(SportType.FANTASY);
        fantasyTeam.setTeamName("Mark Loretta\'s Scars");
        fantasyTeam.setTeamId(1);
        dp.setMlbTeam(mlbTeam);
        dp.setFantasyTeam(fantasyTeam);

        DAO dao = new DAO();
        DailyPlayer dbDailyPlayer = dao.get();

        Assert.assertEquals(dp.getDate(), dbDailyPlayer.getDate());
        Assert.assertEquals(dp, dbDailyPlayer);
    }

    private class DAO extends MySQLDAO {

        public DailyPlayer get() {
            DailyPlayer player = null;
            Connection connection = getConnection();
            try {

                String sql = "select * from PLAYER player, TEAM fantasyTeam, TEAM mlbTeam, PLAYERTOTEAM playerToTeam, POSITION position " +
                        "where player.playerId = playerToTeam.playerId " +
                        "and fantasyTeam.teamId = playerToTeam.fantasyTeamId " +
                        "and mlbTeam.teamId = playerToTeam.mlbTeamId " +
                        "and position.positionId = playerToTeam.fantasyPositionId " +
                        "and player.playerId = 1 " +
                        "and playerToTeam.gameDate = '2015-01-01'";

                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();

                while(rs.next()) {
                    player = new DailyPlayer(rs);
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
