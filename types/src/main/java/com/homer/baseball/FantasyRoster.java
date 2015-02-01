package com.homer.baseball;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
* Created by arigolub on 1/29/15.
*/
public class FantasyRoster extends DailyTeam {

	private List<DailyPlayer> catchers;
	private DailyPlayer firstBase;
	private DailyPlayer secondBase;
	private DailyPlayer thirdBase;
	private DailyPlayer shortstop;
	private DailyPlayer middleInfield;
	private DailyPlayer cornerInfield;
	private List<DailyPlayer> outfielders;
	private List<DailyPlayer> pitchers;
	private DailyPlayer utility;

    public FantasyRoster(Team team, List<DailyPlayer> dailyPlayers) {
        setTeam(team);
        setPlayers(dailyPlayers);
        init();
    }

	public FantasyRoster() {
		init();
	}

	private void init() {
		catchers = new ArrayList<DailyPlayer>();
        outfielders = new ArrayList<DailyPlayer>();
        pitchers = new ArrayList<DailyPlayer>();
		for(DailyPlayer p : getPlayers()) {
			if(Position.FANTASYCATCHER.equals(p.getFantasyPosition())) {
				catchers.add(p);
			} else if(Position.FANTASYOUTFIELD.equals(p.getFantasyPosition())) {
				outfielders.add(p);
			} else if(Position.FANTASYPITCHER.equals(p.getFantasyPosition()) ||
				Position.FANTASYPITCHER.equals(p.getFantasyPosition())) {
				pitchers.add(p);
			} else if(Position.FANTASYFIRSTBASE.equals(p.getFantasyPosition())) {
				firstBase = p;
			} else if(Position.FANTASYSECONDBASE.equals(p.getFantasyPosition())) {
				secondBase = p;
			} else if(Position.FANTASYTHIRDBASE.equals(p.getFantasyPosition())) {
				thirdBase = p;
			} else if(Position.FANTASYSHORTSTOP.equals(p.getFantasyPosition())) {
				shortstop = p;
			} else if(Position.FANTASYMIDDLEINFIELD.equals(p.getFantasyPosition())) {
				middleInfield = p;
			} else if(Position.FANTASYCORNERINFIELD.equals(p.getFantasyPosition())) {
				cornerInfield = p;
			} else if(Position.FANTASYUTILITY.equals(p.getFantasyPosition())) {
				utility = p;
			}
		}
	}

    public List<DailyPlayer> getCatchers() {
        return catchers;
    }

    public void setCatchers(List<DailyPlayer> catchers) {
        this.catchers = catchers;
    }

    public DailyPlayer getFirstBase() {
        return firstBase;
    }

    public void setFirstBase(DailyPlayer firstBase) {
        this.firstBase = firstBase;
    }

    public DailyPlayer getSecondBase() {
        return secondBase;
    }

    public void setSecondBase(DailyPlayer secondBase) {
        this.secondBase = secondBase;
    }

    public DailyPlayer getThirdBase() {
        return thirdBase;
    }

    public void setThirdBase(DailyPlayer thirdBase) {
        this.thirdBase = thirdBase;
    }

    public DailyPlayer getShortstop() {
        return shortstop;
    }

    public void setShortstop(DailyPlayer shortstop) {
        this.shortstop = shortstop;
    }

    public DailyPlayer getMiddleInfield() {
        return middleInfield;
    }

    public void setMiddleInfield(DailyPlayer middleInfield) {
        this.middleInfield = middleInfield;
    }

    public DailyPlayer getCornerInfield() {
        return cornerInfield;
    }

    public void setCornerInfield(DailyPlayer cornerInfield) {
        this.cornerInfield = cornerInfield;
    }

    public List<DailyPlayer> getOutfielders() {
        return outfielders;
    }

    public void setOutfielders(List<DailyPlayer> outfielders) {
        this.outfielders = outfielders;
    }

    public List<DailyPlayer> getPitchers() {
        return pitchers;
    }

    public void setPitchers(List<DailyPlayer> pitchers) {
        this.pitchers = pitchers;
    }

    public DailyPlayer getUtility() {
        return utility;
    }

    public void setUtility(DailyPlayer utility) {
        this.utility = utility;
    }

    @Override
    public String toString() {
        return "FantasyRoster{" +
                "catchers=" + catchers +
                ", firstBase=" + firstBase +
                ", secondBase=" + secondBase +
                ", thirdBase=" + thirdBase +
                ", shortstop=" + shortstop +
                ", middleInfield=" + middleInfield +
                ", cornerInfield=" + cornerInfield +
                ", outfielders=" + outfielders +
                ", pitchers=" + pitchers +
                ", utility=" + utility +
                '}';
    }
}

