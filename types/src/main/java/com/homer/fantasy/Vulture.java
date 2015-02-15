package com.homer.fantasy;

import com.homer.util.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="createdDate")
    public LocalDateTime createdDate;
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="deadline")
    public LocalDateTime deadline;
    @Enumerated(EnumType.STRING)
    @Column(name="vultureStatus")
    public Status vultureStatus;

    public Vulture() { }

    public Vulture(Team vulturingTeam, Team offendingTeam, Player player, LocalDateTime deadline, Status vultureStatus) {
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
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
