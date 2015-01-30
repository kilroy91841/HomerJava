package com.homer.baseball;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;

import com.homer.Parsable;
import com.homer.SportType;

/**
 * Created by arigolub on 1/29/15.
 */
public class DailyPlayer extends Player {

	private Team fantasyTeam;
	private Team mlbTeam;
	private Date date;
	private Position fantasyPosition;
	private List<Game> games;

	public DailyPlayer() { }

	public DailyPlayer(long playerId, String playerName, Position primaryPosition, 
			Team fantasyTeam, Team mlbTeam, Date date, Position fantasyPosition, List<Game> games) {
		super(playerId, playerName, primaryPosition);
		setFantasyTeam(fantasyTeam);
		setMlbTeam(mlbTeam);
		setDate(date);
		setFantasyPosition(fantasyPosition);
		setGames(games);
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

	public void setDate(Date date) { this.date = date; }

	public Date getDate() {
		return date;
	}

	public void setFantasyPosition(Position fantasyPosition) { this.fantasyPosition = fantasyPosition; }

	public Position getFantasyPosition() { return fantasyPosition; }

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public List<Game> getGames() {
		return games;
	}

	@Override
	public String toString() {
		return "DailyPlayer{" +
				"fantasyTeam=" + fantasyTeam +
				", mlbTeam=" + mlbTeam +
				", date=" + date +
				", fantasyPosition=" + fantasyPosition +
				", games=" + games +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		DailyPlayer that = (DailyPlayer) o;

		if (date != null ? !date.equals(that.date) : that.date != null) return false;
		if (fantasyPosition != null ? !fantasyPosition.equals(that.fantasyPosition) : that.fantasyPosition != null)
			return false;
		if (fantasyTeam != null ? !fantasyTeam.equals(that.fantasyTeam) : that.fantasyTeam != null) return false;
		if (games != null ? !games.equals(that.games) : that.games != null) return false;
		if (mlbTeam != null ? !mlbTeam.equals(that.mlbTeam) : that.mlbTeam != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = fantasyTeam != null ? fantasyTeam.hashCode() : 0;
		result = 31 * result + (mlbTeam != null ? mlbTeam.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (fantasyPosition != null ? fantasyPosition.hashCode() : 0);
		result = 31 * result + (games != null ? games.hashCode() : 0);
		return result;
	}
}

