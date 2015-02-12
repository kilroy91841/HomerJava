package com.homer.fantasy;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arigolub on 2/1/15.
 */
@Entity
@Table(name="FREEAGENTAUCTION")
public class FreeAgentAuction {

    public enum Status {
        REQUESTED,
        ACTIVE,
        DENIED,
        CANCELLED,
        COMPLETE
    }

    @Id
    @Column(name="freeAgentAuctionId")
    private long freeAgentAuctionId;
    @OneToOne
    @JoinColumn(name="requestingTeamId", referencedColumnName="teamId")
    private Team requestingTeam;
    @OneToOne
    @JoinColumn(name="playerId", referencedColumnName="playerId")
    private Player player;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="createdDate")
    private Date createdDate;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="modifiedDate")
    private Date modifiedDate;
    @Temporal(value=TemporalType.TIMESTAMP)
    @Column(name="deadline")
    private Date deadline;
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;

    public FreeAgentAuction() { }

    public FreeAgentAuction(Team requestingTeam, Player player, Date createdDate, Date modifiedDate, Date deadline, Status status) {
        this.requestingTeam = requestingTeam;
        this.player = player;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deadline = deadline;
        this.status = status;
    }

    public Team getRequestingTeam() {
        return requestingTeam;
    }

    public void setRequestingTeam(Team requestingTeam) {
        this.requestingTeam = requestingTeam;
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

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FreeAgentAuction{" +
                "freeAgentAuctionId=" + freeAgentAuctionId +
                ", requestingTeam=" + requestingTeam +
                ", player=" + player +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", deadline=" + deadline +
                ", vultureStatus=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FreeAgentAuction that = (FreeAgentAuction) o;

        if (freeAgentAuctionId != that.freeAgentAuctionId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (freeAgentAuctionId ^ (freeAgentAuctionId >>> 32));
    }
}
