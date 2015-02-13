package com.homer.fantasy.mongo.types;

/**
 * Created by arigolub on 2/13/15.
 */
public class MongoPlayerHistory {

    private int year;
    private int salary;
    private int draft_team;
    private int fantasy_team;
    private int keeper_team;
    private boolean locked_up;
    private boolean minor_leaguer;
    private int contract_year;

    public MongoPlayerHistory() { }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getDraft_team() {
        return draft_team;
    }

    public void setDraft_team(int draft_team) {
        this.draft_team = draft_team;
    }

    public int getFantasy_team() {
        return fantasy_team;
    }

    public void setFantasy_team(int fantasy_team) {
        this.fantasy_team = fantasy_team;
    }

    public int getKeeper_team() {
        return keeper_team;
    }

    public void setKeeper_team(int keeper_team) {
        this.keeper_team = keeper_team;
    }

    public boolean isLocked_up() {
        return locked_up;
    }

    public void setLocked_up(boolean locked_up) {
        this.locked_up = locked_up;
    }

    public boolean isMinor_leaguer() {
        return minor_leaguer;
    }

    public void setMinor_leaguer(boolean minor_leaguer) {
        this.minor_leaguer = minor_leaguer;
    }

    public int getContract_year() {
        return contract_year;
    }

    public void setContract_year(int contract_year) {
        this.contract_year = contract_year;
    }
}
