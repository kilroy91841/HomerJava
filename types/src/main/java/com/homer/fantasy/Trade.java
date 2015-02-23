package com.homer.fantasy;

import com.homer.util.LocalDateTimePersistenceConverter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @Fetch(FetchMode.SELECT)
    @JoinColumns({
            @JoinColumn(name="tradeId", referencedColumnName="tradeId")
    })
    private Set<TradeAsset> tradeAssets;

    public Trade() { }

    public Trade(Team proposingTeam, Team proposedToTeam, LocalDateTime createdDate, LocalDateTime deadline,
                 Set<TradeAsset> tradeAssets, Status tradeStatus) {
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

    public Set<TradeAsset> getTradeAssets() {
        if(tradeAssets == null) {
            tradeAssets = new HashSet<TradeAsset>();
        }
        return tradeAssets;
    }

    public void setTradeAssets(Set<TradeAsset> tradeAssets) {
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
                ", tradeStatus=" + tradeStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (tradeId != trade.tradeId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return tradeId;
    }
}
