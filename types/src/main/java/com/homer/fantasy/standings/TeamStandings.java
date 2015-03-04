package com.homer.fantasy.standings;

import com.homer.fantasy.Team;
import com.homer.util.LocalDatePersistenceConverter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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
    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumns({
            @JoinColumn(name = "date", referencedColumnName="date"),
            @JoinColumn(name = "teamId", referencedColumnName="teamId")
    })
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
}
