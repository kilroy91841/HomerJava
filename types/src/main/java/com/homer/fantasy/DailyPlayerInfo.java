package com.homer.fantasy;

import com.homer.PlayerStatus;
import com.homer.fantasy.key.DailyPlayerInfoKey;
import com.homer.mlb.Game;
import com.homer.mlb.Stats;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by arigolub on 1/29/15.
 */
@Entity
@Table(name="PLAYERTOTEAM")
public class DailyPlayerInfo {

	@EmbeddedId
	private DailyPlayerInfoKey dailyPlayerInfoKey;

	@OneToOne
	@JoinColumn(name="fantasyTeamId", referencedColumnName="teamId")
	private Team fantasyTeam;
	@OneToOne
	@JoinColumn(name="mlbTeamId", referencedColumnName="teamId")
	private Team mlbTeam;
	@OneToOne
	@JoinColumn(name="fantasyPositionId", referencedColumnName="positionId")
	private Position fantasyPosition;
	@OneToOne
	@JoinColumn(name="fantasyPlayerStatusCode", referencedColumnName="playerStatusCode")
	private PlayerStatus fantasyStatus;
	@OneToOne
	@JoinColumn(name="mlbPlayerStatusCode", referencedColumnName="playerStatusCode")
	private PlayerStatus mlbStatus;
	@OneToMany(cascade=CascadeType.PERSIST)
	@Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumns({
			@JoinColumn(name = "playerId", referencedColumnName="playerId"),
			@JoinColumn(name = "gameDate", referencedColumnName="gameDate")
	})
	private List<Stats> statsList;

	public DailyPlayerInfo() {
		this.dailyPlayerInfoKey = new DailyPlayerInfoKey();
	}

	public DailyPlayerInfoKey getDailyPlayerInfoKey() {
		return dailyPlayerInfoKey;
	}

	public void setDailyPlayerInfoKey(DailyPlayerInfoKey dailyPlayerInfoKey) {
		this.dailyPlayerInfoKey = dailyPlayerInfoKey;
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

	public void setPlayer(Player player) {
		this.getDailyPlayerInfoKey().setPlayer(player);
	}

	public void setDate(LocalDate date) {
		this.getDailyPlayerInfoKey().setDate(date);
	}

	public LocalDate getDate() {
		return this.getDailyPlayerInfoKey().getDate();
	}

	public void setFantasyPosition(Position fantasyPosition) {
		this.fantasyPosition = fantasyPosition;
	}

	public Position getFantasyPosition() {
		return fantasyPosition;
	}

	public void setStatsList(List<Stats> statsList) {
		this.statsList = statsList;
	}

	public List<Stats> getStatsList() {
		if(statsList == null) {
			statsList = new ArrayList<Stats>();
		}
		return statsList;
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
		StringBuilder sb = new StringBuilder();
		sb.append("DailyPlayerInfo{");
		sb.append("dailyPlayerInfoKey=" + dailyPlayerInfoKey);
		sb.append(", fantasyTeam=" + fantasyTeam);
		sb.append(", mlbTeam=" + mlbTeam);
		sb.append(", fantasyPosition=" + fantasyPosition);
		sb.append(", fantasyStatus=" + fantasyStatus);
		sb.append(", mlbStatus=" + mlbStatus);
		sb.append(", statsList=" + statsList);
		sb.append("}");
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DailyPlayerInfo that = (DailyPlayerInfo) o;

		if (this.getDailyPlayerInfoKey() != that.getDailyPlayerInfoKey()) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = fantasyTeam != null ? fantasyTeam.hashCode() : 0;
		result = 31 * result + getDailyPlayerInfoKey().hashCode();
		result = 31 * result + (mlbTeam != null ? mlbTeam.hashCode() : 0);
		result = 31 * result + (fantasyPosition != null ? fantasyPosition.hashCode() : 0);
		return result;
	}

}

