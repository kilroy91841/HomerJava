package com.homer.fantasy;

import javax.persistence.*;
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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdDate")
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="deadline")
    private Date deadline;
    @Enumerated(EnumType.STRING)
    @Column(name="tradeStatus")
    private Status tradeStatus;
    @OneToMany
    @JoinColumns({
            @JoinColumn(name="tradeId", referencedColumnName="tradeId")
    })
    private List<TradeAsset> tradeAssets;

    public Trade() { }

    public Trade(Team proposingTeam, Team proposedToTeam, Date createdDate, Date deadline,
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
