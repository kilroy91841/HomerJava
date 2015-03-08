package com.homer.fantasy.standings;

import com.homer.fantasy.Team;
import junit.framework.Assert;
import org.junit.Test;
import com.homer.fantasy.standings.Standings.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
* Created by arigolub on 3/1/15.
*/
public class StandingsTest {

    private Team team1 = new Team(1);
    private Team team2 = new Team(2);
    private Team team3 = new Team(3);
    private Team team4 = new Team(4);
    private Team team5 = new Team(5);
    private Team team6 = new Team(6);

    @Test
    public void calculateStandings() {
        TeamStandings teamStandings1 = new TeamStandings(team1);
        TeamStandings teamStandings2 = new TeamStandings(team2);
        TeamStandings teamStandings3 = new TeamStandings(team3);
        TeamStandings teamStandings4 = new TeamStandings(team4);
        TeamStandings teamStandings5 = new TeamStandings(team5);
        TeamStandings teamStandings6 = new TeamStandings(team6);

        List<List<TeamStandingsCategory>> listOfTeamStandingsCategories = new ArrayList<List<TeamStandingsCategory>>();

        List<TeamStandingsCategory> wins = new ArrayList<TeamStandingsCategory>();
        wins.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(6.0).withTeamStandings(teamStandings1));
        wins.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(5.0).withTeamStandings(teamStandings2));
        wins.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(4.0).withTeamStandings(teamStandings3));
        wins.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(3.0).withTeamStandings(teamStandings4));
        wins.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(2.0).withTeamStandings(teamStandings5));
        wins.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(1.0).withTeamStandings(teamStandings6));

        listOfTeamStandingsCategories.add(wins);

        List<TeamStandingsCategory> runs = new ArrayList<TeamStandingsCategory>();
        runs.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings1));
        runs.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings2));
        runs.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings3));
        runs.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings4));
        runs.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings5));
        runs.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings6));

        listOfTeamStandingsCategories.add(runs);

        List<TeamStandings> standingsTeamList = Standings.calculate(listOfTeamStandingsCategories);

        Assert.assertEquals(6, standingsTeamList.size());
        Assert.assertEquals(1, (int)standingsTeamList.get(0).getTeam().getTeamId());
        Assert.assertEquals(9.5, standingsTeamList.get(0).getTotalPoints());
        Assert.assertEquals(2, (int)standingsTeamList.get(1).getTeam().getTeamId());
        Assert.assertEquals(8.5, standingsTeamList.get(1).getTotalPoints());
        Assert.assertEquals(3, (int)standingsTeamList.get(2).getTeam().getTeamId());
        Assert.assertEquals(7.5, standingsTeamList.get(2).getTotalPoints());
        Assert.assertEquals(4, (int)standingsTeamList.get(3).getTeam().getTeamId());
        Assert.assertEquals(6.5, standingsTeamList.get(3).getTotalPoints());
        Assert.assertEquals(5, (int)standingsTeamList.get(4).getTeam().getTeamId());
        Assert.assertEquals(5.5, standingsTeamList.get(4).getTotalPoints());
        Assert.assertEquals(6, (int)standingsTeamList.get(5).getTeam().getTeamId());
        Assert.assertEquals(4.5, standingsTeamList.get(5).getTotalPoints());

        List<List<TeamStandingsCategory>> listOfTeamStandingsCategories2 = new ArrayList<List<TeamStandingsCategory>>();

        List<TeamStandingsCategory> wins2 = new ArrayList<TeamStandingsCategory>();
        wins2.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(6.0).withTeamStandings(teamStandings1));
        wins2.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(5.0).withTeamStandings(teamStandings2));
        wins2.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(4.0).withTeamStandings(teamStandings3));
        wins2.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(3.0).withTeamStandings(teamStandings4));
        wins2.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(2.0).withTeamStandings(teamStandings5));
        wins2.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(1.0).withTeamStandings(teamStandings6));

        listOfTeamStandingsCategories2.add(wins2);

        List<TeamStandingsCategory> runs2 = new ArrayList<TeamStandingsCategory>();
        runs2.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(4.0).withTeamStandings(teamStandings1));
        runs2.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings2));
        runs2.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings3));
        runs2.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings4));
        runs2.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings5));
        runs2.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings6));

        listOfTeamStandingsCategories2.add(runs2);

        List<TeamStandings> standingsDay2 = Standings.calculate(listOfTeamStandingsCategories2);

        Standings.calculateDifference(standingsTeamList, standingsDay2);

        listOfTeamStandingsCategories = new ArrayList<List<TeamStandingsCategory>>();

        wins = new ArrayList<TeamStandingsCategory>();
        wins.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(6.0).withTeamStandings(teamStandings1));
        wins.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(6.0).withTeamStandings(teamStandings2));
        wins.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(6.0).withTeamStandings(teamStandings3));
        wins.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(3.0).withTeamStandings(teamStandings4));
        wins.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(2.0).withTeamStandings(teamStandings5));
        wins.add(new TeamStandingsCategory(StandingsCategory.WINS).withCategoryAmount(1.0).withTeamStandings(teamStandings6));

        listOfTeamStandingsCategories.add(wins);

        runs = new ArrayList<TeamStandingsCategory>();
        runs.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings1));
        runs.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings2));
        runs.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings3));
        runs.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings4));
        runs.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings5));
        runs.add(new TeamStandingsCategory(StandingsCategory.RUNS).withCategoryAmount(3.0).withTeamStandings(teamStandings6));

        listOfTeamStandingsCategories.add(runs);

        standingsTeamList = Standings.calculate(listOfTeamStandingsCategories);

        Assert.assertEquals(6, standingsTeamList.size());
        Assert.assertEquals(1, (int)standingsTeamList.get(0).getTeam().getTeamId());
        Assert.assertEquals(1, (int)standingsTeamList.get(0).getPlace());
        Assert.assertTrue(standingsTeamList.get(0).isTied());
        Assert.assertEquals(8.5, standingsTeamList.get(0).getTotalPoints());
        Assert.assertEquals(2, (int)standingsTeamList.get(1).getTeam().getTeamId());
        Assert.assertEquals(1, (int)standingsTeamList.get(1).getPlace());
        Assert.assertTrue(standingsTeamList.get(1).isTied());
        Assert.assertEquals(8.5, standingsTeamList.get(1).getTotalPoints());
        Assert.assertEquals(3, (int)standingsTeamList.get(2).getTeam().getTeamId());
        Assert.assertEquals(1, (int)standingsTeamList.get(2).getPlace());
        Assert.assertTrue(standingsTeamList.get(2).isTied());
        Assert.assertEquals(8.5, standingsTeamList.get(2).getTotalPoints());
        Assert.assertEquals(4, (int)standingsTeamList.get(3).getTeam().getTeamId());
        Assert.assertEquals(4, (int)standingsTeamList.get(3).getPlace());
        Assert.assertNull(standingsTeamList.get(3).isTied());
        Assert.assertEquals(6.5, standingsTeamList.get(3).getTotalPoints());
        Assert.assertEquals(5, (int)standingsTeamList.get(4).getTeam().getTeamId());
        Assert.assertEquals(5, (int)standingsTeamList.get(4).getPlace());
        Assert.assertNull(standingsTeamList.get(4).isTied());
        Assert.assertEquals(5.5, standingsTeamList.get(4).getTotalPoints());
        Assert.assertEquals(6, (int)standingsTeamList.get(5).getTeam().getTeamId());
        Assert.assertEquals(6, (int)standingsTeamList.get(5).getPlace());
        Assert.assertNull(standingsTeamList.get(5).isTied());
        Assert.assertEquals(4.5, standingsTeamList.get(5).getTotalPoints());
    }
}
