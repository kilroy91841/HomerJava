package com.homer.baseball;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arigolub on 1/29/15.
 */
public class DailyPlayer extends Player {

	public static final PlayerStatus ACTIVE = new PlayerStatus("ACTIVE", "A");
	public static final PlayerStatus DISABLEDLIST = new PlayerStatus("DISABLED LIST", "DL");
	public static final PlayerStatus MINORS = new PlayerStatus("MINORS", "MIN");
	public static final PlayerStatus FREEAGENT = new PlayerStatus("FREEAGENT", "FA");
	public static final PlayerStatus RESTRICTED = new PlayerStatus("RESTRICTED", "RST");
	//public static final PlayerStatus MINORLEAGUER = new PlayerStatus("MINORLEAGUER", "ML");

	static {
		PlayerStatus.map.put(ACTIVE.getName(), ACTIVE);
		PlayerStatus.map.put(DISABLEDLIST.getName(), DISABLEDLIST);
		PlayerStatus.map.put(MINORS.getName(), MINORS);
		PlayerStatus.map.put(FREEAGENT.getName(), FREEAGENT);
		PlayerStatus.map.put(RESTRICTED.getName(), RESTRICTED);
	}

	private Team fantasyTeam;
	private Team mlbTeam;
	private Date date;
	private Position fantasyPosition;
	private PlayerStatus fantasyStatus;
	private PlayerStatus mlbStatus;
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
				"player=" + super.toString() +
				", fantasyTeam=" + fantasyTeam +
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

	public static class PlayerStatus {
		private String name;
		private String code;
		protected static final Map<String, PlayerStatus> map = new HashMap<String, PlayerStatus>();
		private PlayerStatus(String name, String code) {
			this.name = name;
			this.code = code;
		}
		public String getName() { return name; }
		public String getCode() { return code; }
		public static PlayerStatus get(String name) throws Exception {
			PlayerStatus status = map.get(name);
			if(map == null) {
				throw new Exception("PlayerStatus not found for name: " + name);
			}
			return status;
		}
		@Override
		public String toString() {
			return "PlayerStatus{" +
					"name='" + name + '\'' +
					", code='" + code + '\'' +
					'}';
		}
	}
}

