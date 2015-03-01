package com.homer.fantasy.key;

import com.homer.fantasy.FreeAgentAuction;
import com.homer.fantasy.FreeAgentAuctionBid;
import com.homer.fantasy.Team;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by arigolub on 2/28/15.
 */
@Embeddable
public class FreeAgentAuctionBidKey implements Serializable {
    @ManyToOne
    @JoinColumn(name="teamId", referencedColumnName="teamId")
    private Team team;
    @ManyToOne
    @JoinColumn(name="freeAgentAuctionId", referencedColumnName="freeAgentAuctionId")
    private FreeAgentAuction freeAgentAuction;


    public FreeAgentAuctionBidKey() { }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public FreeAgentAuction getFreeAgentAuction() {
        return freeAgentAuction;
    }

    public void setFreeAgentAuction(FreeAgentAuction freeAgentAuction) {
        this.freeAgentAuction = freeAgentAuction;
    }

    @Override
    public String toString() {
        return "FreeAgentAuctionBidKey{" +
                "team=" + team +
                ", freeAgentAuction=" + freeAgentAuction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FreeAgentAuctionBidKey that = (FreeAgentAuctionBidKey) o;

        if (!freeAgentAuction.equals(that.freeAgentAuction)) return false;
        if (!team.equals(that.team)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = team.hashCode();
        result = 31 * result + freeAgentAuction.hashCode();
        return result;
    }
}
