package com.homer.fantasy.standings;

import com.homer.fantasy.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 3/1/15.
 */
public class StandingsTeam {

    private Team team;
    private List<StandingsStatistic> stats;

    public StandingsTeam(Team team) {
        this.team = team;
        this.stats = new ArrayList<StandingsStatistic>();
    }

    public Team getTeam() {
        return team;
    }

    public StandingsTeam addStatistic(StandingsStatistic statistic) {
        this.stats.add(statistic);
        return this;
    }

    public StandingsTeam addStatistics(StandingsStatistic... statistics) {
        for (StandingsStatistic statistic : statistics) {
            this.stats.add(statistic);
        }
        return this;
    }
}
