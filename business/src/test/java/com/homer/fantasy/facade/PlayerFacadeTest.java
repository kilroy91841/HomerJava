package com.homer.fantasy.facade;

import com.homer.fantasy.util.DBPreparer;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Created by arigolub on 2/2/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerFacadeTest {

    private static PlayerFacade facade = new PlayerFacade();

    @BeforeClass
    public static void prepare() throws Exception {

        Operation operation = Operations.sequenceOf(Operations.deleteAllFrom("TRADE", "VULTURE", "PLAYERTOTEAM", "PLAYER"));

        System.out.println("deleting all players from player in db");

        DbSetup dbSetup = new DbSetup(DBPreparer.getDriverManagerDestination(), operation);
        dbSetup.launch();
    }

}
