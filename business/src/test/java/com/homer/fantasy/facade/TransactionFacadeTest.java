package com.homer.fantasy.facade;

import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.dao.HomerDAO;
import com.homer.fantasy.types.factory.Seeder;
import com.homer.fantasy.types.util.DBPreparer;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by golub on 2/5/15.
 */
public class TransactionFacadeTest {

    private static TransactionFacade facade = new TransactionFacade();

    @BeforeClass
    public static void prepare() throws Exception {

        Operation operation = Operations.sequenceOf(Operations.deleteAllFrom("TRADE", "VULTURE", "PLAYERTOTEAM", "PLAYER"));

        System.out.println("deleting all players from player in db");

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

        Seeder.seedTable("PLAYER");
    }

    @Test
    public void doTransactionTest() throws Exception {
        HomerDAO dao =  new HomerDAO();

        Player player = dao.findByExample(new Player("Mike Trout"));
        Assert.assertEquals(1, player.getDailyPlayerInfoList().size());
        Assert.assertEquals(1, (int)player.getCurrentFantasyTeam().getTeamId());
        boolean success = facade.addFreeAgent(545361L, 147, Position.FANTASYCORNERINFIELD);
        player = dao.findByExample(player);
        Assert.assertEquals(1, player.getDailyPlayerInfoList().size());
        Assert.assertEquals(147, (int)player.getCurrentFantasyTeam().getTeamId());
    }
}
