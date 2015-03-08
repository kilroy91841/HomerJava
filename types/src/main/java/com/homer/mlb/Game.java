package com.homer.mlb;

import com.homer.JsonIgnore;
import com.homer.fantasy.Team;
import com.homer.fantasy.Player;
import com.homer.util.LocalDatePersistenceConverter;
import com.homer.util.LocalDateTimePersistenceConverter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by MLB on 1/25/15.
 */
@Entity
@Table(name="MLBGAME")
public class Game {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd h:mm a");

    @Id
    @Column(name="gameId")
    private Long gameId;
    @OneToOne
    @JoinColumn(name="homeTeamId", referencedColumnName="teamId")
    private Team homeTeam;
    @OneToOne
    @JoinColumn(name="awayTeamId", referencedColumnName="teamId")
    private Team awayTeam;
    @Convert(converter=LocalDatePersistenceConverter.class)
    @Column(name="gameDate")
    private LocalDate gameDate;
    @Column(name="awayScore")
    private Integer awayScore;
    @Column(name="homeScore")
    private Integer homeScore;
    @ManyToOne
    @JoinColumn(name="awayProbablePitcherId", referencedColumnName="mlbPlayerId")
    @JsonIgnore
    private Player awayProbablePitcher;
    @ManyToOne
    @JoinColumn(name="homeProbablePitcherId", referencedColumnName="mlbPlayerId")
    @JsonIgnore
    private Player homeProbablePitcher;
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    @Column(name="gameTime")
    private LocalDateTime gameTime;
    @Column(name="status")
    private String status;
    @Column(name="inning")
    private String inning;
    @Column(name="inningState")
    private String inningState;
    @Column(name="gamedayUrl")
    private String gamedayUrl;
    @Column(name="amPm")
    private String amPm;

    public Game() { }

    public Game(long gameId) {
        this.gameId = gameId;
    }

    public Game(MLBJSONObject json) throws Exception {
        if(!"R".equals(json.getString("game_type"))) {
            throw new Exception("Game is not regular season, skipping");
        }
        gameId = json.getLongProtected("game_pk");
        homeTeam = new Team(json.getInteger("home_team_id"));
        awayTeam = new Team(json.getInteger("away_team_id"));

        String dateTime = json.getString("time_date") + " " + json.getString("ampm");
        gameTime = LocalDateTime.parse(dateTime, dateFormatter);
        gameDate = gameTime.toLocalDate();

        MLBJSONObject jsonStatus = new MLBJSONObject(json.getJSONObject("status"));
        status = jsonStatus.getString("status");
        inning = jsonStatus.getString("inning");
        inningState = jsonStatus.getString("inning_state");
        gamedayUrl = json.getString("gameday");
        amPm =json.getString("ampm");

        if(json.has("away_probable_pitcher")) {
            MLBJSONObject awayPitcher = new MLBJSONObject(json.getJSONObject("away_probable_pitcher"));
            Long playerId = awayPitcher.getLongProtected("id");
            if(playerId != null) {
                awayProbablePitcher = new Player();
                awayProbablePitcher.setPlayerId(playerId);
            }
        }

        if(json.has("home_probable_pitcher")) {
            MLBJSONObject homePitcher = new MLBJSONObject(json.getJSONObject("home_probable_pitcher"));
            Long playerId = homePitcher.getLongProtected("id");
            if(playerId != null) {
                homeProbablePitcher = new Player();
                homeProbablePitcher.setPlayerId(playerId);
            }
        }

        if(json.has("linescore")) {
            MLBJSONObject linescore = new MLBJSONObject(json.getJSONObject("linescore"));
            MLBJSONObject runs = new MLBJSONObject(linescore.getJSONObject("r"));
            if(runs != null) {
                awayScore = runs.getInteger("away");
                homeScore = runs.getInteger("home");
            }
        }
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Player getAwayProbablePitcher() {
        return awayProbablePitcher;
    }

    public void setAwayProbablePitcher(Player awayProbablePitcher) {
        this.awayProbablePitcher = awayProbablePitcher;
    }

    public Player getHomeProbablePitcher() {
        return homeProbablePitcher;
    }

    public void setHomeProbablePitcher(Player homeProbablePitcher) {
        this.homeProbablePitcher = homeProbablePitcher;
    }

    public LocalDateTime getGameTime() {
        return gameTime;
    }

    public void setGameTime(LocalDateTime gameTime) {
        this.gameTime = gameTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInning() {
        return inning;
    }

    public void setInning(String inning) {
        this.inning = inning;
    }

    public String getInningState() {
        return inningState;
    }

    public void setInningState(String inningState) {
        this.inningState = inningState;
    }

    public String getGamedayUrl() {
        return gamedayUrl;
    }

    public void setGamedayUrl(String gamedayUrl) {
        this.gamedayUrl = gamedayUrl;
    }

    public String getAmPm() {
        return amPm;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", gameDate=" + gameDate +
                ", awayScore=" + awayScore +
                ", homeScore=" + homeScore +
                ", awayProbablePitcher=" + awayProbablePitcher +
                ", homeProbablePitcher=" + homeProbablePitcher +
                ", gameTime=" + gameTime +
                ", status='" + status + '\'' +
                ", inning='" + inning + '\'' +
                ", inningState='" + inningState + '\'' +
                ", gamedayUrl='" + gamedayUrl + '\'' +
                ", amPm='" + amPm + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (!gameId.equals(game.gameId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (gameId ^ (gameId >>> 32));
    }
}
