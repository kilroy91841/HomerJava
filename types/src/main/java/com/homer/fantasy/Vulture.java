package com.homer.fantasy;

import com.homer.util.LocalDateTimePersistenceConverter;
import org.hibernate.annotations.Cascade;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="vultureId")
    private int vultureId;
    @OneToOne
    @JoinColumn(name="vulturingTeamId", referencedColumnName="teamId")
    private Team vulturingTeam;
    @OneToOne
    @JoinColumn(name="offendingTeamId", referencedColumnName="teamId")
    private Team offendingTeam;
    @OneToOne
    @JoinColumn(name="playerId", referencedColumnName="playerId")
    private Player player;
    @OneToOne
    @JoinColumn(name="droppingPlayerId", referencedColumnName="playerId")
    private Player droppingPlayer;
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="createdDate")
    private LocalDateTime createdDate;
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="deadline")
    private LocalDateTime deadline;
    @Enumerated(EnumType.STRING)
    @Column(name="vultureStatus")
    private Status vultureStatus;

    public Vulture() { }

    public int getVultureId() {
        return vultureId;
    }

    public void setVultureId(int vultureId) {
        this.vultureId = vultureId;
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

    public Player getDroppingPlayer() {
        return droppingPlayer;
    }

    public void setDroppingPlayer(Player droppingPlayer) {
        this.droppingPlayer = droppingPlayer;
    }

    @Override
    public String toString() {
        return "Vulture{" +
                "vultureId=" + vultureId +
                ", vulturingTeam=" + vulturingTeam +
                ", offendingTeam=" + offendingTeam +
                ", player=" + player +
                ", droppingPlayer=" + droppingPlayer +
                ", createdDate=" + createdDate +
                ", deadline=" + deadline +
                ", vultureStatus=" + vultureStatus +
                '}';
    }
}
