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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="moneyId")
    private Long moneyId;
    @OneToOne
    @JoinColumn(name="teamId", referencedColumnName="teamId")
    private Team team;
    @Column(name="season")
    private int season;
    @Enumerated(EnumType.STRING)
    @Column(name="moneyType")
    private MoneyType moneyType;
    @Column(name="amount")
    private Integer amount;

    public Money() { }

    public Money(Team team, int season, MoneyType moneyType, int amount) {
        setTeam(team);
        setSeason(season);
        setMoneyType(moneyType);
        setAmount(amount);
    }

    public Long getMoneyId() {
        return moneyId;
    }

    public void setMoneyId(Long moneyId) {
        this.moneyId = moneyId;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
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

        if (season != money.season) return false;
        if (!moneyId.equals(money.moneyId)) return false;
        if (moneyType != money.moneyType) return false;
        if (team != null ? !team.equals(money.team) : money.team != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long hashMoneyId = moneyId != null ? (long)moneyId : 0;
        return (int) (hashMoneyId ^ (hashMoneyId >>> 32));
    }
}
