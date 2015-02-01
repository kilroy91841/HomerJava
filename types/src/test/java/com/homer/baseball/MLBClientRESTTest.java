package com.homer.baseball;

import com.homer.mlb.MLBClientREST;
import com.homer.mlb.Player;
import com.homer.mlb.Stats;
import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by arigolub on 2/1/15.
 */
public class MLBClientRESTTest {

    @Test
    public void doPlayerTest() {
        MLBClientREST client = new MLBClientREST();
        Player player = client.getPlayer(new Long(545361));
        Assert.assertNotNull(player);
        System.out.println(player);
    }

    @Test
    public void doStatsTest() {
        MLBClientREST client = new MLBClientREST();
        List<Stats> stats = client.getStats(new Long(545361));
        Assert.assertNotNull(stats);
        Assert.assertTrue(stats.size() > 0);
        System.out.println(stats);
    }

    @Test
    public void doRosterTest() {
        MLBClientREST client = new MLBClientREST();
        List<Player> roster = client.get40ManRoster(147);
        Assert.assertNotNull(roster);
        Assert.assertEquals(40, roster.size());
        for(int i = 0; i < roster.size(); i++) {
            System.out.println(roster.get(i));
        }
    }
}
