package com.homer.fantasy.facade;

import com.homer.fantasy.MinorLeagueDraftPick;
import com.homer.fantasy.Team;
import com.homer.fantasy.dao.impl.MockMinorLeagueDraftPickDAO;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by arigolub on 2/25/15.
 */
public class MinorLeagueDraftPickFacadeTest {

    private static final MinorLeagueDraftPickFacade facade = new MinorLeagueDraftPickFacade();

    private static Team team1 = new Team(1);
    private static Team team2 = new Team(2);
    private static MinorLeagueDraftPick team1Round1Pick;
    private static MinorLeagueDraftPick team1Round2Pick;
    private static MinorLeagueDraftPick team2Round1Pick;
    private static MinorLeagueDraftPick team2Round2Pick;
    private static int season = 2015;

    @BeforeClass
    public static void setup() {
        team1Round1Pick = new MinorLeagueDraftPick();
        team1Round1Pick.setOriginalTeam(team1);
        team1Round1Pick.setSeason(season);
        team1Round1Pick.setRound(1);
        team1Round2Pick = new MinorLeagueDraftPick();
        team1Round2Pick.setOriginalTeam(team1);
        team1Round2Pick.setSeason(season);
        team1Round2Pick.setRound(2);
        team2Round1Pick = new MinorLeagueDraftPick();
        team2Round1Pick.setOriginalTeam(team2);
        team2Round1Pick.setSeason(season);
        team2Round1Pick.setRound(1);
        team2Round2Pick = new MinorLeagueDraftPick();
        team2Round2Pick.setOriginalTeam(team2);
        team2Round2Pick.setSeason(season);
        team2Round2Pick.setRound(2);

        MockMinorLeagueDraftPickDAO.addMinorLeagueDraftPick(team1Round1Pick);
        MockMinorLeagueDraftPickDAO.addMinorLeagueDraftPick(team1Round2Pick);
        MockMinorLeagueDraftPickDAO.addMinorLeagueDraftPick(team2Round1Pick);
        MockMinorLeagueDraftPickDAO.addMinorLeagueDraftPick(team2Round2Pick);
    }

    @Test
    public void transfer() {
        MinorLeagueDraftPick transferredPick = facade.transferMinorLeagueDraftPick(team1Round1Pick, team2);

        Assert.assertNotNull(transferredPick);
        Assert.assertEquals(1, (int)transferredPick.getOriginalTeam().getTeamId());
        Assert.assertEquals(2, (int)transferredPick.getOwningTeam().getTeamId());
    }
}
