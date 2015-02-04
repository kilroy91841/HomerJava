package com.homer.fantasy.types;

import com.homer.fantasy.Player;
import com.homer.fantasy.dao.BaseballDAO;
import com.homer.fantasy.types.factory.TestObjectFactory;
import com.homer.fantasy.dao.MySQLDAO;
import com.homer.dao.TypesFactory;
import com.homer.fantasy.types.util.DBPreparer;
import com.homer.util.PropertyRetriever;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by arigolub on 1/29/15.
 */
public class PlayerTest {

    private DAO dao;

    @BeforeClass
    public void prepare() {

        Operation operation = Operations.sequenceOf(Operations.deleteAllFrom("PLAYER"));

        System.out.println("deleting all players from player in db ");

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

        dao = new DAO();
    }

    @Test
    public void testCreate() {
        Player player = TestObjectFactory.getMikeTrout();

        DAO dao = new DAO();
        Player dbPlayer = dao.get();
        Assert.assertEquals(player, dbPlayer);
    }

    @Test
    public void testUpdate() {

    }

    @Test
    public void testFind() {

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
