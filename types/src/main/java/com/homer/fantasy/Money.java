package com.homer.fantasy;

import javax.persistence.*;

/**
 * Created by arigolub on 1/31/15.
 */
@Entity
@Table(name="MONEY")
public class Money {

    public enum MoneyType {
        MAJORLEAGUEDRAFT,
        FREEAGENTAUCTION
    }

    @Id
    @Column(name="moneyId")
    private long moneyId;
    @OneToOne
    @JoinColumn(name="teamId", referencedColumnName="teamId")
    private Team team;
    @Column(name="season")
    private int season;
    @Enumerated(EnumType.STRING)
    @Column(name="moneyType")
    private MoneyType moneyType;
    @Column(name="amount")
    private int amount;

    public Money() { }

    public Money(Team team, int season, MoneyType moneyType, int amount) {
        setTeam(team);
        setSeason(season);
        setMoneyType(moneyType);
        setAmount(amount);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public MoneyType getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(MoneyType moneyType) {
        this.moneyType = moneyType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Money{" +
                "moneyId=" + moneyId +
                ", team=" + team +
                ", season=" + season +
                ", moneyType=" + moneyType +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (moneyId != money.moneyId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (moneyId ^ (moneyId >>> 32));
    }
}
