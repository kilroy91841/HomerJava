package com.homer.baseball;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 1/31/15.
 */
public class Trade {

    public static final TradeStatus DRAFT = new TradeStatus("DRAFT");
    public static final TradeStatus PROPOSED = new TradeStatus("PROPOSED");
    public static final TradeStatus CANCELLED = new TradeStatus("CANCELLED");
    public static final TradeStatus DECLINED = new TradeStatus("DECLINED");
    public static final TradeStatus ACCEPTED = new TradeStatus("ACCEPTED");

    private Team proposingTeam;
    private Team proposedToTeam;
    private Date createdDate;
    private Date deadline;
    private List<Tradable> proposingTeamAssets;
    private List<Tradable> proposedToTeamAssets;
    private TradeStatus tradeStatus;

    public Trade() { }

    public Trade(Team proposingTeam, Team proposedToTeam, Date createdDate, Date deadline,
                 List<Tradable> proposingTeamAssets, List<Tradable> proposedToTeamAssets, TradeStatus tradeStatus) {
        this.proposingTeam = proposingTeam;
        this.proposedToTeam = proposedToTeam;
        this.createdDate = createdDate;
        this.deadline = deadline;
        this.proposingTeamAssets = proposingTeamAssets;
        this.proposedToTeamAssets = proposedToTeamAssets;
        this.tradeStatus = tradeStatus;
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

    public List<Tradable> getProposingTeamAssets() {
        if(proposingTeamAssets == null) {
            proposingTeamAssets = new ArrayList<Tradable>();
        }
        return proposingTeamAssets;
    }

    public void setProposingTeamAssets(List<Tradable> proposingTeamAssets) {
        this.proposingTeamAssets = proposingTeamAssets;
    }

    public List<Tradable> getProposedToTeamAssets() {
        if(proposedToTeamAssets == null) {
            proposedToTeamAssets = new ArrayList<Tradable>();
        }
        return proposedToTeamAssets;
    }

    public void setProposedToTeamAssets(List<Tradable> proposedToTeamAssets) {
        this.proposedToTeamAssets = proposedToTeamAssets;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "proposingTeam=" + proposingTeam +
                ", proposedToTeam=" + proposedToTeam +
                ", createdDate=" + createdDate +
                ", deadline=" + deadline +
                ", proposingTeamAssets=" + proposingTeamAssets +
                ", proposedToTeamAssets=" + proposedToTeamAssets +
                ", tradeStatus=" + tradeStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trade trade = (Trade) o;

        if (!createdDate.equals(trade.createdDate)) return false;
        if (deadline != null ? !deadline.equals(trade.deadline) : trade.deadline != null) return false;
        if (!proposedToTeam.equals(trade.proposedToTeam)) return false;
        if (proposedToTeamAssets != null ? !proposedToTeamAssets.equals(trade.proposedToTeamAssets) : trade.proposedToTeamAssets != null)
            return false;
        if (!proposingTeam.equals(trade.proposingTeam)) return false;
        if (proposingTeamAssets != null ? !proposingTeamAssets.equals(trade.proposingTeamAssets) : trade.proposingTeamAssets != null)
            return false;
        if (!tradeStatus.equals(trade.tradeStatus)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = proposingTeam.hashCode();
        result = 31 * result + proposedToTeam.hashCode();
        result = 31 * result + createdDate.hashCode();
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (proposingTeamAssets != null ? proposingTeamAssets.hashCode() : 0);
        result = 31 * result + (proposedToTeamAssets != null ? proposedToTeamAssets.hashCode() : 0);
        result = 31 * result + tradeStatus.hashCode();
        return result;
    }

    public static class TradeStatus {

        private String name;

        private TradeStatus(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static TradeStatus get(String tradeStatusName) throws Exception {
            if(PROPOSED.getName().equals(tradeStatusName)) {
                return PROPOSED;
            } else if(CANCELLED.getName().equals(tradeStatusName)) {
                return CANCELLED;
            } else if(DECLINED.getName().equals(tradeStatusName)) {
                return DECLINED;
            } else if(ACCEPTED.getName().equals(tradeStatusName)) {
                return ACCEPTED;
            } else if(DRAFT.getName().equals(tradeStatusName)) {
                return DRAFT;
            }
            throw new Exception("Could not identify trade status with name " + tradeStatusName);
        }
    }

}