package com.homer.baseball;

import java.util.Date;
import java.util.List;
import java.sql.ResultSet;

import com.homer.Parsable;
import com.homer.SportType;

/**
 * Created by arigolub on 1/29/15.
 */
public class DailyPlayer implements Parsable {

	private Player player;
	private Team fantasyTeam;
	private Team mlbTeam;
	private Date date;
	private List<Game> games;

	public DailyPlayer() { }

	public DailyPlayer(ResultSet rs) {
		parse(rs);
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public void setFantasyTeam(Team fantasyTeam) {
		this.fantasyTeam = fantasyTeam;
	}

	public Team getFantasyTeam() {
		return fantasyTeam;
	}

	public void setMlbTeam(Team mlbTeam) {
		this.mlbTeam = mlbTeam;
	}

	public Team getMlbTeam() {
		return mlbTeam;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public List<Game> getGames() {
		return games;
	}

	@Override
    public void parse(ResultSet rs) {
    	setMlbTeam(new Team(rs, "mlbTeam"));
    	setFantasyTeam(new Team(rs, "fantasyTeam"));
    	setPlayer(new Player(rs));
    }

    @Override
    public void parse(ResultSet rs, String tableName) { }

}

