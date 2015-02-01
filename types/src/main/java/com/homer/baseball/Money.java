package com.homer.baseball;

/**
 * Created by arigolub on 1/31/15.
 */
public class Money implements Tradable {

    public static final MoneyType MAJORLEAGUEDRAFT = new MoneyType("MAJORLEAGUEDRAFT");
    public static final MoneyType FREEAGENTAUCTION = new MoneyType("FREEAGENTAUCTION");

    private Team team;
    private int season;
    private MoneyType moneyType;
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
                "team=" + team +
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

        if (amount != money.amount) return false;
        if (season != money.season) return false;
        if (!moneyType.equals(money.moneyType)) return false;
        if (!team.equals(money.team)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = team.hashCode();
        result = 31 * result + season;
        result = 31 * result + moneyType.hashCode();
        result = 31 * result + amount;
        return result;
    }

    public static class MoneyType {

        private String typeName;

        private MoneyType(String typeName) {
            this.typeName = typeName;
        }

        public static MoneyType get(String typeName) {
            if(typeName.equals(MAJORLEAGUEDRAFT.typeName)) {
                return MAJORLEAGUEDRAFT;
            } else {
                return FREEAGENTAUCTION;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MoneyType moneyType = (MoneyType) o;

            if (!typeName.equals(moneyType.typeName)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return typeName.hashCode();
        }

        @Override
        public String toString() {
            return "MoneyType{" +
                    "typeName='" + typeName + '\'' +
                    '}';
        }
    }
}
