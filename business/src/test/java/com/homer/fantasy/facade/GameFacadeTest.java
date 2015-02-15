package com.homer.fantasy.facade;

import com.homer.fantasy.facade.GameFacade;
import com.homer.fantasy.facade.PlayerFacade;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by arigolub on 2/15/15.
 */
public class GameFacadeTest {

    private static GameFacade facade;

    @BeforeClass
    public static void beforeClass() {
        facade = new GameFacade();
    }

    @Test
    public void createOrUpdateGame() {
        boolean retVal = facade.createOrUpdateGame(null);
        Assert.assertTrue(retVal);
    }
}
