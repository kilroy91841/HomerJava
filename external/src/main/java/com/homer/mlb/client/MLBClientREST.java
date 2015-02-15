package com.homer.mlb.client;

import com.homer.mlb.Game;
import com.homer.mlb.MLBJSONObject;
import com.homer.mlb.Player;
import com.homer.mlb.Stats;
import com.homer.mlb.parser.JSONPlayerParser;
import com.homer.mlb.parser.JSONRosterParser;
import com.homer.mlb.parser.JSONScheduleParser;
import com.homer.mlb.parser.JSONStatsParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.*;
import java.util.concurrent.Future;

/**
 * Created by arigolub on 2/1/15.
 */
public class MLBClientREST implements MLBClient {

    private static final Logger LOG = LoggerFactory.getLogger(MLBClient.class);

    private static final String URL_PLAYERINFO      = "http://mlb.com/lookup/json/named.player_info.bam";
    private static final String URL_ROSTER          = "http://mlb.mlb.com/lookup/json/named.roster_40.bam";
    private static final String URL_BATTERSTATS     = "http://mlb.mlb.com/lookup/json/named.mlb_bio_hitting_last_10.bam";
    private static final String URL_PITCHERSTATS    = "http://mlb.mlb.com/lookup/json/named.mlb_bio_pitching_last_10.bam";
    private static final String URL_SCHEDULEPART1   = "http://gd2.mlb.com/components/game/mlb/";
    private static final String URL_SCHEDULEPART2   = "/master_scoreboard.json";

    private static final String PARAM_SPORTCODE     = "sport_code";
    private static final String PARAM_PLAYERID      = "player_id";
    private static final String PARAM_GAMECOUNT     = "results";
    private static final String PARAM_GAMETYPE      = "game_type";
    private static final String PARAM_SEASON        = "season";
    private static final String PARAM_TEAMID        = "team_id";

    private static final String VALUE_SPORTCODE     = "'mlb'";
    private static final int    VALUE_GAMECOUNT     = 200;
    private static final String VALUE_GAMETYPE      = "'R'";
    private static final int    VALUE_SEASON        = 2014;

    /**
     * Get MLB Player created from JSON retrieved from MLB using Unirest client
     * Example URL: http://mlb.com/lookup/json/named.player_info.bam?player_id=545361&sport_code=%27mlb%27
     *
     * @param playerId The MLBPlayerID
     * @return {@link com.homer.mlb.Player}
     */
    @Override
    public Player getPlayer(long playerId) {
        Player player = null;

        Map<String, Object> parameters = getPlayerParameters(playerId);
        try {

            HttpResponse<JsonNode> response = Unirest.get(URL_PLAYERINFO)
                    .queryString(parameters)
                    .asJson();

            player = JSONPlayerParser.parsePlayer(response.getBody());

        } catch (UnirestException e) {
            LOG.error("Http Client Exception [playerId:" + playerId + "]", e);
        } catch (Exception e) {
            LOG.error("Runtime Exception [playerId:" + playerId + "]", e);
        }
        return player;
    }

    /**
     * Make request for MLB Player JSON and, on result, perform some task on the json
     * Example URL: http://mlb.com/lookup/json/named.player_info.bam?player_id=545361&sport_code=%27mlb%27
     *
     * @param playerId The MLBPlayerID
     * @param callback The action you want taken on the resulting json
     */
    @Override
    public void getPlayerAsync(long playerId, Callback<JsonNode> callback) {
        Map<String, Object> parameters = getPlayerParameters(playerId);
        try {
            Unirest.get(URL_PLAYERINFO)
                    .queryString(parameters)
                    .asJsonAsync(callback);
        } catch(Exception e) {
            LOG.error("Runtime Exception [playerId: " + playerId + "]", e);
        }
    }

    private Map<String, Object> getPlayerParameters(long playerId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(PARAM_SPORTCODE, VALUE_SPORTCODE);
        parameters.put(PARAM_PLAYERID, playerId);
        return parameters;
    }

    /**
     * Get MLB Player Stats created from JSON retrieved from MLB using Unirest client
     * Example Batting URL: http://mlb.mlb.com/lookup/json/named.mlb_bio_hitting_last_10.bam?results=200&game_type=%27R%27&season=2014&player_id=545361
     * Example Pitching URL: http://mlb.mlb.com/lookup/json/named.mlb_bio_pitching_last_10.bam?results=200&game_type=%27R%27&season=2014&player_id=433587
     *
     * @param playerId The MLBPlayerId
     * @param isBatter Whether the playerId corresponds to a hitter or a pitcher. True for hitter, false for pitcher.
     * @return {@link List<com.homer.mlb.Stats>}
     */
    @Override
    public List<Stats> getStats(long playerId, boolean isBatter) {
        List<Stats> stats = null;

        Map<String, Object> parameters = getStatsParameters(playerId);
        try {
            String url = isBatter ? URL_BATTERSTATS : URL_PITCHERSTATS;

            HttpResponse<JsonNode> response = Unirest.get(url)
                    .queryString(parameters)
                    .asJson();

            stats = JSONStatsParser.parseStats(playerId, response.getBody(), isBatter);

        } catch (UnirestException e) {
            LOG.error("Http Client Exception [playerId:" + playerId + "]", e);
        } catch (Exception e) {
            LOG.error("Runtime Exception [playerId:" + playerId + "]", e);
        }
        return stats;
    }

    /**
     * Make request for MLB Player's stats in JSON and, on result, perform some task on the json
     * Example Batting URL: http://mlb.mlb.com/lookup/json/named.mlb_bio_hitting_last_10.bam?results=200&game_type=%27R%27&season=2014&player_id=545361
     * Example Pitching URL: http://mlb.mlb.com/lookup/json/named.mlb_bio_pitching_last_10.bam?results=200&game_type=%27R%27&season=2014&player_id=433587
     * @param playerId The MLBPlayerId
     * @param isBatter Whether the playerId corresponds to a hitter or a pitcher. True for hitter, false for pitcher.
     * @param callback The action you want to take on the resulting JSON
     */
    @Override
    public void getStatsAsync(long playerId, boolean isBatter, Callback<JsonNode> callback) {
        Map<String, Object> parameters = getStatsParameters(playerId);
        List<Stats> stats = null;
        try {
            String url = isBatter ? URL_BATTERSTATS : URL_PITCHERSTATS;

            Unirest.get(url)
                    .queryString(parameters)
                    .asJsonAsync(callback);
        } catch (Exception e) {
            LOG.error("Runtime Exception [playerId:" + playerId + "]", e);
        }
    }

    private Map<String, Object> getStatsParameters(long playerId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(PARAM_GAMECOUNT, VALUE_GAMECOUNT);
        parameters.put(PARAM_GAMETYPE, VALUE_GAMETYPE);
        parameters.put(PARAM_SEASON, VALUE_SEASON);
        parameters.put(PARAM_PLAYERID, playerId);
        return parameters;
    }

    /**
     * Get List of MLB Players on a specified team created from JSON retrieved from MLB using Unirest client
     * Example URL: http://mlb.mlb.com/lookup/json/named.roster_40.bam?team_id=147
     * @param teamId The MLB TeamId
     * @return {@link List<com.homer.mlb.Player>}
     */
    @Override
    public List<Player> get40ManRoster(int teamId) {
        List<Player> players = null;

        Map<String, Object> parameters = getTeamParameters(teamId);
        try {

            HttpResponse<JsonNode> response = Unirest.get(URL_ROSTER)
                    .queryString(parameters)
                    .asJson();

            players = JSONRosterParser.parseRoster(response.getBody());

        } catch (UnirestException e) {
            LOG.error("Http Client Exception [teamId:" + teamId + "]", e);
        } catch (Exception e) {
            LOG.error("Runtime Exception [teamId:" + teamId + "]", e);
        }
        return players;
    }

    /**
     * Make request for MLB Roster of a specified team and, on result, perform some task on the json
     * Example URL: http://mlb.mlb.com/lookup/json/named.roster_40.bam?team_id=147
     * @param teamId The MLB TeamId
     * @return {@link List<com.homer.mlb.Player>}
     */
    @Override
    public void get40ManRosterAsync(int teamId, Callback<JsonNode> callback) {
        Map<String, Object> parameters = getTeamParameters(teamId);
        try {
            Unirest.get(URL_ROSTER)
                    .queryString(parameters)
                    .asJsonAsync(callback);
        } catch (Exception e) {
            LOG.error("Runtime exception", e);
        }
    }

    private Map<String, Object> getTeamParameters(int teamId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(PARAM_TEAMID, teamId);
        return parameters;
    }

    @Override
    public List<Game> getSchedule(LocalDate date) {
        List<Game> games = null;

        String urlDatePart = getURLDatePart(date);
        try {
            //String url = "http://gd2.mlb.com/components/game/mlb/year_2014/month_08/day_09/master_scoreboard.json";
            String url = URL_SCHEDULEPART1 + urlDatePart + URL_SCHEDULEPART2;
            HttpResponse<JsonNode> response = Unirest
                    .get(url)
                    .asJson();

            games = JSONScheduleParser.parseSchedule(response.getBody());

        } catch (UnirestException e) {
            LOG.error("Http Client Exception [date:" + date+ "]", e);
        } catch (Exception e) {
            LOG.error("Runtime Exception [date:" + date + "]", e);
        }

        return games;
    }

    @Override
    public void getScheduleAsync(LocalDate date, Callback<JsonNode> callback) {
        String urlDatePart = getURLDatePart(date);
        try {
            Unirest.get(URL_SCHEDULEPART1 + urlDatePart + URL_SCHEDULEPART2)
                    .asJsonAsync(callback);
        } catch (Exception e) {
            LOG.error("Runtime Exception [date:" + date +"]", e);
        }
    }

    private String getURLDatePart(LocalDate date) {
        String monthString = String.valueOf(date.getMonthValue());
        if(monthString.length() < 2) {
            monthString = "0" + monthString;
        }
        String dayString = String.valueOf(date.getDayOfMonth());
        if(dayString.length() < 2) {
            dayString = "0" + dayString;
        }
        return "year_" + date.getYear() + "/month_" + monthString + "/day_" + dayString;
    }
}
