package com.homer.fantasy;

import com.homer.PlayerStatus;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arigolub on 2/22/15.
 */
public class Roster {

    private List<Player> players = new ArrayList<Player>();

    private List<Player> catchers = new ArrayList<Player>();
    private List<Player> firstBase = new ArrayList<Player>();
    private List<Player> secondBase = new ArrayList<Player>();
    private List<Player> thirdBase = new ArrayList<Player>();
    private List<Player> shortstop = new ArrayList<Player>();
    private List<Player> middleInfield = new ArrayList<Player>();
    private List<Player> cornerInfield = new ArrayList<Player>();
    private List<Player> outfielders = new ArrayList<Player>();
    private List<Player> pitchers = new ArrayList<Player>();
    private List<Player> utility = new ArrayList<Player>();

    private List<Player> disabledList = new ArrayList<Player>();
    private List<Player> minorLeaguers = new ArrayList<Player>();
    private List<Player> suspendedList = new ArrayList<Player>();
    private List<Player> bench = new ArrayList<Player>();

    private Team team;

    public Roster() { }

    public Roster(List<Player> players) {
        this.players = players;
        init();
    }

    public Roster withTeam(Team team) {
        this.team = team;
        return this;
    }

    private void init() {
        for(Player p : players) {
            Position fantasyPosition = p.getDailyPlayerInfoList().get(0).getFantasyPosition();
            PlayerStatus fantasyStatus = p.getDailyPlayerInfoList().get(0).getFantasyStatus();

            if(PlayerStatus.MINORS.equals(fantasyStatus)) {
                minorLeaguers.add(p);
            } else if (PlayerStatus.SUSPENDED.equals(fantasyStatus)) {
                suspendedList.add(p);
            } else if (Position.FANTASYBENCH.equals(fantasyPosition)) {
                bench.add(p);
            } else if (Position.FANTASYDISABLEDLIST.equals(fantasyPosition)) {
                disabledList.add(p);
            } else if (Position.FANTASYCATCHER.equals(fantasyPosition)) {
                catchers.add(p);
            } else if (Position.FANTASYFIRSTBASE.equals(fantasyPosition)) {
                firstBase.add(p);
            } else if (Position.FANTASYSECONDBASE.equals(fantasyPosition)) {
                secondBase.add(p);
            } else if (Position.FANTASYTHIRDBASE.equals(fantasyPosition)) {
                thirdBase.add(p);
            } else if (Position.FANTASYSHORTSTOP.equals(fantasyPosition)) {
                shortstop.add(p);
            } else if (Position.FANTASYMIDDLEINFIELD.equals(fantasyPosition)) {
                middleInfield.add(p);
            } else if (Position.FANTASYCORNERINFIELD.equals(fantasyPosition)) {
                cornerInfield.add(p);
            } else if (Position.FANTASYOUTFIELD.equals(fantasyPosition)) {
                outfielders.add(p);
            } else if (Position.FANTASYUTILITY.equals(fantasyPosition)) {
                utility.add(p);
            } else if (Position.FANTASYPITCHER.equals(fantasyPosition)) {
                pitchers.add(p);
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getCatchers() {
        return catchers;
    }

    public void setCatchers(List<Player> catchers) {
        this.catchers = catchers;
    }

    public List<Player> getFirstBase() {
        return firstBase;
    }

    public void setFirstBase(List<Player> firstBase) {
        this.firstBase = firstBase;
    }

    public List<Player> getSecondBase() {
        return secondBase;
    }

    public void setSecondBase(List<Player> secondBase) {
        this.secondBase = secondBase;
    }

    public List<Player> getThirdBase() {
        return thirdBase;
    }

    public void setThirdBase(List<Player> thirdBase) {
        this.thirdBase = thirdBase;
    }

    public List<Player> getShortstop() {
        return shortstop;
    }

    public void setShortstop(List<Player> shortstop) {
        this.shortstop = shortstop;
    }

    public List<Player> getMiddleInfield() {
        return middleInfield;
    }

    public void setMiddleInfield(List<Player> middleInfield) {
        this.middleInfield = middleInfield;
    }

    public List<Player> getCornerInfield() {
        return cornerInfield;
    }

    public void setCornerInfield(List<Player> cornerInfield) {
        this.cornerInfield = cornerInfield;
    }

    public List<Player> getUtility() {
        return utility;
    }

    public void setUtility(List<Player> utility) {
        this.utility = utility;
    }

    public List<Player> getOutfielders() {
        return outfielders;
    }

    public void setOutfielders(List<Player> outfielders) {
        this.outfielders = outfielders;
    }

    public List<Player> getPitchers() {
        return pitchers;
    }

    public void setPitchers(List<Player> pitchers) {
        this.pitchers = pitchers;
    }

    public List<Player> getDisabledList() {
        return disabledList;
    }

    public void setDisabledList(List<Player> disabledList) {
        this.disabledList = disabledList;
    }

    public List<Player> getMinorLeaguers() {
        return minorLeaguers;
    }

    public void setMinorLeaguers(List<Player> minorLeaguers) {
        this.minorLeaguers = minorLeaguers;
    }

    public List<Player> getSuspendedList() {
        return suspendedList;
    }

    public void setSuspendedList(List<Player> suspendedList) {
        this.suspendedList = suspendedList;
    }

    public List<Player> getBench() {
        return bench;
    }

    public void setBench(List<Player> bench) {
        this.bench = bench;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
