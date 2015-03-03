package com.homer.fantasy.standings;

import com.homer.fantasy.Team;
import com.homer.util.LocalDatePersistenceConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arigolub on 3/3/15.
 */
@Entity
@Table(name="TEAMSTANDINGSCATEGORY")
public class TeamStandingsCategory implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="teamStandingsCategoryId")
    private long teamStandingsCategoryId;
    @OneToOne
    @JoinColumn(name="teamId", referencedColumnName="teamId")
    private Team team;
    @Convert(converter=LocalDatePersistenceConverter.class)
    @Column(name="date")
    private LocalDate date;
    @OneToOne
    @JoinColumn(name="standingsCategoryName", referencedColumnName="standingsCategoryName")
    private StandingsCategory standingsCategory;
    @Column(name="categoryAmount")
    private Double categoryAmount;
    @Column(name="categoryPoints")
    private Double categoryPoints;

    private Double difference;

    public TeamStandingsCategory() { }

    public TeamStandingsCategory(Team team, LocalDate date, StandingsCategory standingsCategory) {
        this.team = team;
        this.date = date;
        this.standingsCategory = standingsCategory;
    }

    public TeamStandingsCategory withCategoryAmount(Double categoryAmount) {
        this.categoryAmount = categoryAmount;
        return this;
    }

    public long getTeamStandingsId() {
        return teamStandingsCategoryId;
    }

    public void setTeamStandingsId(long teamStandingsId) {
        this.teamStandingsCategoryId = teamStandingsId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StandingsCategory getStandingsCategory() {
        return standingsCategory;
    }

    public void setStandingsCategory(StandingsCategory standingsCategory) {
        this.standingsCategory = standingsCategory;
    }

    public Double getCategoryAmount() {
        return categoryAmount;
    }

    public void setCategoryAmount(Double categoryAmount) {
        this.categoryAmount = categoryAmount;
    }

    public Double getCategoryPoints() {
        return categoryPoints;
    }

    public void setCategoryPoints(Double categoryPoints) {
        this.categoryPoints = categoryPoints;
    }

    public TeamStandingsCategory withDifference(Double difference) {
        this.difference = difference;
        return this;
    }

    public Double getDifference() {
        return difference;
    }

    @Override
    public String toString() {
        return "TeamStandings{" +
                "teamStandingsId=" + teamStandingsCategoryId +
                ", team=" + team +
                ", date=" + date +
                ", standingsCategory=" + standingsCategory +
                ", categoryAmount=" + categoryAmount +
                ", categoryPoints=" + categoryPoints +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if(!this.getClass().equals(o.getClass())) throw new RuntimeException(("Comparing wrong class"));
        TeamStandingsCategory other = (TeamStandingsCategory) o;
        if(!this.getStandingsCategory().equals(other.getStandingsCategory())) throw new RuntimeException("Comparing wrong categories");

        return this.getStandingsCategory().isDescending() ?
                this.getCategoryAmount() > other.getCategoryAmount() ? -1 : 1 :
                this.getCategoryAmount() < other.getCategoryAmount() ? 1 : -1;
    }
}
