package com.homer.fantasy.standings;

import org.junit.Test;
import com.homer.fantasy.standings.Standings.*;

import java.util.ArrayList;

/**
 * Created by arigolub on 3/1/15.
 */
public class StandingsTest {

    @Test
    public void calculateStandings() {
        Category runs = new Category();
        runs.name = StandingsCategory.RUNS;
        runs.teamAmounts = new ArrayList<TeamAmount>();
        runs.teamAmounts.add(new TeamAmount(1, 1));
        runs.teamAmounts.add(new TeamAmount(2, 1));
        runs.teamAmounts.add(new TeamAmount(3, 2));
        runs.teamAmounts.add(new TeamAmount(4, 2));
        runs.teamAmounts.add(new TeamAmount(5, 3));
        runs.teamAmounts.add(new TeamAmount(6, 3));

        Category wins = new Category();
        wins.name = StandingsCategory.WINS;
        wins.teamAmounts = new ArrayList<TeamAmount>();
        wins.teamAmounts.add(new TeamAmount(1, 1));
        wins.teamAmounts.add(new TeamAmount(2, 1));
        wins.teamAmounts.add(new TeamAmount(3, 2));
        wins.teamAmounts.add(new TeamAmount(4, 2));
        wins.teamAmounts.add(new TeamAmount(5, 3));
        wins.teamAmounts.add(new TeamAmount(6, 3));

        Standings standings = new Standings();
        standings.categories.add(runs);
        standings.categories.add(wins);

        standings.calculate();
    }
}
