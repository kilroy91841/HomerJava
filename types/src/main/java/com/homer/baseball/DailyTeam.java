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
public class DailyTeam implements Parsable {

	private List<Player> players;
	private Team team;
	private Date date;

	public DailyTeam() { }

	public DailyTeam(ResultSet rs) {
		parse(rs);
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Player> getPlayers() {
		if(players == null) {
			players = new ArrayList<Player>();
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

	@Override
    public void parse(ResultSet rs) {
    	try {
    		if(rs.first()) {
    			team = new Team(rs);
    			rs.beforeFirst();
		    	while(rs.next()) {
		    		Player p = new Player(rs);
		    		getPlayers().add(p);
		    	}
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public void parse(ResultSet rs, String tableName) { }

}

