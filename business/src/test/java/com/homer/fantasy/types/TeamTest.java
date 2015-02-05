package com.homer.fantasy.types;

import com.homer.SportType;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.dao.MySQLDAO;
import com.homer.fantasy.Team;
import com.homer.fantasy.facade.PlayerFacade;
import com.homer.fantasy.types.factory.TestObjectFactory;
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
import java.util.List;

/**
 * Created by arigolub on 1/29/15.
 */
public class TeamTest {

    private static HomerDAO dao;

    @BeforeClass
    public static void prepare() throws Exception {

        Operation operation = Operations.sequenceOf(Operations.deleteAllFrom("VULTURE"));

        System.out.println("Preparing for class TeamTest");

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

        dao = new HomerDAO();
    }

    @Test
    public void saveAndFind() {
        List<Team> teams = dao.getTeams();
        Assert.assertEquals(32, teams.size());
        for(Team t : teams) {
            Assert.assertNotNull(t.getTeamName());
            Assert.assertNotNull(t.getTeamCode());
            Assert.assertNotNull(t.getTeamType());
        }
    }
}
