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

        Operation operation = Operations.sequenceOf(Operations.deleteAllFrom("VULTURE", "TEAM"));

        System.out.println("Preparing for class TeamTest");

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

        dao = new HomerDAO();
    }

    @Test
    public void saveAndFind() {
        Team mls = TestObjectFactory.getMarkLorettasScars();

        dao.createTeam(mls);

        List<Team> teams = dao.getTeams();
        Assert.assertEquals(1, teams.size());
        Assert.assertEquals(mls, teams.get(0));
    }

    public void seed() {
        dao = new HomerDAO();

        Team mls = TestObjectFactory.getMarkLorettasScars();
        Team snxx = TestObjectFactory.getBSnaxx();
        Team yankees = TestObjectFactory.getYankees();

        dao.createTeam(mls);
        dao.createTeam(snxx);
        dao.createTeam(yankees);
    }
}
