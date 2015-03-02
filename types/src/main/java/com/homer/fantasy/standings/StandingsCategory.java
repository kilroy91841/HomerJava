package com.homer.fantasy.standings;

/**
 * Created by arigolub on 3/1/15.
 */
public class StandingsCategory {

    public static final StandingsCategory RUNS  = new StandingsCategory("RUNS");
    public static final StandingsCategory RBI   = new StandingsCategory("RBI");
    public static final StandingsCategory HR    = new StandingsCategory("HR");
    public static final StandingsCategory SB    = new StandingsCategory("SB");
    public static final StandingsCategory OBP   = new StandingsCategory("OBP");
    public static final StandingsCategory WINS  = new StandingsCategory("WINS");
    public static final StandingsCategory SV    = new StandingsCategory("SV");
    public static final StandingsCategory K     = new StandingsCategory("K");
    public static final StandingsCategory ERA   = new StandingsCategory("ERA", true);
    public static final StandingsCategory WHIP  = new StandingsCategory("WHIP", true);

    private String category;
    private boolean smallerIsBetter;

    private StandingsCategory(String category) {
        this.category = category;
        this.smallerIsBetter = false;
    }

    private StandingsCategory(String category, boolean smallerIsBetter) {
        this.category = category;
        this.smallerIsBetter = smallerIsBetter;
    }

    public boolean isSmallerBetter() {
        return smallerIsBetter;
    }

    @Override
    public String toString() {
        return "StandingsCategory{" +
                "category='" + category + '\'' +
                ", smallerIsBetter=" + smallerIsBetter +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StandingsCategory that = (StandingsCategory) o;

        if (smallerIsBetter != that.smallerIsBetter) return false;
        if (!category.equals(that.category)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = category.hashCode();
        result = 31 * result + (smallerIsBetter ? 1 : 0);
        return result;
    }
}
