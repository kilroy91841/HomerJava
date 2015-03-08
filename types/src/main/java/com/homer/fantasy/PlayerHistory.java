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
	@Column(name="lockedUp")
	private Boolean lockedUp;
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

	//Used to denote the player was kept from the previous season as a minor leaguer
	public Boolean isMinorLeaguer() {
		if(minorLeaguer == null) {
			minorLeaguer = false;
		}
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

	public Boolean getLockedUp() {
		return lockedUp;
	}

	public void setLockedUp(Boolean lockedUp) {
		this.lockedUp = lockedUp;
	}

	//Used to denote whether the player is still allowed to be a minor leaguer, i.e. has not passed
	//ip or ab threshhold and/or has not been called up to fantasy majors yet
	public Boolean hasRookieStatus() {
		if(rookieStatus == null) {
			rookieStatus = false;
		}
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
				", lockedUp=" + lockedUp +
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