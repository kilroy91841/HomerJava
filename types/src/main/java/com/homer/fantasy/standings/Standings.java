package com.homer.fantasy.standings;

import com.homer.fantasy.Team;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by arigolub on 3/1/15.
 */
public class Standings {

    public static List<TeamStandings> calculate(List<List<TeamStandingsCategory>> listOfTeamStandingsCategories) {
        Map<Integer, TeamStandings>  teamMap = new HashMap<Integer, TeamStandings>();
        listOfTeamStandingsCategories.forEach(listOfTeamStandingsCategory -> {
            listOfTeamStandingsCategory.sort((t1, t2) -> t1.compareTo(t2));
            int points = listOfTeamStandingsCategory.size();
            for(int i = 0; i < listOfTeamStandingsCategory.size(); ) {
                double curPoints = points;
                int peek = 1;
                double split = 1;
                Double curAmount = listOfTeamStandingsCategory.get(i).getCategoryAmount();
                Double nextAmount = i + peek < listOfTeamStandingsCategory.size() ? listOfTeamStandingsCategory.get(i+peek).getCategoryAmount() : null;
                while(curAmount.equals(nextAmount)) {
                    split++;
                    points--;
                    curPoints += points;
                    peek++;
                    nextAmount = i + peek < listOfTeamStandingsCategory.size() ? listOfTeamStandingsCategory.get(i+peek).getCategoryAmount() : null;
                }
                double pointsPerTeam = curPoints / split;
                while(split > 0) {
                    listOfTeamStandingsCategory.get(i).setCategoryPoints(pointsPerTeam);
                    split--;
                    i++;
                }
                points--;
            }
            listOfTeamStandingsCategory.forEach(teamStandingsCategory -> {
                int teamId = teamStandingsCategory.getTeamStandings().getTeam().getTeamId();
                TeamStandings standingsTeam = teamMap.getOrDefault(teamId, new TeamStandings(teamStandingsCategory.getTeamStandings().getTeam(), teamStandingsCategory.getTeamStandings().getDate()));
                standingsTeam.getCategoryToTeamAmount().put(teamStandingsCategory.getStandingsCategory(), teamStandingsCategory);
                teamMap.put(teamId, standingsTeam);
            });
        });
        List<TeamStandings> standingsTeamList = teamMap.values().stream().collect(Collectors.toList());

        standingsTeamList.sort((s1, s2) -> s1.compareTo(s2));

        for(int i = 0; i < standingsTeamList.size(); ) {
            int curPlace = i + 1;
            Double curAmount = standingsTeamList.get(i).getTotalPoints();
            Double nextAmount = i + 1 < standingsTeamList.size() ? standingsTeamList.get(i + 1).getTotalPoints() : null;
            standingsTeamList.get(i).setPlace(curPlace);
            while (curAmount.equals(nextAmount)) {
                standingsTeamList.get(i).setTied(true);

                standingsTeamList.get(i + 1).setPlace(curPlace);
                standingsTeamList.get(i + 1).setTied(true);

                i++;
                nextAmount = i + 1 < standingsTeamList.size() ? standingsTeamList.get(i + 1).getTotalPoints() : null;
            }
            i++;
        }
        standingsTeamList.forEach(s -> s.setTeamStandingsCategoryList(s.getCategoryToTeamAmount().values().stream().collect(Collectors.toList())));
        return standingsTeamList;
    }

    public static void calculateDifference(List<TeamStandings> before, List<TeamStandings> after) {
        before.forEach(beforeTeam -> {
            after.forEach(afterTeam -> {
                if(afterTeam.getTeam().equals(beforeTeam.getTeam())) {
                    calculateDifference(beforeTeam, afterTeam);
                }
            });
        });
    }

    public static void calculateDifference(TeamStandings before, TeamStandings after) {

        for(StandingsCategory sc : before.getCategoryToTeamAmount().keySet()) {
            after.getCategoryToTeamAmount().merge(
                    sc, before.getTeamStandingsCategory(sc),
                    (tsc1, tsc2) -> {
                        return tsc1.withDifference(tsc1.getCategoryPoints() - tsc2.getCategoryPoints());
                    }
            );
        }
        after.getCategoryToTeamAmount().values().forEach(tsc ->
                System.out.println("Team: " + tsc.getTeamStandings().getTeam() + ", Category: " + tsc.getStandingsCategory().getStandingsCategoryName() + ", Difference: " + tsc.getDifference())
        );
    }
}
