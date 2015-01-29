package com.homer.baseball;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 * Created by arigolub on 1/29/15.
 */
public class FantasyRoster extends DailyTeam {

	private List<Player> catchers;
	private Player firstBase;
	private Player secondBase;
	private Player thirdBase;
	private Player shortstop;
	private Player middleInfield;
	private Player cornerInfield;
	private List<Player> outfielders;
	private List<Player> pitchers;
	private Player utility;

	public FantasyRoster(ResultSet rs) {
		super(rs);
		init();
	}

	private void init() {
		catchers = new ArrayList<Player>();
		catchers = new ArrayList<Player>();
		catchers = new ArrayList<Player>();
		for(Player p : getPlayers()) {
			if(Position.CATCHER.equals(p.getPosition())) {
				catchers.add(p);
			} else if(Position.LEFTFIELD.equals(p.getPosition()) ||
				Position.RIGHTFIELD.equals(p.getPosition()) ||
				Position.CENTERFIELD.equals(p.getPosition())) {
				outfielders.add(p);
			} else if(Position.STARTINGPITCHER.equals(p.getPosition()) ||
				Position.RELIEFPITCHER.equals(p.getPosition())) {
				pitchers.add(p);
			} else if(Position.FIRSTBASE.equals(p.getPosition())) {
				firstBase = p;
			} else if(Position.SECONDBASE.equals(p.getPosition())) {
				secondBase = p;
			} else if(Position.THIRDBASE.equals(p.getPosition())) {
				thirdBase = p;
			} else if(Position.SHORTSTOP.equals(p.getPosition())) {
				shortstop = p;
			//} else if(Position.MIDDLEINFIELD.equals(p.getPosition())) {
			//	middleInfield = p;
			//} else if(Position.CORNERINFIELD.equals(p.getPosition())) {
			//	cornerInfield = p;
			//} else if(Position.UTILITY.equals(p.getPosition())) {
			//	utility = p;
			}
		}
	}

}

