package com.homer.fantasy;

import com.homer.util.LocalDateTimePersistenceConverter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="freeAgentAuctionId")
    private int freeAgentAuctionId;
    @OneToOne
    @JoinColumn(name="requestingTeamId", referencedColumnName="teamId")
    private Team requestingTeam;
    @OneToOne
    @JoinColumn(name="playerId", referencedColumnName="playerId")
    private Player player;
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="createdDate")
    private LocalDateTime createdDate;
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="modifiedDate")
    private LocalDateTime modifiedDate;
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="deadline")
    private LocalDateTime deadline;
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;
    @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name="freeAgentAuctionId", referencedColumnName="freeAgentAuctionId")
    @org.hibernate.annotations.OrderBy(clause = "amount desc")
    @Fetch(FetchMode.SELECT)
    private Set<FreeAgentAuctionBid> freeAgentAuctionBids;

    public FreeAgentAuction() { }

    public FreeAgentAuction(Team requestingTeam, Player player, LocalDateTime createdDate, LocalDateTime modifiedDate, LocalDateTime deadline, Status status) {
        this.requestingTeam = requestingTeam;
        this.player = player;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deadline = deadline;
        this.status = status;
    }

    public int getFreeAgentAuctionId() {
        return freeAgentAuctionId;
    }

    public void setFreeAgentAuctionId(int freeAgentAuctionId) {
        this.freeAgentAuctionId = freeAgentAuctionId;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<FreeAgentAuctionBid> getFreeAgentAuctionBids() {
        if(freeAgentAuctionBids == null) {
            freeAgentAuctionBids = new HashSet<FreeAgentAuctionBid>();
        }
        return freeAgentAuctionBids;
    }

    public void setFreeAgentAuctionBids(Set<FreeAgentAuctionBid> freeAgentAuctionBids) {
        this.freeAgentAuctionBids = freeAgentAuctionBids;
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
                ", freeAgentAuctionBidCount=" + getFreeAgentAuctionBids().size() +
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
        return freeAgentAuctionId;
    }
}
