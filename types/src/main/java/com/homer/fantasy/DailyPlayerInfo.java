package com.homer.fantasy;

import com.homer.PlayerStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 1/29/15.
 */
public class DailyPlayerInfo {

	private Team fantasyTeam;
	private Team mlbTeam;
	private Date date;
	private Position fantasyPosition;
	private PlayerStatus fantasyStatus;
	private PlayerStatus mlbStatus;
	private List<Game> games;

	public DailyPlayerInfo() { }

	public DailyPlayerInfo(Team fantasyTeam, Team mlbTeam, Date date, Position fantasyPosition, List<Game> games) {
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

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setFantasyPosition(Position fantasyPosition) {
		this.fantasyPosition = fantasyPosition;
	}

	public Position getFantasyPosition() {
		return fantasyPosition;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public List<Game> getGames() {
		return games;
	}

	public PlayerStatus getFantasyStatus() {
		return fantasyStatus;
	}

	public void setFantasyStatus(PlayerStatus fantasyStatus) {
		this.fantasyStatus = fantasyStatus;
	}

	public PlayerStatus getMlbStatus() {
		return mlbStatus;
	}

	public void setMlbStatus(PlayerStatus mlbStatus) {
		this.mlbStatus = mlbStatus;
	}

	@Override
	public String toString() {
		return "DailyPlayer{" +
				"fantasyTeam=" + fantasyTeam +
				", mlbTeam=" + mlbTeam +
				", date=" + date +
				", fantasyPosition=" + fantasyPosition +
				", games=" + games +
				", fantasyStatus=" + fantasyStatus +
				", mlbStatus=" + mlbStatus +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DailyPlayerInfo that = (DailyPlayerInfo) o;

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

