package com.homer.baseball;

/**
 * Created by arigolub on 1/30/15.
 */
public class PlayerHistory {

	private int season;
	private int salary;
	private int keeperSeason;
	private Boolean minorLeaguer;
	private Team draftTeam;
	private Team keeperTeam;

	public PlayerHistory() { }

	public PlayerHistory(int season, int salary, int keeperSeason, Boolean isMinorLeaguer, Team draftTeam, Team keeperTeam) {
		setSeason(season);
		setSalary(salary);
		setKeeperSeason(keeperSeason);
		setMinorLeaguer(isMinorLeaguer);
		setDraftTeam(draftTeam);
		setKeeperTeam(keeperTeam);
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public int getSeason() {
		return season;
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

	@Override
    public String toString() {
        return "PlayerHistory{" +
                "season=" + season +
                ", salary=" + salary +
                ", keeperSeason=" + keeperSeason +
                ", minorLeaguer=" + minorLeaguer +
                ", draftTeam=" + draftTeam +
                ", keeperTeam=" + keeperTeam +
                '}';
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerHistory)) return false;

        PlayerHistory that = (PlayerHistory) o;

        if (keeperSeason != that.keeperSeason) return false;
        if (minorLeaguer != that.minorLeaguer) return false;
        if (salary != that.salary) return false;
        if (season != that.season) return false;
        if (draftTeam != null ? !draftTeam.equals(that.draftTeam) : that.draftTeam != null) return false;
        if (keeperTeam != null ? !keeperTeam.equals(that.keeperTeam) : that.keeperTeam != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = season;
        result = 31 * result + salary;
        result = 31 * result + keeperSeason;
        result = 31 * result + (minorLeaguer ? 1 : 0);
        result = 31 * result + (draftTeam != null ? draftTeam.hashCode() : 0);
        result = 31 * result + (keeperTeam != null ? keeperTeam.hashCode() : 0);
        return result;
    }

}