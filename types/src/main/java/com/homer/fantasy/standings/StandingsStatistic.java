package com.homer.fantasy.standings;

/**
 * Created by arigolub on 3/1/15.
 */
public class StandingsStatistic {

    private StandingsCategory category;
    private double value;
    private int points;

    public StandingsStatistic(StandingsCategory category, double value) {
        this.category = category;
        this.value = value;
    }

    public StandingsCategory getCategory() {
        return category;
    }

    public double getValue() {
        return value;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
