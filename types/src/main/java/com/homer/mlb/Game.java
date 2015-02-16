package com.homer.mlb;

import com.homer.fantasy.Team;
import com.homer.fantasy.Player;
import com.homer.util.LocalDatePersistenceConverter;
import com.homer.util.LocalDateTimePersistenceConverter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by MLB on 1/25/15.
 */
@Entity
@Table(name="MLBGAME")
public class Game {

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
    private int awayScore;
    @Column(name="homeScore")
    private int homeScore;
    @ManyToOne
    @JoinColumn(name="awayProbablePitcherId", referencedColumnName="playerId")
    private Player awayProbablePitcher;
    @ManyToOne
    @JoinColumn(name="homeProbablePitcherId", referencedColumnName="playerId")
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

        MLBJSONObject gameMedia = new MLBJSONObject(json.getJSONObject("game_media"));
        if(gameMedia != null) {
            JSONArray media = null;
            try {
                media = gameMedia.getJSONArray("media");
            } catch(Exception e) {
                JSONObject singleGameMedia = gameMedia.getJSONObject("media");
                media = new JSONArray();
                media.put(singleGameMedia);
            }
            for(int i = 0; i < media.length(); i++) {
                MLBJSONObject med = new MLBJSONObject((JSONObject)media.get(i));
                if(med.has("start")) {
                    String[] dateParts =med.getString("start").split("-");
                    String localDate = dateParts[0] + "-" + dateParts[1] + "-" + dateParts[2];
                    gameTime = LocalDateTime.parse(localDate);
                    gameDate = gameTime.toLocalDate();
                }
            }
        }

        MLBJSONObject jsonStatus = new MLBJSONObject(json.getJSONObject("status"));
        status = jsonStatus.getString("status");
        inning = jsonStatus.getString("inning");
        inningState = jsonStatus.getString("inning_state");
        gamedayUrl = json.getString("gameday");
        amPm =json.getString("ampm");

        if(json.has("away_probabl_pitcher")) {
            MLBJSONObject awayPitcher = new MLBJSONObject(json.getJSONObject("away_probable_pitcher"));
            awayProbablePitcher = new Player();
            awayProbablePitcher.setPlayerId(awayPitcher.getLongProtected("id"));
        }

        if(json.has("home_probable_pitcher")) {
            MLBJSONObject homePitcher = new MLBJSONObject(json.getJSONObject("home_probable_pitcher"));
            homeProbablePitcher = new Player();
            homeProbablePitcher.setPlayerId(homePitcher.getLongProtected("id"));
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

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
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
