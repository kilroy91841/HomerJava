package com.homer.fantasy.wrapper;

import com.homer.fantasy.DailyPlayerInfo;
import com.homer.fantasy.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 1/29/15.
 */
public class DailyTeam {

	private List<DailyPlayerInfo> players;
	private Team team;
	private Date date;

	public DailyTeam() { }

	public DailyTeam(Team team, List<DailyPlayerInfo> players) {
		setTeam(team);
		setPlayers(players);
	}

	public void setPlayers(List<DailyPlayerInfo> players) {
		this.players = players;
	}

	public List<DailyPlayerInfo> getPlayers() {
		if(players == null) {
			players = new ArrayList<DailyPlayerInfo>();
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

