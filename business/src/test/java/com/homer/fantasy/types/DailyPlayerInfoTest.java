package com.homer.fantasy.types;

import com.homer.SportType;
import com.homer.fantasy.Player;
import com.homer.fantasy.dao.BaseballDAO;
import com.homer.fantasy.dao.MySQLDAO;
import com.homer.dao.TypesFactory;
import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.Position;
import com.homer.fantasy.Team;
import com.homer.fantasy.types.util.DBPreparer;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by arigolub on 1/29/15.
 */
public class DailyPlayerInfoTest {

    private DAO dao = new DAO();
    private DailyPlayerInfo dailyPlayerInfo;

    @BeforeClass
    public void prepare() throws Exception {

        Operation operation = Operations.sequenceOf(Operations.deleteAllFrom("PLAYERTOTEAM"));

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

        dailyPlayerInfo = createDailyPlayerInfo();
    }

    @Test
    public void testDo() {
        DAO dao = new DAO();
        DailyPlayerInfo dbDailyPlayerInfo = dao.get();

        Assert.assertEquals(dailyPlayerInfo, dbDailyPlayerInfo);
    }

    private static class DAO extends MySQLDAO {

//        public void save(Player player, DailyPlayerInfo dpi) {
//            Connection connection = getConnection();
//            try {
//                String sql = "insert into PLAYERTOTEAM"
//            }
//        }

        public DailyPlayerInfo get() {
            DailyPlayerInfo player = null;
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
                    Team fantasyTeam = TypesFactory.createTeam(rs, "fantasyTeam");
                    Team mlbTeam = TypesFactory.createTeam(rs, "mlbTeam");
                    player = new DailyPlayerInfo(
                        fantasyTeam,
                        mlbTeam,
                        rs.getDate("playerToTeam.gameDate"),
                        Position.get(rs.getInt("playerToTeam.fantasyPositionId")),
                        null
                    );
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

    private DailyPlayerInfo createDailyPlayerInfo() {
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
        DailyPlayerInfo dp = new DailyPlayerInfo();
        dp.setFantasyPosition(Position.FANTASYOUTFIELD);
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
        return dp;
    }
}
