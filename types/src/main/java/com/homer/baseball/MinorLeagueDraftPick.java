package com.homer.baseball;

import java.util.Date;

/**
 * Created by arigolub on 1/31/15.
 */
public class MinorLeagueDraftPick implements Tradable {

    private Team originalTeam;
    private int season;
    private int round;
    private Team owningTeam;
    private Integer overall;
    private Player player;
    private Date deadline;
    private Boolean skipped;

    public MinorLeagueDraftPick() { }

    public MinorLeagueDraftPick(Team originalTeam, int season, int round, Team owningTeam, Integer overall,
                                Player player, Date deadline, Boolean skipped) {
        this.originalTeam = originalTeam;
        this.season = season;
        this.round = round;
        this.owningTeam = owningTeam;
        this.overall = overall;
        this.player = player;
        this.deadline = deadline;
        this.skipped = skipped;
    }

    public Team getOriginalTeam() {
        return originalTeam;
    }

    public void setOriginalTeam(Team originalTeam) {
        this.originalTeam = originalTeam;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Team getOwningTeam() {
        return owningTeam;
    }

    public void setOwningTeam(Team owningTeam) {
        this.owningTeam = owningTeam;
    }

    public Integer getOverall() {
        return overall;
    }

    public void setOverall(Integer overall) {
        this.overall = overall;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Boolean getSkipped() {
        return skipped;
    }

    public void setSkipped(Boolean skipped) {
        this.skipped = skipped;
    }

    @Override
    public String toString() {
        return "MinorLeagueDraftPick{" +
                "originalTeam=" + originalTeam +
                ", season=" + season +
                ", round=" + round +
                ", owningTeam=" + owningTeam +
                ", overall=" + overall +
                ", player=" + player +
                ", deadline=" + deadline +
                ", skipped=" + skipped +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MinorLeagueDraftPick that = (MinorLeagueDraftPick) o;

        if (overall != that.overall) return false;
        if (round != that.round) return false;
        if (season != that.season) return false;
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) return false;
        if (!originalTeam.equals(that.originalTeam)) return false;
        if (!owningTeam.equals(that.owningTeam)) return false;
        if (player != null ? !player.equals(that.player) : that.player != null) return false;
        if (skipped != null ? !skipped.equals(that.skipped) : that.skipped != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = originalTeam.hashCode();
        result = 31 * result + season;
        result = 31 * result + round;
        result = 31 * result + owningTeam.hashCode();
        result = 31 * result + overall;
        result = 31 * result + (player != null ? player.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (skipped != null ? skipped.hashCode() : 0);
        return result;
    }
}
