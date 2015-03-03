package com.homer.fantasy.standings;

import com.homer.fantasy.Team;
import com.homer.util.LocalDatePersistenceConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by arigolub on 3/1/15.
*/
public class TeamStandings implements Comparable{
    public Team team;
    public Map<StandingsCategory, TeamStandingsCategory> categoryToTeamAmount;

    public TeamStandings(Team team) {
        this.team = team;
        this.categoryToTeamAmount = new HashMap<StandingsCategory, TeamStandingsCategory>();
    }

    public Team getTeam() {
        return team;
    }

    public Map<StandingsCategory, TeamStandingsCategory> getCategoryToTeamAmount() {
        return categoryToTeamAmount;
    }

    public TeamStandingsCategory getTeamStandingsCategory(StandingsCategory standingsCategory) {
        return categoryToTeamAmount.get(standingsCategory);
    }

    public Double getTotalPoints() {
        Double total = 0.0;
        for(TeamStandingsCategory teamAmount : categoryToTeamAmount.values()) {
            total += teamAmount.getCategoryPoints();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Team[id=" + team.getTeamId() + "], CategoryToTeamAmount[" + categoryToTeamAmount.toString() + "], Total[total=" + getTotalPoints() + "]";
    }

    @Override
    public int compareTo(Object o) {
        if(!this.getClass().equals(o.getClass())) throw new RuntimeException("Comparing wrong classes");
        TeamStandings other = (TeamStandings) o;
        return this.getTotalPoints() > other.getTotalPoints() ? -1 : 1;
    }
}
