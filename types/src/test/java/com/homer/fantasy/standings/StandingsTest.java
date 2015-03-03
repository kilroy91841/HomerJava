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
        List<List<TeamStandingsCategory>> listOfTeamStandingsCategories = new ArrayList<List<TeamStandingsCategory>>();

        List<TeamStandingsCategory> wins = new ArrayList<TeamStandingsCategory>();
        wins.add(new TeamStandingsCategory(team1, LocalDate.now(), StandingsCategory.WINS).withCategoryAmount(6.0));
        wins.add(new TeamStandingsCategory(team2, LocalDate.now(), StandingsCategory.WINS).withCategoryAmount(5.0));
        wins.add(new TeamStandingsCategory(team3, LocalDate.now(), StandingsCategory.WINS).withCategoryAmount(4.0));
        wins.add(new TeamStandingsCategory(team4, LocalDate.now(), StandingsCategory.WINS).withCategoryAmount(3.0));
        wins.add(new TeamStandingsCategory(team5, LocalDate.now(), StandingsCategory.WINS).withCategoryAmount(2.0));
        wins.add(new TeamStandingsCategory(team6, LocalDate.now(), StandingsCategory.WINS).withCategoryAmount(1.0));

        listOfTeamStandingsCategories.add(wins);

        List<TeamStandingsCategory> runs = new ArrayList<TeamStandingsCategory>();
        runs.add(new TeamStandingsCategory(team1, LocalDate.now(), StandingsCategory.RUNS).withCategoryAmount(3.0));
        runs.add(new TeamStandingsCategory(team2, LocalDate.now(), StandingsCategory.RUNS).withCategoryAmount(3.0));
        runs.add(new TeamStandingsCategory(team3, LocalDate.now(), StandingsCategory.RUNS).withCategoryAmount(3.0));
        runs.add(new TeamStandingsCategory(team4, LocalDate.now(), StandingsCategory.RUNS).withCategoryAmount(3.0));
        runs.add(new TeamStandingsCategory(team5, LocalDate.now(), StandingsCategory.RUNS).withCategoryAmount(3.0));
        runs.add(new TeamStandingsCategory(team6, LocalDate.now(), StandingsCategory.RUNS).withCategoryAmount(3.0));

        listOfTeamStandingsCategories.add(runs);

        Standings standings = new Standings(listOfTeamStandingsCategories);
        List<TeamStandings> standingsTeamList = standings.calculate();

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
        wins2.add(new TeamStandingsCategory(team1, LocalDate.now(), StandingsCategory.WINS).withCategoryAmount(6.0));
        wins2.add(new TeamStandingsCategory(team2, LocalDate.now(), StandingsCategory.WINS).withCategoryAmount(5.0));
        wins2.add(new TeamStandingsCategory(team3, LocalDate.now(), StandingsCategory.WINS).withCategoryAmount(4.0));
        wins2.add(new TeamStandingsCategory(team4, LocalDate.now(), StandingsCategory.WINS).withCategoryAmount(3.0));
        wins2.add(new TeamStandingsCategory(team5, LocalDate.now(), StandingsCategory.WINS).withCategoryAmount(2.0));
        wins2.add(new TeamStandingsCategory(team6, LocalDate.now(), StandingsCategory.WINS).withCategoryAmount(1.0));

        listOfTeamStandingsCategories2.add(wins2);

        List<TeamStandingsCategory> runs2 = new ArrayList<TeamStandingsCategory>();
        runs2.add(new TeamStandingsCategory(team1, LocalDate.now(), StandingsCategory.RUNS).withCategoryAmount(4.0));
        runs2.add(new TeamStandingsCategory(team2, LocalDate.now(), StandingsCategory.RUNS).withCategoryAmount(3.0));
        runs2.add(new TeamStandingsCategory(team3, LocalDate.now(), StandingsCategory.RUNS).withCategoryAmount(3.0));
        runs2.add(new TeamStandingsCategory(team4, LocalDate.now(), StandingsCategory.RUNS).withCategoryAmount(3.0));
        runs2.add(new TeamStandingsCategory(team5, LocalDate.now(), StandingsCategory.RUNS).withCategoryAmount(3.0));
        runs2.add(new TeamStandingsCategory(team6, LocalDate.now(), StandingsCategory.RUNS).withCategoryAmount(3.0));

        listOfTeamStandingsCategories2.add(runs2);

        standings = new Standings(listOfTeamStandingsCategories2);
        List<TeamStandings> standingsDay2 = standings.calculate();

        standings.calculateDifference(standingsTeamList.get(0), standingsDay2.get(0));
    }
}
