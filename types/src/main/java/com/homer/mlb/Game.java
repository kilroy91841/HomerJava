package com.homer.mlb;

import com.homer.fantasy.Team;

import java.util.Date;

/**
 * Created by MLB on 1/25/15.
 */
public class Game {

    private int gameId;
    private Team homeTeam;
    private Team awayTeam;
    private Date gameDate;
    private int awayScore;
    private int homeScore;
    private Player awayProbablePitcher;
    private Player homeProbablePitcher;
    private Date gameTime;
    private String status;
    private String inning;
    private String inningState;
    private String gamedayUrl;
    private String amPm;

    public Game() { }

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

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
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

    public Date getGameTime() {
        return gameTime;
    }

    public void setGameTime(Date gameTime) {
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
}
