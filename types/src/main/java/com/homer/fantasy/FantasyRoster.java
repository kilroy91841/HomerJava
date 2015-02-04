package com.homer.fantasy;

import java.util.ArrayList;
import java.util.List;

/**
* Created by arigolub on 1/29/15.
*/
public class FantasyRoster extends DailyTeam {

	private List<DailyPlayerInfo> catchers;
	private DailyPlayerInfo firstBase;
	private DailyPlayerInfo secondBase;
	private DailyPlayerInfo thirdBase;
	private DailyPlayerInfo shortstop;
	private DailyPlayerInfo middleInfield;
	private DailyPlayerInfo cornerInfield;
	private List<DailyPlayerInfo> outfielders;
	private List<DailyPlayerInfo> pitchers;
	private DailyPlayerInfo utility;

    public FantasyRoster(Team team, List<DailyPlayerInfo> dailyPlayerInfos) {
        setTeam(team);
        setPlayers(dailyPlayerInfos);
        init();
    }

	public FantasyRoster() {
		init();
	}

	private void init() {
		catchers = new ArrayList<DailyPlayerInfo>();
        outfielders = new ArrayList<DailyPlayerInfo>();
        pitchers = new ArrayList<DailyPlayerInfo>();
		for(DailyPlayerInfo p : getPlayers()) {
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

    public List<DailyPlayerInfo> getCatchers() {
        return catchers;
    }

    public void setCatchers(List<DailyPlayerInfo> catchers) {
        this.catchers = catchers;
    }

    public DailyPlayerInfo getFirstBase() {
        return firstBase;
    }

    public void setFirstBase(DailyPlayerInfo firstBase) {
        this.firstBase = firstBase;
    }

    public DailyPlayerInfo getSecondBase() {
        return secondBase;
    }

    public void setSecondBase(DailyPlayerInfo secondBase) {
        this.secondBase = secondBase;
    }

    public DailyPlayerInfo getThirdBase() {
        return thirdBase;
    }

    public void setThirdBase(DailyPlayerInfo thirdBase) {
        this.thirdBase = thirdBase;
    }

    public DailyPlayerInfo getShortstop() {
        return shortstop;
    }

    public void setShortstop(DailyPlayerInfo shortstop) {
        this.shortstop = shortstop;
    }

    public DailyPlayerInfo getMiddleInfield() {
        return middleInfield;
    }

    public void setMiddleInfield(DailyPlayerInfo middleInfield) {
        this.middleInfield = middleInfield;
    }

    public DailyPlayerInfo getCornerInfield() {
        return cornerInfield;
    }

    public void setCornerInfield(DailyPlayerInfo cornerInfield) {
        this.cornerInfield = cornerInfield;
    }

    public List<DailyPlayerInfo> getOutfielders() {
        return outfielders;
    }

    public void setOutfielders(List<DailyPlayerInfo> outfielders) {
        this.outfielders = outfielders;
    }

    public List<DailyPlayerInfo> getPitchers() {
        return pitchers;
    }

    public void setPitchers(List<DailyPlayerInfo> pitchers) {
        this.pitchers = pitchers;
    }

    public DailyPlayerInfo getUtility() {
        return utility;
    }

    public void setUtility(DailyPlayerInfo utility) {
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

