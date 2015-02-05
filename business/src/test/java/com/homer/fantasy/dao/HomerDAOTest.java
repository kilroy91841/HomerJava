package com.homer.fantasy.dao;

import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.types.factory.Seeder;
import com.homer.fantasy.types.util.DBPreparer;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by arigolub on 2/4/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HomerDAOTest {

    @BeforeClass
    public static void prepare() {
        Operation operation = Operations.sequenceOf(Operations.deleteAllFrom("PLAYERTOTEAM", "PLAYER"));

        System.out.println("PREPARING FOR CLASS HomerDAOTest");

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

        Seeder.seedTable("PLAYER");
    }

    @Test
    public void test1_save() {
        HomerDAO dao = new HomerDAO();
        Player player = dao.findByExample(new Player("Mike Trout"));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        boolean success = dao.createPlayerToTeam(player, cal.getTime(), 1, 108, "A", "A", Position.FANTASYOUTFIELD);
        Assert.assertTrue(success);

        Player dbPlayer = dao.findByExample(new Player("Mike Trout"));
        Assert.assertEquals(1, dbPlayer.getDailyPlayerInfoList().size());
    }

    @Test
    public void test2_update() {
        HomerDAO dao = new HomerDAO();
        Player player = dao.findByExample(new Player("Mike Trout"));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        boolean success = dao.updateDailyFantasyProperties(player, 2, "A", Position.FANTASYOUTFIELD);
        Assert.assertTrue(success);
    }

}
