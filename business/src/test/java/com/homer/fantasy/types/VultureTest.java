package com.homer.fantasy.types;

import com.homer.fantasy.Player;
import com.homer.fantasy.Team;
import com.homer.fantasy.Vulture;
import com.homer.fantasy.dao.BaseballDAO;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.types.factory.Seeder;
import com.homer.fantasy.types.factory.TestObjectFactory;
import com.homer.fantasy.dao.MySQLDAO;
import com.homer.fantasy.types.util.DBPreparer;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by arigolub on 2/1/15.
 */
public class VultureTest {

    private static HomerDAO dao;

    @BeforeClass
    public static void prepare() throws Exception {

        Operation operation = Operations.sequenceOf(Operations.deleteAllFrom("VULTURE"));

        System.out.println("Preparing db for test VultureTest");

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

        Seeder.seedTable("PLAYER");
        Seeder.seedTable("TEAM");

        dao = new HomerDAO();
    }

    @Test
    public void saveAndFind() throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 3);
        cal.set(Calendar.HOUR, 16);
        Vulture vulture = new Vulture();

        Player player = new Player();
        player.setPlayerName("Mike Trout");
        player = dao.findByExample(player);

        Team offendingTeam = dao.getTeamByName("Mark Loretta\'s Scars");
        Team vulturingTeam = dao.getTeamByName("BSnaxx Cracker Jaxx");

        vulture.setPlayer(player);
        vulture.setDeadline(cal.getTime());
        vulture.setStatus(Vulture.ACTIVE);
        vulture.setOffendingTeam(offendingTeam);
        vulture.setVulturingTeam(vulturingTeam);

        dao.saveVulture(vulture);

        List<Vulture> vultures = dao.getVultures();
        Assert.assertEquals(1, vultures.size());
        Assert.assertEquals(vulture, vultures.get(0));
    }
}
