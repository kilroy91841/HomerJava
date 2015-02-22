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
    private Player firstBase;
    private Player secondBase;
    private Player thirdBase;
    private Player shortstop;
    private Player middleInfield;
    private Player cornerInfield;
    private List<Player> outfielders = new ArrayList<Player>();
    private List<Player> pitchers = new ArrayList<Player>();
    private Player utility;

    private List<Player> disabledList = new ArrayList<Player>();
    private List<Player> minorLeaguers = new ArrayList<Player>();
    private List<Player> suspendedList = new ArrayList<Player>();
    private List<Player> bench = new ArrayList<Player>();

    public Roster() { }

    public Roster(List<Player> players) {
        this.players = players;
        init();
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
                firstBase = p;
            } else if (Position.FANTASYSECONDBASE.equals(fantasyPosition)) {
                secondBase = p;
            } else if (Position.FANTASYTHIRDBASE.equals(fantasyPosition)) {
                thirdBase = p;
            } else if (Position.FANTASYSHORTSTOP.equals(fantasyPosition)) {
                shortstop = p;
            } else if (Position.FANTASYMIDDLEINFIELD.equals(fantasyPosition)) {
                middleInfield = p;
            } else if (Position.FANTASYCORNERINFIELD.equals(fantasyPosition)) {
                cornerInfield = p;
            } else if (Position.FANTASYOUTFIELD.equals(fantasyPosition)) {
                outfielders.add(p);
            } else if (Position.FANTASYUTILITY.equals(fantasyPosition)) {
                utility = p;
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

    public Player getFirstBase() {
        return firstBase;
    }

    public void setFirstBase(Player firstBase) {
        this.firstBase = firstBase;
    }

    public Player getSecondBase() {
        return secondBase;
    }

    public void setSecondBase(Player secondBase) {
        this.secondBase = secondBase;
    }

    public Player getThirdBase() {
        return thirdBase;
    }

    public void setThirdBase(Player thirdBase) {
        this.thirdBase = thirdBase;
    }

    public Player getShortstop() {
        return shortstop;
    }

    public void setShortstop(Player shortstop) {
        this.shortstop = shortstop;
    }

    public Player getMiddleInfield() {
        return middleInfield;
    }

    public void setMiddleInfield(Player middleInfield) {
        this.middleInfield = middleInfield;
    }

    public Player getCornerInfield() {
        return cornerInfield;
    }

    public void setCornerInfield(Player cornerInfield) {
        this.cornerInfield = cornerInfield;
    }

    public List<Player> getOutfielders() {
        return outfielders;
    }

    public void setOutfielders(List<Player> outfielders) {
        this.outfielders = outfielders;
    }

    public Player getUtility() {
        return utility;
    }

    public void setUtility(Player utility) {
        this.utility = utility;
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
}
