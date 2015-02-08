package com.homer.fantasy.facade;

import com.homer.fantasy.Player;
import com.homer.fantasy.Position;
import com.homer.fantasy.ThirdPartyPlayerInfo;
import com.homer.fantasy.util.DBPreparer;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;

/**
 * Created by golub on 2/5/15.
 */
public class TransactionFacadeTest {

    private Calendar cal;
    private TransactionFacade facade = new TransactionFacade();

    @Before
    public void prepare() throws Exception {

        Operation operation = Operations.sequenceOf(
                Operations.deleteAllFrom("PLAYERTOTEAM", "PLAYER"),
                Operations.sql("alter table PLAYER AUTO_INCREMENT=1"),
                Operations.sql("insert into PLAYER (playerName, primaryPositionId, mlbPlayerId, espnPlayerId) " +
                        "values (\"Mike Trout\", 8, 100, 200)"),
                Operations.sql("insert into PLAYERTOTEAM (playerId, gameDate) values (1, \"2015-02-08\")")
        );

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

        cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DATE, 8);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    @Test
    public void addFreeAgent() {
        Operation operation = Operations.sequenceOf(
                Operations.deleteAllFrom("PLAYERTOTEAM"),
                Operations.sql("insert into PLAYERTOTEAM (playerId, gameDate) values (1, \"2015-02-08\")")
        );

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

        Player player = new Player();
        player.addThirdPartyPlayerInfo(new ThirdPartyPlayerInfo(200L, ThirdPartyPlayerInfo.ESPN));

        try {
            boolean success = facade.addFreeAgent(player, 1, Position.FANTASYOUTFIELD);
            Assert.assertTrue(success);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void dropPlayer() {
        Operation operation = Operations.sequenceOf(
                Operations.deleteAllFrom("PLAYERTOTEAM"),
                Operations.sql("insert into PLAYERTOTEAM (playerId, gameDate, fantasyTeamId) values (1, \"2015-02-08\", 1)")
        );

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();

        Player player = new Player();
        player.addThirdPartyPlayerInfo(new ThirdPartyPlayerInfo(200L, ThirdPartyPlayerInfo.ESPN));

        try {
            boolean success = facade.dropPlayer(player);
            Assert.assertTrue(success);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
