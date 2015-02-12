package com.homer.fantasy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by arigolub on 2/1/15.
 */
@Entity
@Table(name="VULTURE")
public class Vulture {

    public enum Status {
        ACTIVE,
        RESOLVED,
        GRANTED
    }

    @Id
    @Column(name="vultureId")
    public int vultureId;
    @OneToOne
    @JoinColumn(name="vulturingTeamId", referencedColumnName="teamId")
    public Team vulturingTeam;
    @OneToOne
    @JoinColumn(name="offendingTeamId", referencedColumnName="teamId")
    public Team offendingTeam;
    @OneToOne
    @JoinColumn(name="playerId", referencedColumnName="playerId")
    public Player player;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="createdDate")
    public Date createdDate;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="deadline")
    public Date deadline;
    @Enumerated(EnumType.STRING)
    @Column(name="vultureStatus")
    public Status vultureStatus;

    public Vulture() { }

    public Vulture(Team vulturingTeam, Team offendingTeam, Player player, Date deadline, Status vultureStatus) {
        this.vulturingTeam = vulturingTeam;
        this.offendingTeam = offendingTeam;
        this.player = player;
        this.deadline = deadline;
        this.vultureStatus = vultureStatus;
    }

    public Team getVulturingTeam() {
        return vulturingTeam;
    }

    public void setVulturingTeam(Team vulturingTeam) {
        this.vulturingTeam = vulturingTeam;
    }

    public Team getOffendingTeam() {
        return offendingTeam;
    }

    public void setOffendingTeam(Team offendingTeam) {
        this.offendingTeam = offendingTeam;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Status getVultureStatus() {
        return vultureStatus;
    }

    public void setVultureStatus(Status vultureStatus) {
        this.vultureStatus = vultureStatus;
    }

    @Override
    public String toString() {
        return "Vulture{" +
                "vultureId=" + vultureId +
                ", vulturingTeam=" + vulturingTeam +
                ", offendingTeam=" + offendingTeam +
                ", player=" + player +
                ", createdDate=" + createdDate +
                ", deadline=" + deadline +
                ", vultureStatus=" + vultureStatus +
                '}';
    }
}
