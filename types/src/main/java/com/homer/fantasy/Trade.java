package com.homer.fantasy;

import com.homer.util.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 1/31/15.
 */
@Entity
@Table(name="TRADE")
public class Trade {

    public enum Status {
        DRAFT,
        PROPOSED,
        CANCELLED,
        DECLINED,
        ACCEPTED
    }

    @Id
    @Column(name="tradeId")
    private int tradeId;
    @OneToOne
    @JoinColumn(name="proposingTeamId", referencedColumnName="teamId")
    private Team proposingTeam;
    @OneToOne
    @JoinColumn(name="proposedToTeamId", referencedColumnName="teamId")
    private Team proposedToTeam;
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="createdDate")
    private LocalDateTime createdDate;
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="deadline")
    private LocalDateTime deadline;
    @Enumerated(EnumType.STRING)
    @Column(name="tradeStatus")
    private Status tradeStatus;
    @OneToMany
    @JoinColumns({
            @JoinColumn(name="tradeId", referencedColumnName="tradeId")
    })
    private List<TradeAsset> tradeAssets;

    public Trade() { }

    public Trade(Team proposingTeam, Team proposedToTeam, LocalDateTime createdDate, LocalDateTime deadline,
                 List<TradeAsset> tradeAssets, Status tradeStatus) {
        this.proposingTeam = proposingTeam;
        this.proposedToTeam = proposedToTeam;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.tradeAssets = tradeAssets;
        this.tradeStatus = tradeStatus;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public Team getProposingTeam() {
        return proposingTeam;
    }

    public void setProposingTeam(Team proposingTeam) {
        this.proposingTeam = proposingTeam;
    }

    public Team getProposedToTeam() {
        return proposedToTeam;
    }

    public void setProposedToTeam(Team proposedToTeam) {
        this.proposedToTeam = proposedToTeam;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate (LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline (LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public List<TradeAsset> getTradeAssets() {
        return tradeAssets;
    }

    public void setTradeAssets(List<TradeAsset> tradeAssets) {
        this.tradeAssets = tradeAssets;
    }

    public Status getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Status tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "proposingTeam=" + proposingTeam +
                ", proposedToTeam=" + proposedToTeam +
                ", createdDate=" + createdDate +
                ", deadline=" + deadline +
                ", tradeAssets=" + tradeAssets +
                ", tradeStatus=" + tradeStatus +
                '}';
    }

}
