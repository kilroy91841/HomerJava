package com.homer.fantasy.standings;

import com.homer.fantasy.Team;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arigolub on 3/1/15.
 */
public class Standings {

    public List<Category> categories = new ArrayList<Category>();
    private Map<Integer, StandingsTeam>  teamMap = new HashMap<Integer, StandingsTeam>();

    private Period period;
    private LocalDate beginDate;
    private LocalDate endDate;

    public Standings() {
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public static void main(String[] args) {
        Category runs = new Category();
        runs.name = StandingsCategory.ERA;
        runs.teamAmounts = new ArrayList<TeamAmount>();
        runs.teamAmounts.add(new TeamAmount(1, 1));
        runs.teamAmounts.add(new TeamAmount(2, 2));
        runs.teamAmounts.add(new TeamAmount(3, 3));
        runs.teamAmounts.add(new TeamAmount(4, 4));
        runs.teamAmounts.add(new TeamAmount(5, 5));
        runs.teamAmounts.add(new TeamAmount(6, 6));

        Category wins = new Category();
        wins.name = StandingsCategory.WHIP;
        wins.teamAmounts = new ArrayList<TeamAmount>();
        wins.teamAmounts.add(new TeamAmount(1, 1));
        wins.teamAmounts.add(new TeamAmount(2, 2));
        wins.teamAmounts.add(new TeamAmount(3, 3));
        wins.teamAmounts.add(new TeamAmount(4, 4));
        wins.teamAmounts.add(new TeamAmount(5, 5));
        wins.teamAmounts.add(new TeamAmount(6, 6));

        Standings standings = new Standings();
        standings.categories.add(runs);
        standings.categories.add(wins);

        standings.calculate();
    }

    public void calculate() {
        Map<Integer, Double> pointMap = new HashMap<Integer, Double>();
        categories.forEach(category -> {
            category.teamAmounts.sort((t1, t2) -> t1.compareTo(t2, category.name.isSmallerBetter()));
            int points = category.teamAmounts.size();
            for(int i = 0; i < category.teamAmounts.size(); ) {
                double curPoints = points;
                int peek = 1;
                double split = 1;
                Double curAmount = category.teamAmounts.get(i).amount;
                Double nextAmount = i + peek < category.teamAmounts.size() ? category.teamAmounts.get(i+peek).amount : null;
                while(curAmount.equals(nextAmount)) {
                    split++;
                    points--;
                    curPoints += points;
                    peek++;
                    nextAmount = i + peek < category.teamAmounts.size() ? category.teamAmounts.get(i+peek).amount : null;
                }
                double pointsPerTeam = curPoints / split;
                while(split > 0) {
                    category.teamAmounts.get(i).points = pointsPerTeam;
                    split--;
                    i++;
                }
                points--;
            }
            category.teamAmounts.forEach(teamAmount -> {
                pointMap.put(teamAmount.teamId, pointMap.getOrDefault(teamAmount.teamId, 0.0) + teamAmount.points);
                StandingsTeam standingsTeam = teamMap.getOrDefault(teamAmount.teamId, new StandingsTeam(new Team(teamAmount.teamId)));
                standingsTeam.categoryToTeamAmount.put(category.name, teamAmount);
                teamMap.put(teamAmount.teamId, standingsTeam);
            });
        });
        System.out.println(pointMap);
        System.out.println(teamMap);
    }

    public static class TeamAmount {
        public int teamId;
        public double amount;
        public double points;

        public TeamAmount(int teamId, double amount) {
            this.teamId = teamId;
            this.amount = amount;
        }

        public int compareTo(TeamAmount o, boolean isSmallerBetter) {
            return isSmallerBetter ?
                    this.amount < o.amount ? -1 : 1
                    : this.amount > o.amount ? -1 : 1;
        }

        @Override
        public String toString() {
            return "TeamAmount{" +
                    "teamId=" + teamId +
                    ", amount=" + amount +
                    ", points=" + points +
                    '}';
        }
    }

    public static class Category {
        public StandingsCategory name;
        public List<TeamAmount> teamAmounts;
    }

    public static class StandingsTeam {
        public Team team;
        public Map<StandingsCategory, TeamAmount> categoryToTeamAmount;

        public StandingsTeam(Team team) {
            this.team = team;
            this.categoryToTeamAmount = new HashMap<StandingsCategory, TeamAmount>();
        }

        public Double getTotalPoints() {
            Double total = 0.0;
            for(TeamAmount teamAmount : categoryToTeamAmount.values()) {
                total += teamAmount.points;
            }
            return total;
        }

        @Override
        public String toString() {
            return "Team[id=" + team.getTeamId() + "], CategoryToTeamAmount[" + categoryToTeamAmount.toString() + "], Total[total=" + getTotalPoints() + "]";
        }
    }
}
