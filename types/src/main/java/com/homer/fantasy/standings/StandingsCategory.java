package com.homer.fantasy.standings;

import javax.persistence.*;

/**
 * Created by arigolub on 3/1/15.
 */
@Entity
@Table(name="STANDINGSCATEGORY")
public class StandingsCategory {

    public static final StandingsCategory RUNS  = new StandingsCategory("RUNS", true, true);
    public static final StandingsCategory RBI   = new StandingsCategory("RBI", true, true);
    public static final StandingsCategory HR    = new StandingsCategory("HR", true, true);
    public static final StandingsCategory SB    = new StandingsCategory("SB", true, true);
    public static final StandingsCategory OBP   = new StandingsCategory("OBP", true, true);
    public static final StandingsCategory WINS  = new StandingsCategory("WINS", false, true);
    public static final StandingsCategory SV    = new StandingsCategory("SV", false, true);
    public static final StandingsCategory K     = new StandingsCategory("K", false, true);
    public static final StandingsCategory ERA   = new StandingsCategory("ERA", false, false);
    public static final StandingsCategory WHIP  = new StandingsCategory("WHIP", false, false);

    @Id
    @Column(name="standingsCategoryName")
    private String standingsCategoryName;
    @Column(name="isHitting")
    private boolean isHitting;
    @Column(name="isDescending")
    private boolean isDescending;

    public StandingsCategory() { }

    public StandingsCategory(String standingsCategoryName, boolean isHitting, boolean isDescending) {
        this.standingsCategoryName = standingsCategoryName;
        this.isHitting = isHitting;
        this.isDescending = isDescending;
    }

    public String getStandingsCategoryName() {
        return standingsCategoryName;
    }

    public void setStandingsCategoryName(String standingsCategoryName) {
        this.standingsCategoryName = standingsCategoryName;
    }

    public boolean isHitting() {
        return isHitting;
    }

    public void setHitting(boolean isHitting) {
        this.isHitting = isHitting;
    }

    public boolean isDescending() {
        return isDescending;
    }

    public void setDescending(boolean isDescending) {
        this.isDescending = isDescending;
    }

    @Override
    public String toString() {
        return "StandingsCategory{" +
                "standingsCategoryName='" + standingsCategoryName + '\'' +
                ", isHitting=" + isHitting +
                ", isDescending=" + isDescending +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StandingsCategory that = (StandingsCategory) o;

        if (!standingsCategoryName.equals(that.standingsCategoryName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return standingsCategoryName.hashCode();
    }
}
