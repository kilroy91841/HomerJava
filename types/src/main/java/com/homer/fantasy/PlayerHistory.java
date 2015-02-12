package com.homer.fantasy;

import com.homer.fantasy.key.PlayerHistoryKey;

import javax.persistence.*;

/**
 * Created by arigolub on 1/30/15.
 */
@Entity
@Table(name="PLAYERHISTORY")
public class PlayerHistory {

	@EmbeddedId
	private PlayerHistoryKey playerHistoryKey;
	@Column(name="salary")
	private int salary;
	@Column(name="keeperSeason")
	private int keeperSeason;
	@Column(name="minorLeaguer")
	private Boolean minorLeaguer;
	@OneToOne
	@JoinColumn(name="draftTeamId", referencedColumnName="teamId")
	private Team draftTeam;
	@OneToOne
	@JoinColumn(name="keeperTeamId", referencedColumnName="teamId")
	private Team keeperTeam;
	@Column(name="rookieStatus")
	private Boolean rookieStatus;

	public PlayerHistory() {
		playerHistoryKey = new PlayerHistoryKey();
	}

	public PlayerHistory(int season, int salary, int keeperSeason, Boolean isMinorLeaguer,
						 Team draftTeam, Team keeperTeam, Boolean rookieStatus) {
		setSeason(season);
		setSalary(salary);
		setKeeperSeason(keeperSeason);
		setMinorLeaguer(isMinorLeaguer);
		setDraftTeam(draftTeam);
		setKeeperTeam(keeperTeam);
		setRookieStatus(rookieStatus);
	}

	public void setPlayer(Player player) { playerHistoryKey.setPlayer(player);}

	public Player getPlayer() { return playerHistoryKey.getPlayer(); }

	public void setSeason(int season) {
		playerHistoryKey.setSeason(season);
	}

	public int getSeason() {
		return playerHistoryKey.getSeason();
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getSalary() {
		return salary;
	}

	public void setKeeperSeason(int keeperSeason) {
		this.keeperSeason = keeperSeason;
	}

	public int getKeeperSeason() {
		return keeperSeason;
	}

	public void setMinorLeaguer(Boolean minorLeaguer) {
		this.minorLeaguer = minorLeaguer;
	}

	public Boolean isMinorLeaguer() {
		return minorLeaguer;
	}

	public void setDraftTeam(Team draftTeam) {
		this.draftTeam = draftTeam;
	}

	public Team getDraftTeam() {
		return draftTeam;
	}

	public void setKeeperTeam(Team keeperTeam) {
		this.keeperTeam = keeperTeam;
	}

	public Team getKeeperTeam() {
		return keeperTeam;
	}

	public Boolean hasRookieStatus() {
		return rookieStatus;
	}

	public void setRookieStatus(Boolean rookieStatus) {
		this.rookieStatus = rookieStatus;
	}

	@Override
    public String toString() {
        return "PlayerHistory{" +
                "season=" + playerHistoryKey.getSeason() +
                ", salary=" + salary +
                ", keeperSeason=" + keeperSeason +
                ", minorLeaguer=" + minorLeaguer +
                ", draftTeam=" + draftTeam +
                ", keeperTeam=" + keeperTeam +
				", rookieStatus=" + rookieStatus +
                '}';
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PlayerHistory that = (PlayerHistory) o;

		if (!playerHistoryKey.equals(that.playerHistoryKey)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return playerHistoryKey.hashCode();
	}
}