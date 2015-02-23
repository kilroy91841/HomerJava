package com.homer.fantasy;

import javax.persistence.*;

/**
 * Created by arigolub on 2/12/15.
 */
@Entity
@Table(name="TRADEASSET")
public class TradeAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="tradeAssetId")
    private long tradeAssetId;
    @ManyToOne
    @JoinColumn(name="tradeId", referencedColumnName="tradeId")
    private Trade trade;
    @OneToOne
    @JoinColumn(name="teamId",referencedColumnName="teamId")
    private Team team;
    @OneToOne
    @JoinColumn(name="playerId",referencedColumnName="playerId")
    private Player player;
    @OneToOne
    @JoinColumn(name="moneyId",referencedColumnName="moneyId")
    private Money money;
    @OneToOne
    @JoinColumn(name="minorLeagueDraftPickId",referencedColumnName="minorLeagueDraftPickId")
    private MinorLeagueDraftPick minorLeagueDraftPick;

    public TradeAsset() { }

    public long getTradeAssetId() {
        return tradeAssetId;
    }

    public void setTradeAssetId(long tradeAssetId) {
        this.tradeAssetId = tradeAssetId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public MinorLeagueDraftPick getMinorLeagueDraftPick() {
        return minorLeagueDraftPick;
    }

    public void setMinorLeagueDraftPick(MinorLeagueDraftPick minorLeagueDraftPick) {
        this.minorLeagueDraftPick = minorLeagueDraftPick;
    }

    @Override
    public String toString() {
        return "TradeAsset{" +
                "tradeAssetId=" + tradeAssetId +
                ", trade=" + trade +
                ", team=" + team +
                ", player=" + player +
                ", money=" + money +
                ", minorLeagueDraftPick=" + minorLeagueDraftPick +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeAsset that = (TradeAsset) o;

        if (tradeAssetId != that.tradeAssetId) return false;
        if (trade != that.trade) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (tradeAssetId ^ (tradeAssetId >>> 32));
        result = 31 * result + (trade != null ? trade.hashCode() : 0);
        result = 31 * result + team.hashCode();
        result = 31 * result + (player != null ? player.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (minorLeagueDraftPick != null ? minorLeagueDraftPick.hashCode() : 0);
        return result;
    }
}
