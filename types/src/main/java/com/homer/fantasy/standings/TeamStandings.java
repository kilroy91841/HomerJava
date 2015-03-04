package com.homer.fantasy.standings;

import com.homer.fantasy.Team;
import com.homer.util.LocalDatePersistenceConverter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Created by arigolub on 3/1/15.
*/
@Entity
@Table(name="TEAMSTANDINGS")
public class TeamStandings implements Comparable, Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="teamStandingsId")
    private long teamStandingsId;
    @OneToOne
    @JoinColumn(name="teamId", referencedColumnName="teamId")
    private Team team;
    @Column(name="place")
    private Integer place;
    @Column(name="tied")
    private Boolean tied;
    @Convert(converter=LocalDatePersistenceConverter.class)
    @Column(name="date")
    private LocalDate date;
//    @OneToMany( fetch=FetchType.EAGER, mappedBy = "owner", cascade = CascadeType.ALL)
    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name="teamStandingsId", referencedColumnName="teamStandingsId")
    @Fetch(FetchMode.SELECT)
    private List<TeamStandingsCategory> teamStandingsCategoryList;
    @Transient
    private Map<StandingsCategory, TeamStandingsCategory> categoryToTeamAmount;

    public TeamStandings() {
        this.categoryToTeamAmount = new HashMap<StandingsCategory, TeamStandingsCategory>();
    }

    public TeamStandings(Team team) {
        this.team = team;
        this.categoryToTeamAmount = new HashMap<StandingsCategory, TeamStandingsCategory>();
    }

    public TeamStandings(Team team, LocalDate date) {
        this.team = team;
        this.date = date;
        this.categoryToTeamAmount = new HashMap<StandingsCategory, TeamStandingsCategory>();
    }

    public Team getTeam() {
        return team;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<StandingsCategory, TeamStandingsCategory> getCategoryToTeamAmount() {
        return categoryToTeamAmount;
    }

    public TeamStandingsCategory getTeamStandingsCategory(StandingsCategory standingsCategory) {
        return categoryToTeamAmount.get(standingsCategory);
    }

    public long getTeamStandingsId() {
        return teamStandingsId;
    }

    public void setTeamStandingsId(long teamStandingsId) {
        this.teamStandingsId = teamStandingsId;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public Boolean isTied() {
        return tied;
    }

    public void setTied(Boolean tied) {
        this.tied = tied;
    }

    public List<TeamStandingsCategory> getTeamStandingsCategoryList() {
        return teamStandingsCategoryList;
    }

    public void setTeamStandingsCategoryList(List<TeamStandingsCategory> teamStandingsCategoryList) {
        if(this.teamStandingsCategoryList == null) {
            this.teamStandingsCategoryList = new ArrayList<TeamStandingsCategory>();
        }
        this.teamStandingsCategoryList = teamStandingsCategoryList;
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
        return "TeamStandings{" +
                "teamStandingsId=" + teamStandingsId +
                ", team=" + team +
                ", place=" + place +
                ", tied=" + tied +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if(!this.getClass().equals(o.getClass())) throw new RuntimeException("Comparing wrong classes");
        TeamStandings other = (TeamStandings) o;
        return this.getTotalPoints() > other.getTotalPoints() ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamStandings that = (TeamStandings) o;

        if (!date.equals(that.date)) return false;
        if (!team.equals(that.team)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (teamStandingsId ^ (teamStandingsId >>> 32));
        result = 31 * result + team.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
