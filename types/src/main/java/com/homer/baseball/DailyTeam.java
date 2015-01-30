package com.homer.baseball;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;

import com.homer.Parsable;
import com.homer.SportType;

/**
 * Created by arigolub on 1/29/15.
 */
public class DailyTeam {

	private List<DailyPlayer> players;
	private Team team;
	private Date date;

	public DailyTeam() { }

	public DailyTeam(Team team, List<DailyPlayer> players) {
		setTeam(team);
		setPlayers(players);
	}

	public void setPlayers(List<DailyPlayer> players) {
		this.players = players;
	}

	public List<DailyPlayer> getPlayers() {
		if(players == null) {
			players = new ArrayList<DailyPlayer>();
		}
		return players;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Team getTeam() {
		return team;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

}

