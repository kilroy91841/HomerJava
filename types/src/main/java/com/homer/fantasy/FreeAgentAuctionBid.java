package com.homer.fantasy;

import com.homer.fantasy.key.FreeAgentAuctionBidKey;
import com.homer.util.LocalDateTimePersistenceConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by arigolub on 2/28/15.
 */
@Entity
@Table(name="FREEAGENTAUCTIONBID")
public class FreeAgentAuctionBid {

    @EmbeddedId
    private FreeAgentAuctionBidKey freeAgentAuctionBidKey;

    @Column(name="amount")
    private int amount;

    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="createdDate")
    private LocalDateTime createdDate;

    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="modifiedDate")
    private LocalDateTime modifiedDate;

    public FreeAgentAuctionBid() { this.freeAgentAuctionBidKey = new FreeAgentAuctionBidKey(); }

    public FreeAgentAuctionBidKey getFreeAgentAuctionBidKey() {
        return freeAgentAuctionBidKey;
    }

    public void setFreeAgentAuctionBidKey(FreeAgentAuctionBidKey freeAgentAuctionBidKey) {
        this.freeAgentAuctionBidKey = freeAgentAuctionBidKey;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public Team getTeam() {
        return this.freeAgentAuctionBidKey.getTeam();
    }

    @Override
    public String toString() {
        return "FreeAgentAuctionBid{" +
                "freeAgentAuctionBidKey=" + freeAgentAuctionBidKey +
                ", amount=" + amount +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FreeAgentAuctionBid that = (FreeAgentAuctionBid) o;

        if (!freeAgentAuctionBidKey.equals(that.freeAgentAuctionBidKey)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return freeAgentAuctionBidKey.hashCode();
    }
}
