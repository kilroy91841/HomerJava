package com.homer.fantasy.facade;

import com.homer.fantasy.facade.GameFacade;
import com.homer.fantasy.facade.StatsFacade;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by arigolub on 2/15/15.
 */
public class StatsFacadeTest {

    private static StatsFacade facade;

    @BeforeClass
    public static void beforeClass() {
        facade = new StatsFacade();
    }

    @Test
    public void createOrUpdateStats() {
        boolean retVal = facade.createOrUpdateStats(null);
        Assert.assertTrue(retVal);
    }
}
