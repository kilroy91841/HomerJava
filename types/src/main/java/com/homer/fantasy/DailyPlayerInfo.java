package com.homer.fantasy;

import com.homer.JsonIgnore;
import com.homer.PlayerStatus;
import com.homer.mlb.Game;
import com.homer.mlb.Stats;
import com.homer.util.LocalDatePersistenceConverter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="playerToTeamId")
	private long dailyPlayerInfoId;
	@ManyToOne
	@JoinColumn(name="playerId", referencedColumnName="playerId")
	private Player player;
	@Convert(converter=LocalDatePersistenceConverter.class)
	@Column(name="gameDate")
	private LocalDate date;
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
	@OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
	@Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@JoinColumn(name="playerToTeamId", referencedColumnName="playerToTeamId")
	@org.hibernate.annotations.OrderBy(clause = "gameDate desc")
	@Fetch(FetchMode.SELECT)
	private List<Stats> statsList;

	public DailyPlayerInfo() { }

	public long getDailyPlayerInfoId() {
		return dailyPlayerInfoId;
	}

	public void setDailyPlayerInfoId(long dailyPlayerInfoId) {
		this.dailyPlayerInfoId = dailyPlayerInfoId;
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

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getDate() {
		return this.date;
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
		sb.append("dailyPlayerInfoId=" + dailyPlayerInfoId);
		sb.append(", playerId=" + player.getPlayerId());
		sb.append(", date=" + date);
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

		if(!this.getPlayer().equals(that.getPlayer())) return false;
		if(!this.getDate().equals(that.getDate())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = fantasyTeam != null ? fantasyTeam.hashCode() : 0;
		result = 31 * result + getPlayer().hashCode();
		result = 31 * result + date.hashCode();
		result = 31 * result + (mlbTeam != null ? mlbTeam.hashCode() : 0);
		result = 31 * result + (fantasyPosition != null ? fantasyPosition.hashCode() : 0);
		return result;
	}

	public DailyPlayerInfo copyAndIncrementDay() {
		DailyPlayerInfo copy = new DailyPlayerInfo();
		copy.setFantasyTeam(this.fantasyTeam);
		copy.setMlbTeam(this.mlbTeam);
		copy.setFantasyPosition(this.fantasyPosition);
		copy.setFantasyStatus(this.fantasyStatus);
		copy.setMlbStatus(this.mlbStatus);
		copy.setPlayer(this.player);
		copy.setDate(this.date.plusDays(1));
		return copy;
	}

}

