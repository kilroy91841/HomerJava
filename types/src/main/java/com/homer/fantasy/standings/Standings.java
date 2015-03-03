package com.homer.fantasy.standings;

import com.homer.fantasy.Team;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by arigolub on 3/1/15.
 */
public class Standings {

    public List<List<TeamStandingsCategory>> listOfTeamStandingsCategories = new ArrayList<List<TeamStandingsCategory>>();

    public Standings(List<List<TeamStandingsCategory>> listOfTeamStandingsCategories) {
        this.listOfTeamStandingsCategories = listOfTeamStandingsCategories;
    }

    public List<TeamStandings> calculate() {
        Map<Integer, TeamStandings>  teamMap = new HashMap<Integer, TeamStandings>();
        Map<Integer, Double> pointMap = new HashMap<Integer, Double>();
        listOfTeamStandingsCategories.forEach(teamStanding -> {
            teamStanding.sort((t1, t2) -> t1.compareTo(t2));
            int points = teamStanding.size();
            for(int i = 0; i < teamStanding.size(); ) {
                double curPoints = points;
                int peek = 1;
                double split = 1;
                Double curAmount = teamStanding.get(i).getCategoryAmount();
                Double nextAmount = i + peek < teamStanding.size() ? teamStanding.get(i+peek).getCategoryAmount() : null;
                while(curAmount.equals(nextAmount)) {
                    split++;
                    points--;
                    curPoints += points;
                    peek++;
                    nextAmount = i + peek < teamStanding.size() ? teamStanding.get(i+peek).getCategoryAmount() : null;
                }
                double pointsPerTeam = curPoints / split;
                while(split > 0) {
                    teamStanding.get(i).setCategoryPoints(pointsPerTeam);
                    split--;
                    i++;
                }
                points--;
            }
            teamStanding.forEach(teamAmount -> {
                int teamId = teamAmount.getTeam().getTeamId();
                pointMap.put(teamId, pointMap.getOrDefault(teamId, 0.0) + teamAmount.getCategoryPoints());
                TeamStandings standingsTeam = teamMap.getOrDefault(teamId, new TeamStandings(teamAmount.getTeam()));
                standingsTeam.categoryToTeamAmount.put(teamAmount.getStandingsCategory(), teamAmount);
                teamMap.put(teamId, standingsTeam);
            });
        });
        List<TeamStandings> standingsTeamList = teamMap.values().stream().collect(Collectors.toList());
        standingsTeamList.sort((s1, s2) -> s1.compareTo(s2));
        return standingsTeamList;
    }

    public void calculateDifference(TeamStandings before, TeamStandings after) {
        for(StandingsCategory sc : before.getCategoryToTeamAmount().keySet()) {
            before.getCategoryToTeamAmount().merge(
                    sc, after.getTeamStandingsCategory(sc),
                    (tsc1, tsc2) -> {
                        return tsc1.withDifference(tsc2.getCategoryPoints() - tsc1.getCategoryPoints());
                    }
            );
        }
        before.getCategoryToTeamAmount().values().forEach(tsc ->
                System.out.println("Category: " + tsc.getStandingsCategory().getStandingsCategoryName() + ", Difference: " + tsc.getDifference())
        );
    }
}
